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

public class DeleteTeacherHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private final ITeacherService teacherService = new TeacherService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      Map<String, String> pathParameters = input.getPathParameters();
      String id = pathParameters.get("id");
      Teacher teacher = teacherService.findTeacherById(id);
      if (teacher != null) {
        String deleteTeacherId = teacherService.delete(teacher);
        logger.debug("Delete teacher by id: " + deleteTeacherId);
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setObjectBody("Delete Successfully!")
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.ERROR)
            .setObjectBody("Delete failed!")
            .build();
      }
    } catch (Exception ex) {
      logger.error("Error in deleted Teacher: ");
      ex.printStackTrace();
      Response response = new Response("Error deleting Teacher", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }

  }
}
