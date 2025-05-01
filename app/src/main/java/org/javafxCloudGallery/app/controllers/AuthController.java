package org.javafxCloudGallery.app.controllers;

import org.javafcCloudGallery.database.models.User;
import org.javafxCloudGallery.app.App;
import org.javafxCloudGallery.app.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthController {
  @FXML
  private TextField usernameEmailField;
  @FXML
  private TextField usernameField;
  @FXML
  private PasswordField passwordField;
  @FXML
  private PasswordField ConfirmPasswordField;
  @FXML
  private TextField emailField;
  @FXML
  private Label errorLabel;

  private Stage primaryStage;

  public AuthController() {
  }

  @FXML
  private void handleLogin() {
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
  private void handleRegister() {
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
    }
  }

  public void setPrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }
}