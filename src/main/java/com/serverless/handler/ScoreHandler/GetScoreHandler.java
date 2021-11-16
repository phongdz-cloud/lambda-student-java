package com.serverless.handler.ScoreHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Score;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IScoreService;
import com.serverless.service.Impl.ScoreService;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetScoreHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private IScoreService scoreService = new ScoreService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    try {
      Map<String, String> pathParameters = input.getPathParameters();
      String id = pathParameters.get("id");
      Score score = scoreService.findScoreById(id);
      if (score != null) {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.OK)
            .setObjectBody(score)
            .build();
      } else {
        return ApiGatewayResponse.builder()
            .setStatusCode(Constant.NO_CONTENT)
            .setObjectBody(Constant.UNDEFINE)
            .build();
      }
    } catch (Exception e) {
      logger.error("Error in get score by id");
      Response response = new Response("Error in get score by id ", input);
      return ApiGatewayResponse.builder()
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
