package org.javafxCloudGallery.app.service;

import java.io.IOException;
import java.util.Map;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import io.github.cdimascio.dotenv.Dotenv;

public class CloudinaryService {
  private final Cloudinary cloudinary;
  private static final Dotenv dotenv = Dotenv.load();
  private static final String CLOUDINARY_CLOUD_NAME = dotenv.get("CLOUDINARY_CLOUD_NAME");
  private static final String CLOUDINARY_API_KEY = dotenv.get("CLOUDINARY_API_KEY");
  private static final String CLOUDINARY_API_SECRET = dotenv.get("CLOUDINARY_API_SECRET");

  public CloudinaryService() {
    cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", CLOUDINARY_CLOUD_NAME,
        "api_key", CLOUDINARY_API_KEY,
        "api_secret", CLOUDINARY_API_SECRET,
        "secure", true));
  }

  public String uploadImage(String filePath) throws IOException {
    Map<?, ?> uploadResult = cloudinary.uploader().upload(filePath, ObjectUtils.emptyMap());
    return (String) uploadResult.get("url");
  }

  public String uploadImage(byte[] fileData) throws IOException {
    Map<?, ?> uploadResult = cloudinary.uploader().upload(fileData, ObjectUtils.emptyMap());
    return (String) uploadResult.get("url");
  }
}
