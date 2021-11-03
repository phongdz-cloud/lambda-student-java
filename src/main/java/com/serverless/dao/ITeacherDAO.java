package com.serverless.dao;

import com.serverless.model.Teacher;
import java.util.List;

public interface ITeacherDAO extends GenericDAO<Teacher> {

  List<Teacher> findAll();

  Teacher findTeacherById(String id);

  String save(Teacher teacher);

  String update(String id, Teacher teacher);

  String delete(Teacher teacher);


}
