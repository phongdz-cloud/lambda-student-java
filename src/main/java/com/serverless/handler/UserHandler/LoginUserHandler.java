package com.serverless.handler.UserHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.User;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.UserService;
import com.serverless.util.JWTUtil;
import java.util.Map;
import org.apache.log4j.Logger;

public class LoginUserHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private IUserService userService = new UserService();

  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    try {
      JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
      User user = new User();
      user.setUsername(body.get("username").asText());
      user.setPassword(body.get("password").asText());
      if (userService.existsUserByUsernameAndPassword(user)) {
        Response responseBody = new Response("Login success!", input);
        responseBody.setSuccess(Boolean.TRUE);
        responseBody.setToken(
            JWTUtil.createJWT(user.getId(), user.getUsername(), user.getPassword(), 1L));
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setObjectBody(responseBody)
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.NO_CONTENT)
            .setObjectBody("Login faild!")
            .build();
      }
    } catch (Exception ex) {
      logger.error("Thrown Exception: " + ex.getMessage());
      Response response = new Response("Error login user: ", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
