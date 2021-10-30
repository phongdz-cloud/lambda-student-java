package com.serverless.dao;

import com.serverless.model.User;
import java.util.List;

public interface IUserDAO extends GenericDAO<User> {

  List<User> findAll();


  User findUserById(String id);

  Boolean existsUserByUsername(String username);

  Boolean existsUserByUsernameAndPassword(User user);

  String save(User user);
}
