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

public class CreateSubjectHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final ISubjectService subjectService = new SubjectService();


  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      Subject subject = objectMapper.readValue(input.getBody(), Subject.class);
      subjectService.save(subject);
      logger.debug("Subject saved success! " + subject);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(subject)
          .build();
    } catch (Exception e) {
      logger.error("Error in subject code!");
      e.printStackTrace();
      Response response = new Response("Error in saving Subject!", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
