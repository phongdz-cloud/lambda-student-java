package com.serverless.handler.StudentHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Student;
import com.serverless.model.User;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IStudentService;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.StudentService;
import com.serverless.service.Impl.UserService;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class DeleteStudentHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private IStudentService studentService = new StudentService();
  private IUserService userService = new UserService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      Map<String, String> pathParameters = input.getPathParameters();
      String id = pathParameters.get("id");
      Student student = studentService.findStudentById(id);
      Map<String, Boolean> success = new HashMap<>();
      Integer statusCode = Constant.ERROR;
      logger.info(student);
      if (student != null) {
        String deleteId = studentService.delete(student);
        User user = userService.findUserById(student.getUserId());
        if (user != null) {
          userService.delete(user);
        }
        logger.info("Delete by id: " + deleteId);
        success.put("success", Boolean.TRUE);
        statusCode = Constant.OK;
      } else {
        success.put("success", Boolean.FALSE);
      }
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(statusCode)
          .setObjectBody(success)
          .build();
    } catch (Exception ex) {
      logger.error("Error in deleted Student!: " + ex.getMessage());
      Response responseBody = new Response("Error deleting Student", input);
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.ERROR)
          .setObjectBody(responseBody)
          .build();
    }
  }
}
