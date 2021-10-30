package com.serverless.dao.Impl;

import com.serverless.dao.IRoleDAO;
import com.serverless.model.Role;
import java.util.List;
import org.apache.log4j.Logger;

public class RoleDAO extends AbstractDAO<Role> implements IRoleDAO {

  private Logger logger = Logger.getLogger(this.getClass());
  private static RoleDAO roleDAO = null;

  public static RoleDAO getInstance() {
    if (roleDAO == null) {
      roleDAO = new RoleDAO();
      roleDAO.setType(Role.class);
    }
    return roleDAO;
  }

  @Override
  public List<Role> findAll() {
    List<Role> results = query();
    for (Role p : results) {
      logger.info("Role - list() " + p.toString());
    }
    return results;
  }

  @Override
  public Role findRoleByName(String name) {
    List<Role> roles = query();
    if (roles.size() > 0) {
      for (Role role : roles) {
        if (role.getName().equals(name)) {
          return role;
        }
      }
    }
    return null;
  }

  @Override
  public String save(Role role) {
    insert(role);
    logger.info("Role - save(): " + role.toString());
    return role.getId();
  }
}
