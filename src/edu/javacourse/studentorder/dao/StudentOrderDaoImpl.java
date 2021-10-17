package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;

public class StudentOrderDaoImpl implements StudentOrderDao {
  private static final String INSERT_ORDER = "INSERT INTO student_order(" +
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

  public static final String INSERT_CHILD = "INSERT INTO student_child (" +
      "student_order_id, child_sur_name, child_given_name, child_patronymic," +
      "child_date_of_birth, child_certificate_number, child_certificate_date, child_register_office_id," +
      "child_post_index, child_street_code, child_building, child_extension, child_apartment)" +
      "VALUES (" +
      "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
      ");";

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
         PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {

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
}
