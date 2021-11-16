package com.serverless.handler.ScoreHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Score;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IScoreService;
import com.serverless.service.Impl.ScoreService;
import org.apache.log4j.Logger;

public class CreateScoreHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IScoreService scoreService = new ScoreService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try{
      ObjectMapper objectMapper = new ObjectMapper();
      Score score = objectMapper.readValue(input.getBody(),Score.class);
      logger.info("Score saved success!");
      scoreService.save(score);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.OK)
          .setObjectBody(score)
          .build();
    }catch (Exception e){
      logger.error("Error in crate score");
      e.printStackTrace();
      Response response = new Response("Error in save score",input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
