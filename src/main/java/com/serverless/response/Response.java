package com.serverless.response;

import java.util.Map;

public class Response {

  private final String message;
  private Boolean success;
  private String token;
  private final Map<String, Object> input;


  public Response(String message, Map<String, Object> input) {
    this.message = message;
    this.input = input;
  }

  public String getMessage() {
    return this.message;
  }

  public Map<String, Object> getInput() {
    return this.input;
  }

  public Boolean getSuccess() {
    return success;
  }

  public String getToken() {
    return token;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
