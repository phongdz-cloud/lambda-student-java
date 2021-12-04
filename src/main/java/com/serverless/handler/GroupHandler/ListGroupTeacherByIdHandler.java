package com.serverless.handler.GroupHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Student;
import com.serverless.model.Teacher;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IGroupService;
import com.serverless.service.Impl.GroupService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ListGroupTeacherByIdHandler implements
    RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IGroupService groupService = new GroupService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      Map<String, String> parameters = input.getPathParameters();
      String id = parameters.get("id");
      Map<Teacher, List<Student>> teacherListMap = groupService.findTeacherAndStudentsBySubjectId(
          id);
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.OK)
          .setObjectBody(teacherListMap)
          .build();
    } catch (Exception ex) {
      logger.error("Error in teacher list by subject ID");
      ex.printStackTrace();
      Response response = new Response("Error in get teacher list!",input);
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
