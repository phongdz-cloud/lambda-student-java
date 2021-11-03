package com.serverless.service.Impl;

import com.serverless.dao.ITeacherDAO;
import com.serverless.dao.Impl.TeacherDAO;
import com.serverless.model.Teacher;
import com.serverless.service.ITeacherService;
import java.util.List;

public class TeacherService implements ITeacherService {

  private ITeacherDAO teacherDAO = TeacherDAO.getInstance();

  @Override
  public List<Teacher> findAll() {
    return teacherDAO.findAll();
  }

  @Override
  public Teacher findTeacherById(String id) {
    return teacherDAO.findTeacherById(id);
  }

  @Override
  public String save(Teacher teacher) {
    return teacherDAO.save(teacher);
  }

  @Override
  public String update(String id, Teacher teacher) {
    return teacherDAO.update(id, teacher);
  }

  @Override
  public String delete(Teacher teacher) {
    return teacherDAO.delete(teacher);
  }
}
