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
import java.util.List;
import org.apache.log4j.Logger;

public class ListTeacherHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private final ITeacherService teacherService = new TeacherService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      List<Teacher> teachers = teacherService.findAll();
      logger.info("List Teachers: " + teachers.size());
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(teachers)
          .build();
    } catch (Exception e) {
      logger.error("Error in List Teacher! " + e.getMessage());
      Response response = new Response("Error in list Teacher: ", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.NO_CONTENT)
          .setObjectBody(response)
          .build();
    }
  }
}
