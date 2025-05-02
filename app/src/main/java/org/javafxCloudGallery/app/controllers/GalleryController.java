package org.javafxCloudGallery.app.controllers;

import java.io.File;
import java.sql.ResultSet;

import org.javafxCloudGallery.app.App;
import org.javafxCloudGallery.app.service.CloudinaryService;
import org.javafxCloudGallery.database.repository.UserImageRepository;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GalleryController {
  @FXML
  private FlowPane imageContainer;

  // FXML elements for navigation
  @FXML
  private HBox navbar;
  @FXML
  private Button hamburgerButton;
  @FXML
  private VBox hamburgerMenu;

  private static final double HAMBURGER_BREAKPOINT = 768; // Define breakpoint

  @FXML
  private Label errorLabel;

  private Stage primaryStage;

  private final CloudinaryService cloudinaryService = new CloudinaryService();
  private int currentUserId;

  /*
   * Initial state setting for responsiveness happens *after* the scene is
   * attached
   * 
   * Load images when the controller is initialized (or when user ID is set)
   */
  public void initializeGallery(int userId, Scene scene) {
    setCurrentUserId(userId);
    setupResponsiveness(scene);
    loadImages();
  }

  @FXML
  public void initialize() {

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
        closeHamburgerMenuIfOpen(); // Close menu after action
      } catch (Exception e) {
        e.printStackTrace();
        errorLabel.setText(e.getMessage());
      }
    }
  }

  private void loadImages() {
    try {
      ResultSet resultSet = UserImageRepository.getUserImages(currentUserId, 1, 20);
      // Clear existing images
      imageContainer.getChildren().clear();
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
    imageView.setStyle("image-view"); // apply css class

    // Add click listener to images (optional)
    imageView.setOnMouseClicked(event -> handleImageClick(imageView));

    return imageView;
  }

  /**
   * Sets up the listener for window resizing.
   */
  private void setupResponsiveness(Scene scene) {
    // Add a listener to the scene width property
    scene.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double currentWidth = newValue.doubleValue();
        updateNavigationVisibility(currentWidth);
      }
    });

    // Trigger the listener once to set the initial state based on current width
    updateNavigationVisibility(scene.getWidth());
  }

  // Helper method to manage visibility based on width
  private void updateNavigationVisibility(double width) {
    if (width > HAMBURGER_BREAKPOINT) {
      // Wide screen: Show navbar, hide hamburger elements
      navbar.setVisible(true);
      navbar.setManaged(true);
      hamburgerButton.setVisible(false);
      hamburgerButton.setManaged(false);
      // Ensure hamburger menu is hidden if window resized while it was open
      hamburgerMenu.setVisible(false);
      hamburgerMenu.setManaged(false);
    } else {
      // Small screen: Hide navbar, show hamburger button
      navbar.setVisible(false);
      navbar.setManaged(false);
      hamburgerButton.setVisible(true);
      hamburgerButton.setManaged(true);
      // Hamburger menu visibility is controlled by the button click
    }
  }

  // --- Gallery Specific Logic ---

  private void handleImageClick(ImageView imageView) {
    System.out.println("Image clicked: " + imageView.getImage().getUrl());
    // Add logic to view image full size, etc.
  }

  @FXML
  private void handleHome() {
    System.out.println("Navigating to Home");
    // TODO: Add logic to navigate to the Home screen
    App.showLandingPage();
    closeHamburgerMenuIfOpen(); // Close menu after clicking
  }

  @FXML
  private void handleGalleryNav() {
    System.out.println("Navigating to Gallery (Already here)");
    // already on the gallery page
    closeHamburgerMenuIfOpen(); // Close menu after clicking
  }

  @FXML
  private void handleAccount() {
    System.out.println("Navigating to Account");
    // Add logic to navigate to the Account/Profile screen
    // App.showAccountScreen(currentUserId); not yet implemented
    closeHamburgerMenuIfOpen(); // Close menu after clicking
  }

  @FXML
  private void handleLogout() throws Exception {
    System.out.println("Logging out...");
    App.setUserId(0);
    App.showLoginScreen();
    closeHamburgerMenuIfOpen(); // Close menu after clicking
  }

  // --- Handlers for Hamburger Menu Toggle ---

  @FXML
  private void toggleHamburgerMenu() {
    boolean isVisible = hamburgerMenu.isVisible();
    hamburgerMenu.setVisible(!isVisible);
    hamburgerMenu.setManaged(!isVisible); // Toggle managed property too
  }

  // Helper to close the hamburger menu
  private void closeHamburgerMenuIfOpen() {
    if (hamburgerMenu.isVisible()) {
      toggleHamburgerMenu();
    }
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
