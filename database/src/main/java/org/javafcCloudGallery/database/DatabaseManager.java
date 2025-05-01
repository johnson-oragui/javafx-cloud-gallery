package org.javafcCloudGallery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseManager {
  private static final Dotenv dotenv = Dotenv.load();
  private static final String URL = dotenv.get("DB_URL");
  private static final String USER = dotenv.get("POSTGRES_USER");
  private static final String PASSWORD = dotenv.get("POSTGRES_PASSWORD");

  public static Connection getConnection() throws SQLException, Exception {
    if (URL == null || USER == null || PASSWORD == null) {
      throw new Exception("DATABASE URL, PASSWORD, USER missing in config");
    }
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}
