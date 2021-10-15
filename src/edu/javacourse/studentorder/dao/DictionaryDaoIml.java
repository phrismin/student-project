package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.domain.Street;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoIml implements DictionaryDao {

  private static final String GET_STREET = "SELECT * FROM street WHERE LOWER(street_name) LIKE LOWER(?)";

  public List<Street> findStreets(String pattern) throws DaoException {
//    Class.forName("org.postgresql.Driver");
    LinkedList<Street> streetList = new LinkedList<>();

    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(GET_STREET)) {

      preparedStatement.setString(1, "%" + pattern + "%");
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Street street = new Street(resultSet.getLong("street_code"), resultSet.getString("street_name"));
        streetList.add(street);
      }
    } catch (SQLException e) {
      throw new DaoException(e);
    }

    return streetList;
  }

  private Connection getConnection() throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/student_project_database",
        "postgres",
        "postgres"
    );
    return connection;
  }


}
