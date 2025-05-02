package org.javafxCloudGallery.app.controllers;

import org.javafxCloudGallery.app.App;
import org.javafxCloudGallery.app.service.UserService;
import org.javafxCloudGallery.database.models.User;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AuthController {
  // FXML elements for the Login Form
  @FXML
  private TextField usernameEmailField;
  @FXML
  private PasswordField passwordField; // shared with register FXML

  // FXML elements for the Register Form
  @FXML
  private TextField usernameField;
  @FXML
  private PasswordField ConfirmPasswordField;
  @FXML
  private TextField emailField;

  @FXML
  private Label errorLabel;

  // FXML elements for Navigation
  @FXML
  private HBox navbar;
  @FXML
  private Button hamburgerButton;
  @FXML
  private VBox hamburgerMenu;

  private static final double HAMBURGER_BREAKPOINT = 768; // Define breakpoint

  private Stage primaryStage;

  @FXML
  public void initialize() {
    // Initial state setting for responsiveness happens *after* the scene is
    // attached
    // The setupResponsiveness method will handle this.
    // can set default focus here if needed.
  }

  /**
   * Will Call this method from main Application class after setting the scene.
   * It sets up the listener for window resizing.
   */
  public void setupResponsiveness(Scene scene) {
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

  // --- Handlers for the Login Form Buttons ---

  @FXML
  private void handleLoginForm() {
    System.out.println("Attempting Login...");
    try {
      User user = UserService.authenticate(usernameEmailField.getText(), passwordField.getText());
      // Successful login - switch to main application
      App.showGalleryScreen(user.getId());
    } catch (Exception e) {
      errorLabel.setText("Login failed: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @FXML
  private void handleRegisterForm() {
    try {
      UserService.registerUser(usernameField.getText(), emailField.getText(),
          passwordField.getText(), ConfirmPasswordField.getText());

      // switch to login
      switchToLogin();

    } catch (Exception e) {
      errorLabel.setText("Register failed: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @FXML
  private void switchToLogin() {
    try {
      App.showLoginScreen();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void switchToRegister() {
    try {
      App.showRegisterScreen();
    } catch (Exception e) {
      e.printStackTrace();
      // Handle potential error showing register screen
      errorLabel.setText("Could not switch to register page.");
    }
  }

  // --- Handlers for Navigation Buttons (Navbar and Hamburger) ---

  @FXML
  private void handleHome() {
    System.out.println("Navigating to Home");
    App.showLandingPage();
    closeHamburgerMenuIfOpen(); // Close menu after clicking
  }

  @FXML
  private void handleLoginNav() throws Exception {
    System.out.println("Navigating to Login");
    App.showLoginScreen();
    closeHamburgerMenuIfOpen(); // Close menu after clicking
  }

  @FXML
  private void handleRegisterNav() {
    System.out.println("Navigating to Register");
    switchToRegister(); // Calls the method that uses App.showRegisterScreen()
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
}