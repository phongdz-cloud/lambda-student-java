package com.serverless.handler.UserHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.User;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.UserService;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class UpdateUserHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private final IUserService userService = new UserService();


  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      User oddUser = objectMapper.readValue(input.getBody(), User.class);
      logger.info("odduser: " + oddUser);
      User newUser = userService.findUserByUsername(oddUser.getUsername());
      if (newUser != null) {
        logger.info("newUser: " + newUser);
        newUser.setAvatar(oddUser.getAvatar());
        userService.save(newUser);
        return ApiGatewayResponse.builder()
            .setHeaders(origin)
            .setStatusCode(Constant.OK)
            .setObjectBody(newUser)
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setHeaders(origin)
            .setStatusCode(Constant.NO_CONTENT)
            .setObjectBody("User empty by id")
            .build();
      }
    } catch (Exception ex) {
      logger.error("Error in update user: " + ex.getMessage());
      Response response = new Response("Error in saving user!", input);
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
