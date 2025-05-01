package org.javafcCloudGallery.database;

import org.javafxCloudGallery.utilities.MyConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

  public static Connection getConnection() throws SQLException, Exception {

    return DriverManager.getConnection(MyConfig.DB_URL, MyConfig.DB_USER, MyConfig.DB_PASSWORD);
  }
}
