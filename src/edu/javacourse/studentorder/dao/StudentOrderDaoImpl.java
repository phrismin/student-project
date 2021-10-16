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

      // Husband and wife
      setParamsForAdult(statement, 3, so.getHusband());
      setParamsForAdult(statement, 16, so.getWife());

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

  private void setParamsForAdult(PreparedStatement statement, int index, Adult adult) throws SQLException {
    statement.setString(index++, (adult.getSurName()));
    statement.setString(index++, (adult.getGivenName()));
    statement.setString(index++, (adult.getPatronymic()));
    statement.setDate(index++, Date.valueOf(adult.getDateOfBirth()));
    statement.setString(index++, adult.getPassportSeries());
    statement.setString(index++, adult.getPassportNumber());
    statement.setDate(index++, Date.valueOf(adult.getIssueDate()));
    statement.setLong(index++, adult.getIssueDepartment().getOfficeId());
    Address address = adult.getAddress();
    statement.setString(index++, address.getPostIndex());
    statement.setLong(index++, address.getStreet().getStreetCode());
    statement.setString(index++, address.getBuilding());
    statement.setString(index++, address.getBuilding());
    statement.setString(index, address.getApartment());
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
