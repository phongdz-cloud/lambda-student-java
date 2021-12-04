package com.serverless.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.dao.IUserDAO;
import com.serverless.model.User;
import com.serverless.util.HashingUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class UserDAO extends AbstractDAO<User> implements IUserDAO {

  private Logger logger = Logger.getLogger(this.getClass());

  private static UserDAO userDAO = null;

  public static UserDAO getInstance() {
    if (userDAO == null) {
      userDAO = new UserDAO();
      userDAO.setType(User.class);
    }
    return userDAO;
  }

  @Override
  public List<User> findAll() {
    List<User> users = query();
    for (User p : users) {
      logger.info("User - list() " + p.toString());
    }
    return users;
  }

  @Override
  public User findUserById(String id) {
    User user = null;
    Map<String, AttributeValue> av = new HashMap<>();
    av.put(":v1", new AttributeValue().withS(id));
    DynamoDBQueryExpression<User> query = new DynamoDBQueryExpression<User>()
        .withKeyConditionExpression("id = :v1")
        .withExpressionAttributeValues(av);
    PaginatedQueryList<User> results = this.getMapper().query(User.class, query);
    if (results.size() > 0) {
      user = results.get(0);
      logger.info("User - get(): User - " + user.toString());
    } else {
      logger.info("User - get(): User - Not Found!.");

    }
    return user;
  }


  @Override
  public Boolean existsUserByUsername(String username) {
    List<User> users = query();
    if (users.size() > 0) {
      for (User user : users) {
        if (user.getUsername().equals(username)) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public Boolean existsUserByUsernameAndPassword(User user) {
    try {
      List<User> users = query();
      if (users.size() > 0) {
        for (User e : users) {
          if (e.getUsername().equals(user.getUsername()) && HashingUtil.verifyAndUpdateHash(
              user.getPassword(), e.getPassword(), e)) {
            logger.info("password: "+ user.getPassword());
            user.setId(e.getId());
            user.setPassword(e.getPassword());
            return true;
          }
        }
      }
    } catch (Exception ex) {
      logger.error("not query get all Users");
    }
    return false;
  }


  @Override
  public String save(User user) {
    if(user.getId() == null){
      user.setPassword(HashingUtil.hash(user.getPassword()));
    }
    logger.info(user.getPassword());
    insert(user);
    return user.getId();
  }

  @Override
  public String delete(User user) {
    try {
      if (remove(user) != null) {
        logger.debug("User - get(): User - Delete scucess");
        return user.getId();
      } else {
        logger.info("User - delete failed!");
      }
    } catch (Exception ex) {
      logger.error("User - delete(): Delete error exception!");
    }
    return null;
  }

  @Override
  public User findUserByUsername(String username) {
    List<User> users = query();
    if (users.size() > 0) {
      for (User user : users) {
        if (user.getUsername().equals(username)) {
          return user;
        }
      }
    }
    return null;
  }
}
