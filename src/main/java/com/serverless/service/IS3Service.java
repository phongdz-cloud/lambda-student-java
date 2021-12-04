package com.serverless.service;

import com.serverless.pojo.Base64ImagePojo;
import java.util.List;

public interface IS3Service {

  String uploadS3Object(Base64ImagePojo base64ImagePojo);

  Boolean deleteS3Object(String object_keys);

  void createS3Bucket(String bucket_name);

  List<String> findAllS3Name(String bucket_name);
}
