package com.serverless.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.dao.ICacheDAO;
import com.serverless.dao.IRoleDAO;
import com.serverless.dao.Impl.CacheDAO;
import com.serverless.dao.Impl.RoleDAO;
import com.serverless.model.Role;
import com.serverless.service.IRoleService;
import java.util.List;

public class RoleService implements IRoleService {

  private IRoleDAO roleDAO = RoleDAO.getInstance();

  private ICacheDAO cache = new CacheDAO();

  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public List<Role> findAll() {
    List<Role> results = null;
    try {
      String jsonString = (String) cache.getObject(Constant.FINDALLROLE);
      if (jsonString == null) {
        results = roleDAO.findAll();
        cache.setObject(Constant.FINDALLROLE, mapper.writeValueAsString(results));
      } else {
        results = mapper.readValue(jsonString, new TypeReference<List<Role>>() {
        });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public Role findRoleByName(String name) {
    Role role = (Role) cache.getObject(Constant.FINDROLENAME + name);
    if (role == null) {
      role = roleDAO.findRoleByName(name);
      cache.setObject(Constant.FINDROLENAME + name, role);
    }
    return role;
  }

  @Override
  public String save(Role role) {
    if (roleDAO.save(role) != null) {
      List<Role> results = roleDAO.findAll();
      cache.deleteCache(Constant.FINDALLROLE);
      try {
        cache.setObject(Constant.FINDALLROLE, mapper.writeValueAsString(results));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    return "success";
  }
}
