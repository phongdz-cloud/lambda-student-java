package com.serverless.service.Impl;

import com.serverless.dao.ICacheDAO;
import com.serverless.dao.Impl.CacheDAO;
import com.serverless.service.ICacheService;
import java.util.Map;

public class CacheService implements ICacheService {

  ICacheDAO cache = new CacheDAO();


  @Override
  public void setObject(String key, Object object) {
    cache.setObject(key, object);
  }

  @Override
  public void setObject(String key, Object object, int ttl) {
    cache.setObject(key, object, ttl);
  }

  @Override
  public Object getObject(String key) {
    return cache.getObject(key);
  }

  @Override
  public void setMap(String key, Map<String, String> map) {
    cache.setMap(key, map);
  }

  @Override
  public void setMap(String key, Map<String, String> map, int ttl) {
    cache.setMap(key, map, ttl);
  }

  @Override
  public Map<String, String> getMap(String key) {
    return cache.getMap(key);
  }
}

