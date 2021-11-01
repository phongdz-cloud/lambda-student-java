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
import org.apache.log4j.Logger;

public class UpdateStudentHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private IStudentService studentService = new StudentService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Student newStudent = mapper.readValue(input.getBody(), Student.class);
      if (studentService.update(newStudent.getId(), newStudent) != null) {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setObjectBody(newStudent)
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.ERROR)
            .setObjectBody("Update failed!")
            .build();
      }
    } catch (Exception ex) {
      logger.error("Error in update Student: " + ex.getMessage());
      Response response = new Response("Error in saving Student! ", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
