package com.serverless.handler.GroupHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Subject;
import com.serverless.model.Teacher;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IGroupService;
import com.serverless.service.Impl.GroupService;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ListGroupTeacherHandler implements
    RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private final IGroupService groupService = new GroupService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      Map<Teacher, List<Subject>> teacherListMap = groupService.findAllSubjectByTeacher();
      logger.info("List teacher and subject: " + teacherListMap.size());
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(teacherListMap)
          .build();
    } catch (Exception ex) {
      logger.error("Error in Teacher and subject");
      ex.printStackTrace();
      Response response = new Response("Error in teacher and subject: ", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
