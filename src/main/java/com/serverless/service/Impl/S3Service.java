package com.serverless.service.Impl;

import com.serverless.dao.Impl.S3DAO;
import com.serverless.pojo.Base64ImagePojo;
import com.serverless.service.IS3Service;
import java.util.List;

public class S3Service implements IS3Service {


  private final S3DAO s3DAO = S3DAO.getInstance();

  @Override
  public String uploadS3Object(Base64ImagePojo base64ImagePojo) {
    return s3DAO.uploadS3Object(base64ImagePojo);
  }

  @Override
  public Boolean deleteS3Object(String object_keys) {
    return s3DAO.deleteS3Object(object_keys);
  }

  @Override
  public void createS3Bucket(String bucket_name) {
    s3DAO.createS3Bucket(bucket_name);
  }

  @Override
  public List<String> findAllS3Name(String bucket_name) {
    return s3DAO.findAllS3Name(bucket_name);
  }
}
