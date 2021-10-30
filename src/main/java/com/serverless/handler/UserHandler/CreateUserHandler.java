package com.serverless.handler.UserHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Role;
import com.serverless.model.User;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IRoleService;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.RoleService;
import com.serverless.service.Impl.UserService;
import java.util.Map;
import org.apache.log4j.Logger;

public class CreateUserHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private IRoleService roleService = new RoleService();

  private IUserService userService = new UserService();

  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    Response responseBody = null;
    try {
      JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
      Role role = roleService.findRoleByName("user");
      User user = new User();
      user.setUsername(body.get("username").asText());
      user.setPassword(body.get("password").asText());
      user.setRole(role);
      if (userService.existsUserByUsername(user.getUsername())) {
        userService.save(user);
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setObjectBody(user)
            .build();
      } else {
        logger.info("User exists!");
        responseBody = new Response("Username exists!", input);
      }
    } catch (Exception e) {
      logger.error("Error in saving user: " + e.getMessage());
      responseBody = new Response("Error in saving User!" + e.getMessage(), input);
    }
    return ApiGatewayResponse.builder()
        .setStatusCode(Constant.ERROR)
        .setObjectBody(responseBody)
        .build();
  }
}
