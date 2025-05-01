package org.javafxCloudGallery.utilities;

import io.github.cdimascio.dotenv.Dotenv;

public class MyConfig {
  public static final Dotenv dotenv = Dotenv.load();
  public static final String DB_URL = dotenv.get("DB_URL");
  public static final String DB_USER = dotenv.get("POSTGRES_USER");
  public static final String DB_PASSWORD = dotenv.get("POSTGRES_PASSWORD");

  public static final String CLOUDINARY_CLOUD_NAME = dotenv.get("CLOUDINARY_CLOUD_NAME");
  public static final String CLOUDINARY_API_KEY = dotenv.get("CLOUDINARY_API_KEY");
  public static final String CLOUDINARY_API_SECRET = dotenv.get("CLOUDINARY_API_SECRET");

}
