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
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class UpdateScoreHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IScoreService scoreService = new ScoreService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      ObjectMapper mapper = new ObjectMapper();
      Score newScore = mapper.readValue(input.getBody(),Score.class);
      if(scoreService.update(newScore.getId(),newScore) != null){
        return ApiGatewayResponse.builder()
            .setHeaders(origin)
            .setStatusCode(Constant.OK)
            .setObjectBody(newScore)
            .build();
      }else{
        return ApiGatewayResponse.builder()
            .setHeaders(origin)
            .setStatusCode(Constant.ERROR)
            .setObjectBody("Update failed!")
            .build();
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Error in update score!");
      Response response = new Response("Error in update Score", input);
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
