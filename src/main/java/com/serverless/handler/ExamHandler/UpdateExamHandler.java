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

public class UpdateExamHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IExamService examService = new ExamService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Exam newExam = mapper.readValue(input.getBody(), Exam.class);
      if (examService.update(newExam.getId(), newExam) != null) {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setObjectBody(newExam)
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.ERROR)
            .setObjectBody("Update failed!")
            .build();
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Error in update exam!");
      Response response = new Response("Error in update Exam", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
