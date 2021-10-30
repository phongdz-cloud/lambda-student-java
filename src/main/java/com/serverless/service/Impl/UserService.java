package com.serverless.service.Impl;

import com.serverless.dao.IUserDAO;
import com.serverless.dao.Impl.UserDAO;
import com.serverless.model.User;
import com.serverless.service.IUserService;
import java.util.List;

public class UserService implements IUserService {

  private IUserDAO userDAO = UserDAO.getInstance();

  @Override
  public List<User> findAll() {
    return userDAO.findAll();
  }

  @Override
  public User findUserById(String id) {
    return userDAO.findUserById(id);
  }

  @Override
  public Boolean existsUserByUsername(String username) {
    if (userDAO.existsUserByUsername(username)) {
      return true;
    }
    return false;
  }

  @Override
  public Boolean existsUserByUsernameAndPassword(User user) {
    return userDAO.existsUserByUsernameAndPassword(user);
  }

  @Override
  public String save(User user) {
    return userDAO.save(user);
  }
}
