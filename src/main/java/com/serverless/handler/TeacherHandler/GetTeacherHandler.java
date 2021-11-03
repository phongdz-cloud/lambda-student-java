package com.serverless.handler.TeacherHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Teacher;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.ITeacherService;
import com.serverless.service.Impl.TeacherService;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetTeacherHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private ITeacherService teacherService = new TeacherService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      Map<String, String> pathParameters = input.getPathParameters();
      String id = pathParameters.get("id");
      Teacher teacher = teacherService.findTeacherById(id);
      if (teacher != null) {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setObjectBody(teacher)
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.NO_CONTENT)
            .setObjectBody(Constant.UNDEFINE)
            .build();
      }
    } catch (Exception e) {
      logger.error("Error in Get Student: " + e.getMessage());
      Response response = new Response("Error in get Teacher: ", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
