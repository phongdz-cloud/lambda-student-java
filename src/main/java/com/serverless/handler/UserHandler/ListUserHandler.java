package com.serverless.handler.UserHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.UserService;
import java.util.Map;
import org.apache.log4j.Logger;

public class ListUserHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private IUserService userService = new UserService();

  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    try {
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(userService.findAll())
          .build();
    } catch (Exception ex) {
      logger.error("Error in list! " + ex);
      Response response = new Response("Error in list User: ", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
