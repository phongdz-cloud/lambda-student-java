package com.serverless.handler.SubjectHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Subject;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.ISubjectService;
import com.serverless.service.Impl.SubjectService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ListSubjectHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final ISubjectService subjectService = new SubjectService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      List<Subject> subjects = subjectService.findAll();
      logger.info("List subject: " + subjects.size());
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.OK)
          .setObjectBody(subjects)
          .build();
    } catch (Exception e) {
      logger.error("Error in list Subject! ");
      Response response = new Response("Error in list Subject: ", input);
      e.printStackTrace();
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.NO_CONTENT)
          .setObjectBody(response)
          .build();
    }
  }
}
