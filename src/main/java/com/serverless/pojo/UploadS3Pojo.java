package com.serverless.pojo;

public class UploadS3Pojo {

  private String id;

  private String base64Image;

  private String urlS3;

  public UploadS3Pojo() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBase64Image() {
    return base64Image;
  }

  public void setBase64Image(String base64Image) {
    this.base64Image = base64Image;
  }

  public String getUrlS3() {
    return urlS3;
  }

  public void setUrlS3(String urlS3) {
    this.urlS3 = urlS3;
  }
}
