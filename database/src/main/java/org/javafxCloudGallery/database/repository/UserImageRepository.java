package org.javafxCloudGallery.database.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.javafxCloudGallery.database.DatabaseManager;

public class UserImageRepository {
  public static Boolean saveImage(String thumbnailUrl, int UserId, String imageUrl, String title, String description)
      throws SQLException, Exception {
    String queryString = "INSERT INTO user_images (user_id, image_url, thumbnail_url, title, description) VALUES (?,?,?,?,?);";
    try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(queryString)) {
      stmt.setInt(1, UserId);
      stmt.setString(2, imageUrl);
      stmt.setString(3, thumbnailUrl);
      stmt.setString(4, title);
      stmt.setString(5, description);
      return stmt.executeUpdate() > 0;
    }
  }

  public static ResultSet getUserImages(int userId, int page, int limit) throws SQLException, Exception {
    String queryString = "SELECT * FROM user_images WHERE user_id = ? ORDER BY uploaded_at DESC LIMIT ? OFFSET ?";
    Connection conn = DatabaseManager.getConnection();
    PreparedStatement stmt = conn.prepareStatement(queryString);
    int offset = page * limit - limit;
    stmt.setInt(1, userId);
    stmt.setInt(2, limit);
    stmt.setInt(3, offset);

    return stmt.executeQuery();

  }
}
