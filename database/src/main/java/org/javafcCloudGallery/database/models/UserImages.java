package org.javafcCloudGallery.database.models;

public class UserImages {
  private int id;
  private int user_id;
  private String image_url;
  private String thumbnail_url;
  private String title;
  private String description;

  public UserImages() {
  }

  public UserImages(int id, int user_id, String image_url, String thumnnail_url, String title, String description) {
    this.id = id;
    this.user_id = user_id;
    this.thumbnail_url = thumnnail_url;
    this.title = title;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public String getImage_url() {
    return image_url;
  }

  public void setImage_url(String image_url) {
    this.image_url = image_url;
  }

  public String getThumbnail_url() {
    return thumbnail_url;
  }

  public void setThumbnail_url(String thumbnail_url) {
    this.thumbnail_url = thumbnail_url;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}
