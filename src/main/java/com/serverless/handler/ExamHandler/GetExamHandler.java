package com.serverless.handler.ExamHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Exam;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IExamService;
import com.serverless.service.Impl.ExamService;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetExamHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IExamService examService = new ExamService();


  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      Map<String, String> pathParameters = input.getPathParameters();
      String id = pathParameters.get("id");
      Exam exam = examService.findExamById(id);
      if (exam != null) {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setObjectBody(exam)
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setObjectBody(Constant.NO_CONTENT)
            .setObjectBody(Constant.UNDEFINE)
            .build();
      }
    } catch (Exception e) {
      logger.error("Error in get exam by id");
      Response response = new Response("Error in get exam by id", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
