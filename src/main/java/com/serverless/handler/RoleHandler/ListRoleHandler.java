package com.serverless.handler.RoleHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IRoleService;
import com.serverless.service.Impl.RoleService;
import java.util.Map;
import org.apache.log4j.Logger;

public class ListRoleHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private IRoleService roleService = new RoleService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(roleService.findAll())
          .build();
    } catch (Exception e) {
      logger.error("Error in listing roles: " + e);
      Response responseBody = new Response("Error in listing role! ", (Map<String, Object>) input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(responseBody)
          .build();
    }
  }
}
