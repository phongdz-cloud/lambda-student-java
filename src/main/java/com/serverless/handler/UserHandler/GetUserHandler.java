package com.serverless.handler.UserHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.User;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.UserService;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetUserHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private IUserService userService = new UserService();


  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      Map<String, String> pathParameters = input.getPathParameters();
      String username = pathParameters.get("id");
      User user = userService.findUserById(username);
      if (user != null) {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setObjectBody(user)
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.NO_CONTENT)
            .setObjectBody(Constant.UNDEFINE)
            .build();
      }
    } catch (Exception ex) {
      logger.error("Error in retrieving user: " + ex.getMessage());
      Response responseBody = new Response("Error in retrieving user: ",
           input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(responseBody)
          .build();
    }
  }
}
