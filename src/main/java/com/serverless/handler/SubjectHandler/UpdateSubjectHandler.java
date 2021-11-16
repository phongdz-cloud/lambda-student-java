package com.serverless.handler.SubjectHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Subject;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.ISubjectService;
import com.serverless.service.Impl.SubjectService;
import org.apache.log4j.Logger;

public class UpdateSubjectHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private final ISubjectService subjectService = new SubjectService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      int statusCode;
      ObjectMapper mapper = new ObjectMapper();
      Subject newSubject = mapper.readValue(input.getBody(), Subject.class);
      if (subjectService.update(newSubject.getId(), newSubject) != null) {
        statusCode = Constant.OK;
      } else {
        statusCode = Constant.ERROR;
      }
      return ApiGatewayResponse.builder()
          .setStatusCode(statusCode)
          .setObjectBody(newSubject != null ? newSubject : "Update failed!")
          .build();
    } catch (Exception ex) {
      logger.error("Error in update subject: " + ex.getMessage());
      Response response = new Response("Error in update subject! ", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
