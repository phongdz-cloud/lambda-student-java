package com.serverless.service;

import com.serverless.model.User;
import java.util.List;

public interface IUserService {

  List<User> findAll();

  User findUserById(String id);

  Boolean existsUserByUsername(String username);

  Boolean existsUserByUsernameAndPassword(User user);

  String save(User user);

  String delete(User user);

  User findUserByUsername(String username);
}
