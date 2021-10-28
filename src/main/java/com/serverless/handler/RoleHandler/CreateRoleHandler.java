package com.serverless.handler.RoleHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Role;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IRoleService;
import com.serverless.service.Impl.RoleService;
import java.util.Map;
import org.apache.log4j.Logger;

public class CreateRoleHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private IRoleService roleService = new RoleService();

  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    try {
      JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
      Role role = new Role();
      role.setName(body.get("name").asText());
      roleService.save(role);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(role)
          .build();
    } catch (Exception e) {
      logger.error("Error in saving role: " + e);
      Response responseBody = new Response("Error in saving Role!", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(responseBody)
          .build();
    }
  }
}
