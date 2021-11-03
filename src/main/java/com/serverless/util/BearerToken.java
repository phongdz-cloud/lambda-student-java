package com.serverless.util;

import com.serverless.response.ApiGatewayRequest;

public class BearerToken {

  private String tokenWrapper;

  private static BearerToken bearerToken = null;

  public static BearerToken getInstance() {
    if (bearerToken == null) {
      bearerToken = new BearerToken();
    }
    return bearerToken;
  }

  public boolean preHandle(ApiGatewayRequest request) {
    final String authorizationHeader = request.getHeaders().get("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
      String token = authorizationHeader.substring(7, authorizationHeader.length());
      bearerToken.setTokenWrapper(token);
      return true;
    }
    return false;
  }


  public String getTokenWrapper() {
    return tokenWrapper;
  }

  public void setTokenWrapper(String tokenWrapper) {
    this.tokenWrapper = tokenWrapper;
  }
}
