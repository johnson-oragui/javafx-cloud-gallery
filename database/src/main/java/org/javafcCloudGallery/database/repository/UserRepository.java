package org.javafcCloudGallery.database.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.javafcCloudGallery.database.DatabaseManager;
import org.javafcCloudGallery.database.models.User;

public class UserRepository {
  public static Optional<User> findUserByEmailOrUsername(String target) throws SQLException, Exception {
    String query = "SELECT id, username, email, password FROM users WHERE username = ? OR email = ?;";

    try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, target);
      stmt.setString(2, target);

      ResultSet resultSet = stmt.executeQuery();
      if (resultSet.next()) {
        User user = new User(
            resultSet.getInt("id"),
            resultSet.getString("username"),
            resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        return Optional.of(user);
      }

    }
    return Optional.empty();
  }

  public static Boolean createNewUser(String username, String password, String email) throws Exception, SQLException {
    String query = "INSERT INTO users (username, email, password) VALUES(?, ?, ?);";

    try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, username);
      stmt.setString(2, email);
      stmt.setString(3, password);

      return stmt.executeUpdate() > 0;
    }
  }
}
