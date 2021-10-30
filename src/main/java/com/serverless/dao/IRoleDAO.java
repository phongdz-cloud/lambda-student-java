package com.serverless.dao;

import com.serverless.model.Role;
import java.util.List;

public interface IRoleDAO extends GenericDAO<Role> {

  List<Role> findAll();

  Role findRoleByName(String name);

  String save(Role role);
}
