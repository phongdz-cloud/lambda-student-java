package com.serverless.handler.ScoreHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IScoreService;
import com.serverless.service.Impl.ScoreService;
import org.apache.log4j.Logger;

public class ListScoreHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private IScoreService scoreService = new ScoreService();


  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(scoreService.findAll())
          .build();
    } catch (Exception ex) {
      logger.error("Error in list score!");
      Response response = new Response("Error in list Score", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
