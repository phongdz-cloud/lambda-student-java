package com.serverless.service.Impl;

import com.serverless.dao.IRoleDAO;
import com.serverless.dao.Impl.RoleDAO;
import com.serverless.model.Role;
import com.serverless.service.IRoleService;
import java.util.List;

public class RoleService implements IRoleService {

  private IRoleDAO roleDAO = RoleDAO.getInstance();

  @Override
  public List<Role> findAll() {
    List<Role> results = roleDAO.findAll();
    return results;
  }

  @Override
  public Role findRoleByName(String name) {
    return roleDAO.findRoleByName(name);
  }

  @Override
  public String save(Role role) {

    return roleDAO.save(role);
  }
}
