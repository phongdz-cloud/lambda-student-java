package com.serverless.handler.UserHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Role;
import com.serverless.model.User;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IRoleService;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.RoleService;
import com.serverless.service.Impl.UserService;
import java.util.Map;
import org.apache.log4j.Logger;

public class CreateUserHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private IRoleService roleService = new RoleService();

  private IUserService userService = new UserService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Response responseBody = null;
    try {
      ObjectMapper mapper = new ObjectMapper();
      Role role = roleService.findRoleByName("user");
      User user = mapper.readValue((String) input.getBody(), User.class);
      user.setRole(role);
      if (userService.existsUserByUsername(user.getUsername())) {
        userService.save(user);
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setObjectBody(user)
            .build();
      } else {
        logger.info("User exists!");
        responseBody = new Response("Username exists!",  input);
      }
    } catch (Exception e) {
      logger.error("Error in saving user: " + e.getMessage());
      responseBody = new Response("Error in saving User!" + e.getMessage(),
           input);
    }
    return ApiGatewayResponse.builder()
        .setStatusCode(Constant.ERROR)
        .setObjectBody(responseBody)
        .build();
  }
}
