package com.serverless.handler.TeacherHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Teacher;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.ITeacherService;
import com.serverless.service.Impl.TeacherService;
import org.apache.log4j.Logger;

public class CreateTeacherHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private ITeacherService teacherService = new TeacherService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      Teacher teacher = objectMapper.readValue(input.getBody(), Teacher.class);
      teacherService.save(teacher);
      logger.debug("Teacher saved success! " + teacher);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(teacher)
          .build();
    } catch (JsonProcessingException e) {
      logger.error("Error in create techer: " + e.getMessage());
      e.printStackTrace();
      Response response = new Response("Error in save teacher", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
