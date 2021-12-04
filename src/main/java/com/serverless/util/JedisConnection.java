package com.serverless.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import redis.clients.jedis.Jedis;

public class JedisConnection {

  public static Jedis getConnection() {

    Jedis jedis = new Jedis("http://ec2-54-227-174-216.compute-1.amazonaws.com", 6379);
//    jedis.auth("Ye7o0Kzl2rKd3AB2EJHmbh8W5iwNSs7r");
//    Jedis jedis = new Jedis("localhost", 6379);
    try {
      System.out.println("Hello Redis: " + jedis.ping());
//      jedis.flushAll();
      return jedis;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void main(String[] args) throws JsonProcessingException {
    Jedis jedis = getConnection();
  }
}
