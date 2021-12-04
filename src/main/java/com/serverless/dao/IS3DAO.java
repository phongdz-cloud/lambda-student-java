package com.serverless.dao;

import com.serverless.pojo.Base64ImagePojo;
import java.util.List;

public interface IS3DAO {

  String uploadS3Object(Base64ImagePojo base64ImagePojo);

  Boolean deleteS3Object(String object_keys);

  void createS3Bucket(String bucket_name);

  List<String> findAllS3Name(String bucket_name);

}
