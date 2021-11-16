package com.serverless.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.dao.ICacheDAO;
import com.serverless.dao.IUserDAO;
import com.serverless.dao.Impl.CacheDAO;
import com.serverless.dao.Impl.UserDAO;
import com.serverless.model.User;
import com.serverless.service.IUserService;
import com.serverless.util.HashingUtil;
import java.util.List;

public class UserService implements IUserService {

  private final IUserDAO userDAO = UserDAO.getInstance();

  private final ICacheDAO cache = new CacheDAO();

  private final ObjectMapper mapper = new ObjectMapper();


  @Override
  public List<User> findAll() {
    List<User> results = null;
    try {
      String jsonString = (String) cache.getObject(Constant.FINDALLUSER);
      if (jsonString == null) {
        System.out.println("Không chạm cache server!");
        results = userDAO.findAll();
        cache.setObject(Constant.FINDALLUSER, mapper.writeValueAsString(results));
      } else {
        System.out.println(" chạm cache server!");
        results = mapper.readValue(jsonString, new TypeReference<List<User>>() {
        });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public User findUserById(String id) {

    User user = (User) cache.getObject(id);
    if (user == null) {
      System.out.println("Không chạm cache server!");
      user = userDAO.findUserById(id);
      cache.setObject(id, user);
    } else {
      System.out.println("Chạm Cache server!");
    }
    return user;
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
    boolean check = false;
    if (cache.getObject(Constant.FindUserByUsernameAndPassword + user.getUsername()) == null) {
      if (userDAO.existsUserByUsernameAndPassword(user)) {
        cache.updateFindBy(Constant.FindUserByUsernameAndPassword + user.getUsername(), user);
        check = true;
      }
    } else {
      User oddUser = (User) cache.getObject(
          Constant.FindUserByUsernameAndPassword + user.getUsername());
      if (HashingUtil.verifyAndUpdateHash(user.getPassword(), oddUser.getPassword(), oddUser)) {
        check = true;
      } else {
        if (userDAO.existsUserByUsernameAndPassword(user)) {
          cache.updateFindBy(Constant.FindUserByUsernameAndPassword + user.getUsername(), user);
          check = true;
        }
      }
    }
    return check;
  }

  @Override
  public String save(User user) {
    if (userDAO.save(user) != null) {
      cache.updateFindALL(Constant.FINDALLUSER, userDAO.findAll());
      cache.updateFindBy(Constant.FindUserByUsernameAndPassword + user.getUsername(), user);
    }
    return "success";
  }
}
