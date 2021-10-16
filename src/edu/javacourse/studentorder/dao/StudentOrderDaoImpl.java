package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;

public class StudentOrderDaoImpl implements StudentOrderDao {
  private static final String INSERT_ORDER = "INSERT INTO student_order (" +
      "student_order_status, student_order_date, husb_sur_name, husb_given_name, husb_patronymic, " +
      "husb_date_of_birth, husb_passport_series, husb_passport_number, husb_passport_date, husb_passport_office_id," +
      "husb_post_index, husb_street_code, husb_building, husb_extension, husb_apartment," +
      "wife_sur_name, wife_given_name, wife_patronymic, wife_date_of_birth, wife_passport_series," +
      "wife_passport_number, wife_passport_date, wife_passport_office_id, wife_post_index, wife_street_code," +
      "wife_building, wife_extension, wife_apartment, certificate_id, register_office_id, marriage_date)" +
      "VALUES (" +
      "?, ?, ?, ?, ?," +
      "?, ?, ?, ?, ?," +
      "?, ?, ?, ?, ?," +
      "?, ?, ?, ?, ?," +
      "?, ?, ?, ?, ?," +
      "?, ?, ?, ?, ?, ?" +
      ")";

  @Override
  public Long saveStudentOrder(StudentOrder so) throws DaoException {
    Long result = -1L;

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {

      // Header
      statement.setInt(1, StudentOrderStatus.START.ordinal());
      statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

      // Husband
      Adult husband = so.getHusband();
      statement.setString(3, (husband.getSurName()));
      statement.setString(4, (husband.getGivenName()));
      statement.setString(5, (husband.getPatronymic()));
      statement.setDate(6, Date.valueOf(husband.getDateOfBirth()));
      statement.setString(7, husband.getPassportSeries());
      statement.setString(8, husband.getPassportNumber());
      statement.setDate(9, Date.valueOf(husband.getIssueDate()));
      statement.setLong(10, husband.getIssueDepartment().getOfficeId());
      Address husbandAddress = husband.getAddress();
      statement.setString(11, husbandAddress.getPostIndex());
      statement.setLong(12, husbandAddress.getStreet().getStreetCode());
      statement.setString(13, husbandAddress.getBuilding());
      statement.setString(14, husbandAddress.getBuilding());
      statement.setString(15, husbandAddress.getApartment());

      // Wife
      Adult wife = so.getWife();
      statement.setString(16, (wife.getSurName()));
      statement.setString(17, (wife.getGivenName()));
      statement.setString(18, (wife.getPatronymic()));
      statement.setDate(19, Date.valueOf(wife.getDateOfBirth()));
      statement.setString(20, wife.getPassportSeries());
      statement.setString(21, wife.getPassportNumber());
      statement.setDate(22, Date.valueOf(wife.getIssueDate()));
      statement.setLong(23, wife.getIssueDepartment().getOfficeId());
      Address wifeAddress = wife.getAddress();
      statement.setString(24, wifeAddress.getPostIndex());
      statement.setLong(25, wifeAddress.getStreet().getStreetCode());
      statement.setString(26, wifeAddress.getBuilding());
      statement.setString(27, wifeAddress.getBuilding());
      statement.setString(28, wifeAddress.getApartment());

      // Marriage
      statement.setString(29, so.getMarriageCertificateId());
      statement.setLong(30, so.getMarriageOffice().getOfficeId());
      statement.setDate(31, Date.valueOf(so.getMarriageDate()));

      statement.executeUpdate();

      ResultSet generatedKeys = statement.getGeneratedKeys();
      if (generatedKeys.next()) {
        result = generatedKeys.getLong(1);
      }

    } catch (SQLException e) {
      throw new DaoException(e);
    }

    return result;
  }

  // TODO refactoring: make one method
  private Connection getConnection() throws SQLException {
    Connection connection = DriverManager.getConnection(
        Config.getProperty(Config.DB_URL),
        Config.getProperty(Config.DB_USER),
        Config.getProperty(Config.DB_PASSWORD)
    );
    return connection;
  }
}
