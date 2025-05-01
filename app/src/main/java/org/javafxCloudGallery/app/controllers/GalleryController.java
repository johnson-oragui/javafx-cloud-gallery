package org.javafxCloudGallery.app.controllers;

import java.io.File;
import java.sql.ResultSet;

import org.javafcCloudGallery.database.repository.UserImageRepository;
import org.javafxCloudGallery.app.service.CloudinaryService;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GalleryController {
  @FXML
  private FlowPane imageContainer;

  private Stage primaryStage;

  private final CloudinaryService cloudinaryService = new CloudinaryService();
  private int currentUserId;

  public void initializeGallery(int userId) {
    setCurrentUserId(userId);
    loadImages();
  }

  @FXML
  private void handleUpload() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Image");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

    File selectedFile = fileChooser.showOpenDialog(imageContainer.getScene().getWindow());
    if (selectedFile != null) {
      try {
        String imageUrl = cloudinaryService.uploadImage(selectedFile.getAbsolutePath());
        UserImageRepository.saveImage(imageUrl, currentUserId, imageUrl, "Untitled", "");

        // Add new image to gallery
        ImageView imageView = createImageView(imageUrl);
        imageContainer.getChildren().add(0, imageView); // Add at beginning
      } catch (Exception e) {
        e.printStackTrace();
        // Show error message
      }
    }
  }

  private void loadImages() {
    try {
      ResultSet resultSet = UserImageRepository.getUserImages(currentUserId, 1, 20);
      while (resultSet.next()) {
        String imaString = resultSet.getString("image_url");
        ImageView imageView = createImageView(imaString);
        imageContainer.getChildren().add(imageView);

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private ImageView createImageView(String imageUrl) {
    Image image = new Image(imageUrl, 200, 200, true, true);
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(200);
    imageView.setFitHeight(200);
    imageView.setPreserveRatio(true);
    return imageView;
  }

  public void setPrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public int getCurrentUserId() {
    return currentUserId;
  }

  public void setCurrentUserId(int currentUserId) {
    this.currentUserId = currentUserId;
  }
}
