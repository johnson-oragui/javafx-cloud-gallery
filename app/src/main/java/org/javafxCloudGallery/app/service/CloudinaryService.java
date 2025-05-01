package org.javafxCloudGallery.app.service;

import java.io.IOException;
import java.util.Map;

import org.javafxCloudGallery.utilities.MyConfig;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryService {
  private final Cloudinary cloudinary;

  public CloudinaryService() {
    cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", MyConfig.CLOUDINARY_CLOUD_NAME,
        "api_key", MyConfig.CLOUDINARY_API_KEY,
        "api_secret", MyConfig.CLOUDINARY_API_SECRET,
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
