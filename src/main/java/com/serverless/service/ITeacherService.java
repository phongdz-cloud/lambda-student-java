package com.serverless.service;

import com.serverless.model.Teacher;
import java.util.List;

public interface ITeacherService {

  List<Teacher> findAll();

  Teacher findTeacherById(String id);

  String save(Teacher teacher);

  String update(String id, Teacher teacher);

  String delete(Teacher teacher);
}
