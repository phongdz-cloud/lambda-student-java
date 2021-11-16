package com.serverless.dao;

import java.util.Collection;
import java.util.Map;

public interface ICacheDAO<T> {

  void setObject(String key, Object object);

  void setObject(String key, Object object, int ttl);

  Object getObject(String key);

  void setMap(String key, Map<String, String> map);

  void setMap(String key, Map<String, String> map, int ttl);

  boolean deleteCache(String key);

  void updateFindALL(String key, Collection<T> value);

  void updateFindBy(String key, T obj);

  Map<String, String> getMap(String key);
}
