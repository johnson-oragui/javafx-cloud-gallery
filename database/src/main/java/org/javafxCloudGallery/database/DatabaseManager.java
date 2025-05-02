package org.javafxCloudGallery.database;

import org.javafxCloudGallery.utilities.MyConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

class Pair<X, Y, Z> {
  private X first;
  private Y second;
  private Z third;

  public Pair() {
  }

  public Pair(X first, Y second, Z third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }

  public void setFirst(X first) {
    this.first = first;
  }

  public void setSecond(Y second) {
    this.second = second;
  }

  public X getFirst() {
    return first;
  }

  public Y getSecond() {
    return second;
  }

  public void setThird(Z third) {
    this.third = third;
  }

  public Z getThird() {
    return third;
  }

}

public class DatabaseManager {
  private static HikariDataSource dataSource;

  static {
    HikariConfig config = new HikariConfig();
    try {
      // Verify environment variables first
      if (MyConfig.DB_URL == null || MyConfig.DB_USER == null || MyConfig.DB_PASSWORD == null) {
        throw new RuntimeException("Missing database configuration in .env file");
      }

      config.setJdbcUrl(MyConfig.DB_URL);
      config.setUsername(MyConfig.DB_USER);
      config.setPassword(MyConfig.DB_PASSWORD);
      config.setDriverClassName("org.postgresql.Driver");

      // Pool configuration
      config.setMaximumPoolSize(10);
      config.setMinimumIdle(5);
      config.setConnectionTimeout(30000); // 30 seconds
      config.setIdleTimeout(600000); // 10 minutes
      config.setMaxLifetime(1800000); // 30 minutes
      config.setPoolName("GalleryPool");
      config.setRegisterMbeans(true);

      // PostgreSQL optimizations
      config.addDataSourceProperty("cachePrepStmts", "true");
      config.addDataSourceProperty("prepStmtCacheSize", "250");
      config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

      dataSource = new HikariDataSource(config);
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize database pool", e);
    }

  }

  public static Connection getConnection() throws SQLException, Exception {
    Pair<String, String, String> pair = mimicMigration();

    Connection connection = dataSource.getConnection();

    Statement statement = connection.createStatement();
    // create tables
    statement.executeUpdate(pair.getFirst());
    statement.executeUpdate(pair.getSecond());
    statement.executeUpdate(pair.getThird());
    System.out.println("Database tables verified/created successfully");
    return connection;
  }

  public static Pair<String, String, String> mimicMigration() {
    Pair<String, String, String> pair = new Pair<>();

    // createUsersTableSQL
    pair.setFirst("""
        CREATE TABLE IF NOT EXISTS users (
            id SERIAL PRIMARY KEY,
            username VARCHAR(50) UNIQUE NOT NULL,
            email VARCHAR(100) UNIQUE NOT NULL,
            password VARCHAR(255) NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            is_active BOOLEAN DEFAULT TRUE
        )
        """);

    // createUserImagesTableSQL
    pair.setSecond("""
        CREATE TABLE IF NOT EXISTS user_images (
            id SERIAL PRIMARY KEY,
            user_id INTEGER REFERENCES users(id),
            title VARCHAR(255) NOT NULL,
            description VARCHAR(255) NOT NULL,
            image_url VARCHAR(255),
            thumbnail_url VARCHAR(255),
            uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            is_public BOOLEAN DEFAULT FALSE,
            CONSTRAINT fk_user_images_user_id
                FOREIGN KEY(user_id)
                REFERENCES users(id)
                ON DELETE CASCADE
        )
        """);

    pair.setThird("""
        CREATE INDEX IF NOT EXISTS idx_user_images_user_id ON user_images(user_id);
        CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
        """);

    return pair;
  }
}
