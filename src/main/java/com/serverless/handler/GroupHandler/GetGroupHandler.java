package com.serverless.handler.GroupHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Group;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IGroupService;
import com.serverless.service.Impl.GroupService;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetGroupHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IGroupService groupService = new GroupService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      Map<String, String> pathParameters = input.getPathParameters();
      String id = pathParameters.get("id");
      Group group = groupService.findGroupById(id);
      if (group != null) {
        return ApiGatewayResponse.builder()
            .setHeaders(origin)
            .setStatusCode(Constant.OK)
            .setObjectBody(group)
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setHeaders(origin)
            .setStatusCode(Constant.NO_CONTENT)
            .setObjectBody(Constant.UNDEFINE)
            .build();
      }
    } catch (Exception ex) {
      logger.error("Error in get Group by id: " + ex.getMessage());
      Response response = new Response("Error in get group by id: ", input);
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
