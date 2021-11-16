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
import com.serverless.util.BearerToken;
import org.apache.log4j.Logger;

public class GetTeacherTokenHandler implements
    RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final ITeacherService teacherService = new TeacherService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      String id = null;
      Teacher teacher = null;
      int statusCode;
      if (BearerToken.getInstance().preHandle(input)) {
        id = BearerToken.getInstance().getTokenWrapper();
        logger.info(id);
        teacher = teacherService.findTeacherByToken(id);
        if (teacher != null) {
          statusCode = Constant.OK;
        } else {
          statusCode = Constant.NO_CONTENT;
        }
        return ApiGatewayResponse.builder()
            .setStatusCode(statusCode)
            .setObjectBody(teacher != null ? teacher : Constant.UNDEFINE)
            .build();
      }else{
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.NO_CONTENT)
            .setObjectBody("Not Authorization")
            .build();
      }
    } catch (Exception e) {
      logger.error("Error in Get teacher by token: ");
      Response response = new Response("Error in get Teacher token", input);
      e.printStackTrace();
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
