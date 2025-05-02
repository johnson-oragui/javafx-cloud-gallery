package org.javafxCloudGallery.app.service;

import java.util.Optional;

import org.javafxCloudGallery.database.models.User;
import org.javafxCloudGallery.database.repository.UserRepository;
import org.javafxCloudGallery.utilities.PasswordUtil;
import org.javafxCloudGallery.utilities.Tuple;
import org.javafxCloudGallery.utilities.ValidationUtil;

public class UserService {
  public static void registerUser(String username, String email, String password, String confirmPassword)
      throws Exception {
    if (username == null || email == null || password == null) {
      throw new Exception("username, email, and password must be provided");
    }
    if (!password.equals(confirmPassword)) {
      throw new Exception("password and confirm password must match");
    }

    Tuple<String, String, String> tuple = ValidationUtil.validator(username, email, password);

    String hashedPassword = PasswordUtil.hashPassword(tuple.getPassword());

    Boolean isCreated = UserRepository.createNewUser(tuple.getUsername(), hashedPassword, tuple.getEmail());

    if (!isCreated) {
      throw new Exception("Something went wrong. User could not be registered");
    }

  }

  public static User authenticate(String usernameOrEMail, String password) throws Exception {
    if (usernameOrEMail.trim().length() < 3) {
      throw new Exception("username or email must have length upto three(3)");
    }
    Optional<User> foundUser = UserRepository.findUserByEmailOrUsername(usernameOrEMail);
    if (foundUser.isPresent()) {
      User user = foundUser.get();
      Boolean isPasswordValid = PasswordUtil.verifyPassword(user.getPassword(), password);
      if (isPasswordValid) {
        return user;
      }
    }
    throw new Exception("invalid credentials");

  }
}
