package com.serverless.handler.StudentHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Student;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IStudentService;
import com.serverless.service.Impl.StudentService;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetStudentHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private IStudentService studentService = new StudentService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      Map<String, String> pathParameters = input.getPathParameters();
      String id = pathParameters.get("id");
      Student student = studentService.findStudentById(id);
      if (student != null) {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setObjectBody(student)
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.NO_CONTENT)
            .setObjectBody(Constant.UNDEFINE)
            .build();
      }
    } catch (Exception e) {
      logger.error("Error in Get Student: " + e.getMessage());
      Response response = new Response("Error in get Student: ", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
