package com.serverless.handler.ExamHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Exam;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IExamService;
import com.serverless.service.Impl.ExamService;
import org.apache.log4j.Logger;

public class CreateExamHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IExamService examService = new ExamService();


  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      Exam exam = objectMapper.readValue(input.getBody(), Exam.class);
      examService.save(exam);
      logger.info("Exam saved success");
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(exam)
          .build();
    } catch (Exception e) {
      logger.error("Error in create exam");
      e.printStackTrace();
      Response response = new Response("Error in save exam", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
