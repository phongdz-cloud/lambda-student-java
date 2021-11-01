package com.serverless.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import org.apache.log4j.Logger;

public class HttpUtil {

  private Logger logger = Logger.getLogger(this.getClass());

  public String value;

  public HttpUtil(String value) {
    this.value = value;
  }

  public <T> T toModel(Class<T> tClass) {
    try {
      return new ObjectMapper().readValue(value, tClass);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public static HttpUtil of(BufferedReader reader) throws IOException {
    StringBuilder sb = new StringBuilder();
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return new HttpUtil(sb.toString());
  }
}
