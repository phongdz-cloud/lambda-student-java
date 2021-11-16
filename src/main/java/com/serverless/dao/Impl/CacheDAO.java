package com.serverless.dao.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.dao.ICacheDAO;
import com.serverless.util.JedisConnection;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import redis.clients.jedis.Jedis;

public class CacheDAO<T> implements ICacheDAO<T> {

  private static Jedis jedis = JedisConnection.getConnection();

  private final ObjectMapper mapper = new ObjectMapper();


  @Override
  public void setObject(String key, Object object) {
    if (jedis != null) {
      Object obj = object;

      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      ObjectOutput out = null;

      try {
        out = new ObjectOutputStream(bos);

        out.writeObject(obj);

        out.flush();

        byte[] objectValue = bos.toByteArray();

        jedis.set(key.getBytes(), objectValue);

      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        jedis.close();
      }
    }
  }

  @Override
  public void setObject(String key, Object object, int ttl) {
    if (jedis != null) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      ObjectOutput out = null;

      try {
        out = new ObjectOutputStream(bos);

        out.writeObject(object);

        out.flush();

        byte[] objectValue = bos.toByteArray();

        jedis.set(key.getBytes(), objectValue);

        jedis.expire(key.getBytes(), ttl);
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        jedis.close();
      }
    }
  }

  @Override
  public Object getObject(String key) {
    if (jedis != null) {
      Object obj = null;

      byte[] redisObject = null;

      redisObject = jedis.get(key.getBytes());

      if (redisObject != null) {
        try {
          ByteArrayInputStream in = new ByteArrayInputStream(redisObject);

          ObjectInputStream is = new ObjectInputStream(in);

          obj = is.readObject();

        } catch (IOException | ClassNotFoundException e) {
          e.printStackTrace();
        } finally {
          jedis.close();
        }
      }
      return obj;
    }
    return null;
  }

  @Override
  public void setMap(String key, Map<String, String> map) {
    if (jedis != null) {
      try {
        jedis.hmset(key, map);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        jedis.close();
      }
    }
  }

  @Override
  public void setMap(String key, Map<String, String> map, int ttl) {
    if (jedis != null) {
      try {
        jedis.hmset(key, map);
        jedis.expire(key, ttl);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        jedis.close();
      }
    }
  }

  @Override
  public boolean deleteCache(String key) {
    if (jedis != null) {
      try {
        if (jedis.del(key) != null) {
          return true;
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        jedis.close();
      }
    }
    return false;
  }

  @Override
  public void updateFindALL(String key, Collection<T> value) {
    try {
      jedis.del(key);
      setObject(key, mapper.writeValueAsString(value));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateFindBy(String key, T obj) {
    try {
      jedis.del(key);
      setObject(key, obj);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @Override
  public Map<String, String> getMap(String key) {
    if (jedis != null) {
      Map<String, String> map = new HashMap<String, String>();
      try {
        map = jedis.hgetAll(key);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        jedis.close();
      }
      return map;
    }
    return null;
  }

  public Jedis getJedis() {
    if (jedis == null) {
      jedis = JedisConnection.getConnection();
    }
    return jedis;
  }

}

