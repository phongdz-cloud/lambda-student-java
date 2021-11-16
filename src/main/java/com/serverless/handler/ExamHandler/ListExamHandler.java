package com.serverless.handler.ExamHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IExamService;
import com.serverless.service.Impl.ExamService;
import org.apache.log4j.Logger;

public class ListExamHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IExamService examService = new ExamService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(examService.findAll())
          .build();
    } catch (Exception e) {
      logger.error("Error in list exam!");
      Response response = new Response("Error in list Exam", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
