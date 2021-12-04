package com.serverless.dao.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.serverless.dao.IS3DAO;
import com.serverless.pojo.Base64ImagePojo;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class S3DAO implements IS3DAO {

  private static S3DAO s3DAO = null;

  private final String bucket_name = "bucketserverphong";

  private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().build();

  public static S3DAO getInstance() {
    if (s3DAO == null) {
      s3DAO = new S3DAO();
    }
    return s3DAO;
  }

  @Override
  public String uploadS3Object(Base64ImagePojo base64ImagePojo) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(base64ImagePojo.getFileType());
    try {
      PutObjectRequest putObjectRequest = new PutObjectRequest(bucket_name,
          base64ImagePojo.getFileName(), new ByteArrayInputStream(base64ImagePojo.getImageBytes()),
          objectMetadata);
      AccessControlList acl = new AccessControlList();
      acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
      putObjectRequest.setAccessControlList(acl);
      amazonS3.putObject(putObjectRequest);
      return String.valueOf(amazonS3.getUrl(bucket_name, base64ImagePojo.getFileName()));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Override
  public Boolean deleteS3Object(String object_keys) {
    try {
      amazonS3.deleteObject(bucket_name, object_keys);
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  @Override
  public void createS3Bucket(String bucket_name) {
    try {
      amazonS3.createBucket(bucket_name);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public List<String> findAllS3Name(String bucket_name) {
    List<String> bucketName = new ArrayList<>();
    try {
      List<Bucket> buckets = amazonS3.listBuckets();
      for (Bucket b : buckets) {
        bucketName.add(b.getName());
      }
      return bucketName;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return bucketName;
  }

  public static S3DAO getS3DAO() {
    return s3DAO;
  }

  public static void setS3DAO(S3DAO s3DAO) {
    S3DAO.s3DAO = s3DAO;
  }

  public String getBucket_name() {
    return bucket_name;
  }
}
