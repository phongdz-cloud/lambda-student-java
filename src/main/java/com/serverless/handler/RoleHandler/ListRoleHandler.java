package com.serverless.handler.RoleHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IRoleService;
import com.serverless.service.Impl.RoleService;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class ListRoleHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private IRoleService roleService = new RoleService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setHeaders(origin)
          .setObjectBody(roleService.findAll())
          .build();
    } catch (Exception e) {
      logger.error("Error in listing roles: " + e);
      Response responseBody = new Response("Error in listing role! ", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setHeaders(origin)
          .setObjectBody(responseBody)
          .build();
    }
  }
}
