package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class StudentOrderDaoImpl implements StudentOrderDao {
  private static final String INSERT_ORDER =
      "INSERT INTO student_order(" +
          "student_order_status, student_order_date, husb_sur_name, husb_given_name, husb_patronymic," +
          " husb_date_of_birth, husb_passport_series, husb_passport_number, husb_passport_date," +
          " husb_passport_office_id, husb_post_index, husb_street_code, husb_building," +
          " husb_extension, husb_apartment, husb_university_id, husb_student_number, wife_sur_name," +
          " wife_given_name, wife_patronymic, wife_date_of_birth, wife_passport_series, wife_passport_number," +
          " wife_passport_date, wife_passport_office_id, wife_post_index, wife_street_code, wife_building," +
          " wife_extension, wife_apartment, wife_university_id, wife_student_number, certificate_id," +
          " register_office_id, marriage_date)" +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
          " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

  public static final String INSERT_CHILD =
      "INSERT INTO student_child (" +
          "student_order_id, child_sur_name, child_given_name, child_patronymic," +
          "child_date_of_birth, child_certificate_number, child_certificate_date, child_register_office_id," +
          "child_post_index, child_street_code, child_building, child_extension, child_apartment)" +
          "VALUES (" +
          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
          ");";

  public static final String SELECT_ORDERS =
      "SELECT so.*, ro.reg_office_id, ro.reg_office_name, " +
          "po_h.passp_office_area_id husb_passp_office_area_id, po_h.passp_office_name husb_passp_office_name, " +
          "po_w.passp_office_area_id wife_passp_office_area_id, po_w.passp_office_name wife_passp_office_name " +
          "FROM student_order so " +
          "JOIN register_office ro ON so.register_office_id = ro.reg_office_id " +
          "JOIN passport_office po_h ON po_h.passp_office_id = so.husb_passport_office_id " +
          "JOIN passport_office po_w ON po_w.passp_office_id = so.wife_passport_office_id " +
          "WHERE student_order_status = ? ORDER BY so.student_order_id";

  public static final String SELECT_CHILD =
      "SELECT sc.*, ro.reg_office_area_id, ro.reg_office_name " +
          "FROM student_child sc " +
          "JOIN register_office ro ON sc.child_register_office_id = ro.reg_office_id " +
          "WHERE student_order_id IN  ";

  public static final String SELECT_ORDERS_FULL =
      "SELECT so.*, " +
          "ro.reg_office_area_id, " +
          "ro.reg_office_name, " +
          "po_h.passp_office_area_id husb_passp_office_area_id, " +
          "po_h.passp_office_name    husb_passp_office_name, " +
          "po_w.passp_office_area_id wife_passp_office_area_id, " +
          "po_w.passp_office_name    wife_passp_office_name, " +
          "so_c.*, " +
          "ro_c.reg_office_area_id, " +
          "ro_c.reg_office_name " +
          "FROM student_order so " +
          "JOIN register_office ro ON so.register_office_id = ro.reg_office_id " +
          "JOIN passport_office po_h ON so.husb_passport_office_id = po_h.passp_office_id " +
          "JOIN passport_office po_w ON so.wife_passport_office_id = po_w.passp_office_id " +
          "JOIN student_child so_c ON so.student_order_id = so_c.student_order_id " +
          "JOIN register_office ro_c ON ro_c.reg_office_id = so_c.child_register_office_id " +
          "WHERE student_order_status = ? " +
          "ORDER BY so.student_order_date";

  // TODO refactoring: make one method
  private Connection getConnection() throws SQLException {
    Connection connection = DriverManager.getConnection(
        Config.getProperty(Config.DB_URL),
        Config.getProperty(Config.DB_USER),
        Config.getProperty(Config.DB_PASSWORD)
    );
    return connection;
  }

  @Override
  public Long saveStudentOrder(StudentOrder so) throws DaoException {
    Long studentOrderID = -1L;

    try (Connection connection = getConnection();
         var statement = connection.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {

      connection.setAutoCommit(false);
      try {
        // Header
        statement.setInt(1, StudentOrderStatus.START.ordinal());
        statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

        // Husband and wife
        setParamsForAdult(statement, 3, so.getHusband());
        setParamsForAdult(statement, 18, so.getWife());

        // Marriage
        statement.setString(33, so.getMarriageCertificateId());
        statement.setLong(34, so.getMarriageOffice().getOfficeId());
        statement.setDate(35, Date.valueOf(so.getMarriageDate()));

        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
          studentOrderID = generatedKeys.getLong(1);
        }

        saveChildren(connection, so, studentOrderID);
        connection.commit();
      } catch (SQLException SQLExc) {
        connection.rollback();
        throw SQLExc;
      }

    } catch (SQLException e) {
      throw new DaoException(e);
    }

    return studentOrderID;
  }

  private void saveChildren(Connection connection, StudentOrder so, Long studentOrderID) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(INSERT_CHILD)) {

      for (Child child : so.getChildren()) {
        statement.setLong(1, studentOrderID);
        setParamsForChild(statement, child);
        statement.addBatch();
      }
      statement.executeBatch();
    }

  }

  private void setParamsForAdult(PreparedStatement statement, int index, Adult adult) throws SQLException {
    setParamsForPerson(statement, index, adult);

    statement.setString(index + 4, adult.getPassportSeries());
    statement.setString(index + 5, adult.getPassportNumber());
    statement.setDate(index + 6, Date.valueOf(adult.getIssueDate()));
    statement.setLong(index + 7, adult.getIssueDepartment().getOfficeId());

    setParamsForAddress(statement, index + 8, adult);

    statement.setLong(index + 13, adult.getUniversity().getUniversityId());
    statement.setString(index + 14, adult.getUniversity().getUniversityName());
  }

  private void setParamsForChild(PreparedStatement statement, Child child) throws SQLException {
    setParamsForPerson(statement, 2, child);

    statement.setString(6, child.getCertificateNumber());
    statement.setDate(7, Date.valueOf(child.getIssueDate()));
    statement.setLong(8, child.getIssueDepartment().getOfficeId());

    setParamsForAddress(statement, 9, child);
  }

  private void setParamsForPerson(PreparedStatement statement, int index, Person person) throws SQLException {
    statement.setString(index, (person.getSurName()));
    statement.setString(index + 1, (person.getGivenName()));
    statement.setString(index + 2, (person.getPatronymic()));
    statement.setDate(index + 3, Date.valueOf(person.getDateOfBirth()));
  }

  private void setParamsForAddress(PreparedStatement statement, int index, Person person) throws SQLException {
    Address address = person.getAddress();
    statement.setString(index, address.getPostIndex());
    statement.setLong(index + 1, address.getStreet().getStreetCode());
    statement.setString(index + 2, address.getBuilding());
    statement.setString(index + 3, address.getExtension());
    statement.setString(index + 4, address.getApartment());
  }

  @Override
  public List<StudentOrder> getStudentOrders() throws DaoException {
    return getStudentOrdersOneSelect();
//    return getStudentOrdersTwoSelect();
  }

  private List<StudentOrder> getStudentOrdersOneSelect() throws DaoException {
    List<StudentOrder> orderList = new LinkedList<>();

    try (Connection connection = getConnection();
         var statement = connection.prepareStatement(SELECT_ORDERS_FULL)) {

      Map<Long, StudentOrder> orderMap = new HashMap<>();

      statement.setInt(1, StudentOrderStatus.START.ordinal());
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Long soId = resultSet.getLong("student_order_id");
        if (!orderMap.containsKey(soId)) {
          StudentOrder studentOrder = getStudentOrderWithoutChild(resultSet);

          orderList.add(studentOrder);
          orderMap.put(soId, studentOrder);
        }

        StudentOrder studentOrder = orderMap.get(soId);
        studentOrder.addChildren(getChild(resultSet));

      }

      resultSet.close();
    } catch (SQLException SQLExc) {
      throw new DaoException(SQLExc);
    }

    return orderList;
  }

  public List<StudentOrder> getStudentOrdersTwoSelect() throws DaoException {
    List<StudentOrder> orderList = new LinkedList<>();

    try (Connection connection = getConnection();
         var statement = connection.prepareStatement(SELECT_ORDERS)) {

      statement.setInt(1, StudentOrderStatus.START.ordinal());
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        StudentOrder studentOrder = getStudentOrderWithoutChild(resultSet);

        orderList.add(studentOrder);
      }

      fillChildren(connection, orderList);

      resultSet.close();
    } catch (SQLException SQLExc) {
      throw new DaoException(SQLExc);
    }
    return orderList;
  }

  private StudentOrder getStudentOrderWithoutChild(ResultSet resultSet) throws SQLException {
    StudentOrder studentOrder = new StudentOrder();

    fillForHeaderStudentOrder(studentOrder, resultSet);
    fillParamsForMarriage(studentOrder, resultSet);

    studentOrder.setHusband(getAdult(resultSet, "husb"));
    studentOrder.setWife(getAdult(resultSet, "wife"));
    return studentOrder;
  }

  private Child getChild(ResultSet rs) throws SQLException {
    String sur_name = rs.getString("child_sur_name");
    String given_name = rs.getString("child_given_name");
    String patronymic = rs.getString("child_patronymic");
    LocalDate dateOfBirth = rs.getDate("child_date_of_birth").toLocalDate();

    Child child = new Child(sur_name, given_name, patronymic, dateOfBirth);

    child.setCertificateNumber(rs.getString("child_certificate_number"));
    child.setIssueDate(rs.getDate("child_certificate_date").toLocalDate());

    RegisterOffice registerOffice = new RegisterOffice();
    registerOffice.setOfficeId(rs.getLong("child_register_office_id"));
    registerOffice.setOfficeAreaId(rs.getString("reg_office_area_id"));
    registerOffice.setOfficeName(rs.getString("reg_office_name"));
    child.setIssueDepartment(registerOffice);

    Address address = new Address();
    address.setPostIndex(rs.getString("child_post_index"));
    Street street = new Street(rs.getLong("child_street_code"), "");
    address.setStreet(street);
    address.setBuilding(rs.getString("child_building"));
    address.setExtension(rs.getString("child_extension"));
    address.setApartment(rs.getString("child_apartment"));

    child.setAddress(address);

    return child;
  }

  private Adult getAdult(ResultSet rs, String prefix) throws SQLException {
    Adult adult = new Adult();
    adult.setSurName(rs.getString(prefix + "_sur_name"));
    adult.setGivenName(rs.getString(prefix + "_given_name"));
    adult.setPatronymic(rs.getString(prefix + "_patronymic"));
    adult.setDateOfBirth(rs.getDate(prefix + "_date_of_birth").toLocalDate());
    adult.setPassportSeries(rs.getString(prefix + "_passport_series"));
    adult.setPassportNumber(rs.getString(prefix + "_passport_number"));
    adult.setIssueDate(rs.getDate(prefix + "_passport_date").toLocalDate());

    var registerOffice = new PassportOffice();
    registerOffice.setOfficeId(rs.getLong(prefix + "_passport_office_id"));
    registerOffice.setOfficeAreaId(rs.getString(prefix + "_passp_office_area_id"));
    registerOffice.setOfficeName(rs.getString(prefix + "_passp_office_name"));
    adult.setIssueDepartment(registerOffice);

    var address = new Address();
    address.setPostIndex(rs.getString(prefix + "_post_index"));
    var street = new Street(rs.getLong(prefix + "_street_code"), "");
    address.setStreet(street);
    address.setBuilding(rs.getString(prefix + "_building"));
    address.setExtension(rs.getString(prefix + "_extension"));
    address.setApartment(rs.getString(prefix + "_apartment"));

    adult.setAddress(address);

    var university = new University();
    university.setUniversityId(rs.getLong(prefix + "_university_id"));
    university.setUniversityName(rs.getString(prefix + "_student_number"));

    adult.setUniversity(university);

    return adult;
  }

  private void fillParamsForMarriage(StudentOrder studentOrder, ResultSet resultSet) throws SQLException {
    studentOrder.setMarriageCertificateId(resultSet.getString("certificate_id"));

    Long registerOfficeId = resultSet.getLong("register_office_id");
    String areaId = resultSet.getString("reg_office_area_id");
    String areaName = resultSet.getString("reg_office_name");
    var registerOffice = new RegisterOffice(registerOfficeId, areaId, areaName);
    studentOrder.setMarriageOffice(registerOffice);

    studentOrder.setMarriageDate(resultSet.getDate("marriage_date").toLocalDate());
  }

  private void fillChildren(Connection connection, List<StudentOrder> orderList) throws SQLException {
    String stringChild = "(" + orderList.stream()
        .map(StudentOrder::getOrderId)
        .map(String::valueOf)
        .collect(Collectors.joining(",")) + ")";

    Map<Long, StudentOrder> orderMap = orderList.stream()
        .collect(Collectors.toMap(StudentOrder::getOrderId, so -> so));

    try (var statement = connection.prepareStatement(SELECT_CHILD + stringChild);
         ResultSet resultSet = statement.executeQuery()) {

      while (resultSet.next()) {
        Child child = getChild(resultSet);
        long student_order_id = resultSet.getLong("student_order_id");
        StudentOrder studentOrder = orderMap.get(student_order_id);
        studentOrder.addChildren(child);
      }
    }
  }

  private void fillForHeaderStudentOrder(StudentOrder studentOrder, ResultSet resultSet) throws SQLException {
    studentOrder.setOrderId(resultSet.getLong("student_order_id"));
    studentOrder.setOrderStatus(StudentOrderStatus.fromValue(resultSet.getInt("student_order_status")));
    studentOrder.setOrderDate(resultSet.getTimestamp("student_order_date").toLocalDateTime());
  }
}
