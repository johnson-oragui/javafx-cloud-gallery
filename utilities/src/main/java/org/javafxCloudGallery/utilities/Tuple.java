package org.javafxCloudGallery.utilities;

public class Tuple<X, Y, Z> {
  private X username;
  private Y email;
  private Z password;

  public Tuple() {
  }

  public Tuple(X username, Y email, Z password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public void setEmail(Y email) {
    this.email = email;
  }

  public Y getEmail() {
    return email;
  }

  public void setUsername(X username) {
    this.username = username;
  }

  public X getUsername() {
    return username;
  }

  public void setPassword(Z password) {
    this.password = password;
  }

  public Z getPassword() {
    return password;
  }

}
