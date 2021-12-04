package com.serverless.response;

import java.util.Map;

public class Response {

  private final String message;
  private Boolean success;
  private String token;
  //  private final Map<String, Object> input;
  private Map<String, String> headers;


  public Response(String message, ApiGatewayRequest input) {
    this.message = message;
    this.headers = input.getHeaders();
  }


  public String getMessage() {
    return this.message;
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

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }
}
