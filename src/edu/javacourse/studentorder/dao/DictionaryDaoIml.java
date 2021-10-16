package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.CountryArea;
import edu.javacourse.studentorder.domain.PassportOffice;
import edu.javacourse.studentorder.domain.RegisterOffice;
import edu.javacourse.studentorder.domain.Street;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoIml implements DictionaryDao {

  private static final String GET_STREET = "SELECT * FROM street WHERE LOWER(street_name) LIKE LOWER(?)";
  private static final String GET_PASSPORT = "SELECT * FROM passport_office WHERE passp_office_area_id = ?";
  private static final String GET_REGISTER = "SELECT * FROM register_office WHERE reg_office_area_id = ?";
  private static final String GET_AREA = "SELECT * FROM country_structure WHERE area_id LIKE ? and area_id <> ?";

  public List<Street> findStreets(String pattern) throws DaoException {
    List<Street> streetList = new LinkedList<>();

    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(GET_STREET)) {

      preparedStatement.setString(1, "%" + pattern + "%");
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Street street = new Street(
            resultSet.getLong("street_code"),
            resultSet.getString("street_name"));
        streetList.add(street);
      }
    } catch (SQLException e) {
      throw new DaoException(e);
    }

    return streetList;
  }

  @Override
  public List<PassportOffice> findPassportOffices(String areaId) throws DaoException {
    List<PassportOffice> passportOffices = new LinkedList<>();

    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(GET_PASSPORT)) {

      preparedStatement.setString(1, areaId);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        PassportOffice passportOffice = new PassportOffice(
            resultSet.getLong("passp_office_id"),
            resultSet.getString("passp_office_area_id"),
            resultSet.getString("passp_office_name")
            );
        passportOffices.add(passportOffice);
      }
    } catch (SQLException e) {
      throw new DaoException(e);
    }

    return passportOffices;
  }

  @Override
  public List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException {
    List<RegisterOffice> registerOffices = new LinkedList<>();

    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(GET_REGISTER)) {

      preparedStatement.setString(1, areaId);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        RegisterOffice registerOffice = new RegisterOffice(
            resultSet.getLong("reg_office_id"),
            resultSet.getString("reg_office_area_id"),
            resultSet.getString("reg_office_name")
        );
        registerOffices.add(registerOffice);
      }
    } catch (SQLException e) {
      throw new DaoException(e);
    }

    return registerOffices;
  }

  @Override
  public List<CountryArea> findAreas(String areaId) throws DaoException {
    List<CountryArea> countryAreas = new LinkedList<>();

    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(GET_AREA)) {

      String parameter1 = getInclusiveAreas(areaId);
      String parameter2 = "";
      preparedStatement.setString(1, parameter1);
      preparedStatement.setString(2, parameter2);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        CountryArea countryArea = new CountryArea(
            resultSet.getString("area_id"),
            resultSet.getString("area_name")
        );
        countryAreas.add(countryArea);
      }
    } catch (SQLException e) {
      throw new DaoException(e);
    }

    return countryAreas;
  }

  private String getInclusiveAreas(String areaId) throws SQLException {
    if (areaId == null || areaId.trim().isEmpty()) {
      return "__00000";
    } else if (areaId.endsWith("00000")) {
      return areaId.substring(0, 2) + "__000";
    } else if (areaId.endsWith("000")) {
      return areaId.substring(0, 4) + "___";
    } else {
      throw new SQLException("Invalid parameter 'areaId': " + areaId);
    }
  }


  private Connection getConnection() throws SQLException {
    Connection connection = DriverManager.getConnection(
        Config.getProperty(Config.DB_URL),
        Config.getProperty(Config.DB_USER),
        Config.getProperty(Config.DB_PASSWORD)
    );
    return connection;
  }


}
