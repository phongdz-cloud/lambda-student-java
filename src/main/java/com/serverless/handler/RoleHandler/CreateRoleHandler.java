package com.serverless.handler.RoleHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Role;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IRoleService;
import com.serverless.service.Impl.RoleService;
import java.util.Map;
import org.apache.log4j.Logger;

public class CreateRoleHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private IRoleService roleService = new RoleService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Role role = mapper.readValue(input.getBody(), Role.class);
      roleService.save(role);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(role)
          .build();
    } catch (Exception e) {
      logger.error("Error in saving role: " + e);
      Response responseBody = new Response("Error in saving Role!", (Map<String, Object>) input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(responseBody)
          .build();
    }
  }
}
