package com.serverless.service;

import java.sql.ResultSet;
import java.util.Map;

public interface ICacheService {


  void setObject(String key, Object object);

  void setObject(String key, Object object, int ttl);

  Object getObject(String key);

  void setMap(String key, Map<String, String> map);

  void setMap(String key, Map<String, String> map, int ttl);

  Map<String, String> getMap(String key);
}
