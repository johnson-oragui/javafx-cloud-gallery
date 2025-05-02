package org.javafxCloudGallery.app.controllers;

import org.javafxCloudGallery.app.App;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LandingPageController {
  private Stage primaryStage;

  @FXML
  private HBox navbar;
  @FXML
  private Button hamburgerButton;
  @FXML
  private VBox hamburgerMenu;

  private static final double HAMBURGER_BREAKPOINT = 768; // Define the screen width mobile-view

  @FXML
  public void initialize() {
    // Initial state: Assume wide screen, show navbar, hide hamburger elements
    navbar.setVisible(true);
    navbar.setManaged(true);
    hamburgerButton.setVisible(false);
    hamburgerButton.setManaged(false);
    hamburgerMenu.setVisible(false);
    hamburgerMenu.setManaged(false);

    // i need access to the scene to add a width listener.
    // This can only be done *after* the scene is set on the stage.
    // So, i'll add the listener logic in a method called from the main App class.
  }

  /**
   * Will Call this method from main Application class after setting the
   * scene.
   */
  public void setupResponsiveness(Scene scene) {
    // Add a listener to the scene width property
    scene.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double currentWidth = newValue.doubleValue();
        if (currentWidth > HAMBURGER_BREAKPOINT) {
          // Wide screen: Show navbar, hide hamburger
          navbar.setVisible(true);
          navbar.setManaged(true);
          hamburgerButton.setVisible(false);
          hamburgerButton.setManaged(false);
          // Ensure menu is also hidden when switching back to wide
          hamburgerMenu.setVisible(false);
          hamburgerMenu.setManaged(false);
        } else {
          // Small screen: Hide navbar, show hamburger button
          navbar.setVisible(false);
          navbar.setManaged(false);
          hamburgerButton.setVisible(true);
          hamburgerButton.setManaged(true);
          // The hamburger menu itself is toggled by the button
        }
      }
    });

    // Trigger the listener once to set the initial state based on current width
    updateNavigationVisibility(scene.getWidth());
  }

  private void updateNavigationVisibility(double width) {
    if (width > HAMBURGER_BREAKPOINT) {
      navbar.setVisible(true);
      navbar.setManaged(true);
      hamburgerButton.setVisible(false);
      hamburgerButton.setManaged(false);
      hamburgerMenu.setVisible(false);
      hamburgerMenu.setManaged(false);
    } else {
      navbar.setVisible(false);
      navbar.setManaged(false);
      hamburgerButton.setVisible(true);
      hamburgerButton.setManaged(true);
      // hamburgerMenu visibility is handled by the button click
    }
  }

  @FXML
  private void toggleHamburgerMenu() {
    boolean isVisible = hamburgerMenu.isVisible();
    hamburgerMenu.setVisible(!isVisible);
    hamburgerMenu.setManaged(!isVisible); // Toggle managed property too
  }

  // --- Navigation Handlers ---
  // TODO: Implement these methods to navigate or perform actions

  @FXML
  private void handleHome() {
    System.out.println("Navigating to Home");
    App.showLandingPage();
    if (hamburgerMenu.isVisible()) { // Close menu after click if open
      toggleHamburgerMenu();
    }
  }

  @FXML
  private void handleShowLogin() throws Exception {
    System.out.println("Navigating to Login");
    int userId = App.getUserId();
    if (userId > 0) {
      App.showGalleryScreen(userId);
      if (hamburgerMenu.isVisible()) {
        toggleHamburgerMenu();
      }
      return;
    }
    App.showLoginScreen();
    if (hamburgerMenu.isVisible()) {
      toggleHamburgerMenu();
    }
  }

  @FXML
  private void handleShowRegister() throws Exception {
    System.out.println("Navigating to Register");
    App.showRegisterScreen();
    if (hamburgerMenu.isVisible()) {
      toggleHamburgerMenu();
    }
  }

  @FXML
  private void handleShowGallery() throws Exception {
    System.out.println("Navigating to Gallery");
    int userId = App.getUserId();
    if (userId < 1) {
      App.showLoginScreen();
      if (hamburgerMenu.isVisible()) {
        toggleHamburgerMenu();
      }
      return;
    }
    App.showGalleryScreen(userId);
    if (hamburgerMenu.isVisible()) {
      toggleHamburgerMenu();
    }
  }

  @FXML
  private void handleGetStarted() {
    System.out.println("Clicked Get Started");
    // TODO: Add logic for the main CTA button
  }

  public void setPrimaryStage(Stage stage) {
    this.primaryStage = stage;
  }

  public Stage getPrimaryStage() {
    return this.primaryStage;
  }
}
