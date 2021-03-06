package com.serverless.handler.StudentHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Student;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IStudentService;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.StudentService;
import com.serverless.service.Impl.UserService;
import com.serverless.util.BearerToken;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetStudentTokenHandler implements
    RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IUserService userService = new UserService();

  private final IStudentService studentService = new StudentService();


  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      String id = null;
      Student student = null;
      int statusCode;
      if (BearerToken.getInstance().preHandle(input)) {
        id = BearerToken.getInstance().getTokenWrapper();
        logger.info(id);
        student = studentService.findStudentByToken(id);
        if (student != null) {
          statusCode = Constant.OK;
        } else {
          statusCode = Constant.NO_CONTENT;
        }
        return ApiGatewayResponse.builder()
            .setHeaders(origin)
            .setStatusCode(statusCode)
            .setObjectBody(student != null ? student : Constant.UNDEFINE)
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setHeaders(origin)
            .setStatusCode(Constant.NO_CONTENT)
            .setObjectBody("Not Authorization!")
            .build();
      }
    } catch (Exception e) {
      logger.error("Error in Get Student By Token!: " + e.getMessage());
      Response response = new Response("Error in get Student token: ", input);
      e.printStackTrace();
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
