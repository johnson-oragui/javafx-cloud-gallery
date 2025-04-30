package org.javafcCloudGallery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
  private static final String URL = System.getenv("DB_URL");
  private static final String USER = System.getenv("POSTGRES_USER");
  private static final String PASSWORD = System.getenv("POSTGRES_PASSWORD");

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}
