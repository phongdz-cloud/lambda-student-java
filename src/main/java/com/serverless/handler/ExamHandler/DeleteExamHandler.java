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
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class DeleteExamHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IExamService examService = new ExamService();


  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      Map<String, String> pathParameters = input.getPathParameters();
      String id = pathParameters.get("id");
      Exam exam = examService.findExamById(id);
      if (examService.delete(exam) != null) {
        logger.debug("Delete exam by id: " + exam.getId());
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setHeaders(origin)
            .setObjectBody("Delete successfully!")
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.ERROR)
            .setHeaders(origin)
            .setObjectBody("Delete failed!")
            .build();
      }
    } catch (Exception e) {
      logger.error("Error in delted exam");
      e.printStackTrace();
      Response response = new Response("Error in delete Exam", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setHeaders(origin)
          .setObjectBody(response)
          .build();
    }
  }
}
