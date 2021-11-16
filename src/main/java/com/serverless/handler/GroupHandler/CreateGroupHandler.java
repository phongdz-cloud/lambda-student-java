package com.serverless.handler.GroupHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Group;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IGroupService;
import com.serverless.service.Impl.GroupService;
import org.apache.log4j.Logger;

public class CreateGroupHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private IGroupService groupService = new GroupService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Group group = mapper.readValue(input.getBody(), Group.class);
      groupService.save(group);
      logger.debug("Group saved success!: " + group);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(group)
          .build();
    } catch (Exception ex) {
      logger.error("Error in Group code!");
      ex.printStackTrace();
      Response response = new Response("Error in saving Group!",input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
