package com.serverless.service;

import com.serverless.model.Role;
import java.util.List;

public interface IRoleService {

  List<Role> findAll();

  Role findRoleByName(String name);

  String save(Role role);

}
