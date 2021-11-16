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
import java.util.Map;
import org.apache.log4j.Logger;

public class DeleteSubjectHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final ISubjectService subjectService = new SubjectService();


  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      int statusCode;
      String msg;
      Map<String, String> pathParameters = input.getPathParameters();
      String id = pathParameters.get("id");
      Subject subject = subjectService.findSubjectById(id);
      logger.info("Subject find By Id: " + id);
      if (subject != null) {
        String deleteId = subjectService.delete(subject);
        logger.info("Delete subject by id: " + deleteId);
        statusCode = Constant.OK;
        msg = "Delete successfully!";
      } else {
        statusCode = Constant.ERROR;
        msg = "Delete faild!";
      }
      return ApiGatewayResponse.builder()
          .setStatusCode(statusCode)
          .setObjectBody(msg)
          .build();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Error in delete subject!");
      Response response = new Response("Error deleting Subject", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
