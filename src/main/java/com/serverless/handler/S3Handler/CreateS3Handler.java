package com.serverless.handler.S3Handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.pojo.Base64ImagePojo;
import com.serverless.pojo.UploadS3Pojo;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IS3Service;
import com.serverless.service.Impl.S3Service;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class CreateS3Handler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IS3Service s3Service = new S3Service();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    logger.info("Hello S3!!!!");
    logger.info(s3Service.getClass());
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      ObjectMapper mapper = new ObjectMapper();
      logger.info(input.getBody());
      UploadS3Pojo uploadS3Pojo = mapper.readValue(String.valueOf(input.getBody()), UploadS3Pojo.class);
      logger.info(uploadS3Pojo.getId());
      Base64ImagePojo base64ImagePojo = new Base64ImagePojo(uploadS3Pojo.getBase64Image(),
          uploadS3Pojo.getId());
      uploadS3Pojo.setUrlS3(s3Service.uploadS3Object(base64ImagePojo));
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.OK)
          .setObjectBody(uploadS3Pojo)
          .build();
    } catch (Exception ex) {
      logger.error("Error in upload S3: " + ex.getMessage());
      Response response = new Response("Error in upload S3! ", input);
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
