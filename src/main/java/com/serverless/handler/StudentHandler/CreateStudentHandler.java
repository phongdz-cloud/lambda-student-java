package com.serverless.handler.StudentHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Student;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IStudentService;
import com.serverless.service.Impl.StudentService;
import java.util.Map;
import org.apache.log4j.Logger;

public class CreateStudentHandler implements
    RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private IStudentService studentService = new StudentService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      Student student = objectMapper.readValue(input.getBody(), Student.class);
      studentService.save(student);
      logger.debug("Student saved success! " + student.toString());
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(student)
          .build();
    } catch (Exception ex) {
      logger.error("Error in tour code! " + ex.getMessage());
      Response responseBody = new Response("Error in saving Student! " + ex.getMessage(),
           input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(responseBody)
          .build();
    }
  }
}
