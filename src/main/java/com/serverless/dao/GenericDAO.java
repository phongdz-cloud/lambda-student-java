package com.serverless.dao;

import java.util.List;

public interface GenericDAO<T> {

  List<T> query(Object... parameters);

  void insert(T object);

  void update(T object, String id);

  String remove(T object);

}
