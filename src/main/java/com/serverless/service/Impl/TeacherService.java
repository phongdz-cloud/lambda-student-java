package com.serverless.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.dao.ICacheDAO;
import com.serverless.dao.ITeacherDAO;
import com.serverless.dao.Impl.CacheDAO;
import com.serverless.dao.Impl.TeacherDAO;
import com.serverless.model.Teacher;
import com.serverless.service.ITeacherService;
import java.util.List;

public class TeacherService implements ITeacherService {

  private ITeacherDAO teacherDAO = TeacherDAO.getInstance();

  private ICacheDAO cache = new CacheDAO();

  private ObjectMapper mapper = new ObjectMapper();


  @Override
  public List<Teacher> findAll() {
    List<Teacher> teachers = null;
    try {
      String jsonString = (String) cache.getObject(Constant.FINDALLTEACHER);
      if (jsonString == null) {
        System.out.println("Chua chạm cache server Teacher");
        teachers = teacherDAO.findAll();
        cache.setObject(Constant.FINDALLTEACHER, mapper.writeValueAsString(teachers));
      } else {
        System.out.println("chạm cache server Teacher");
        teachers = mapper.readValue(jsonString, new TypeReference<List<Teacher>>() {
        });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return teachers;
  }

  @Override
  public Teacher findTeacherById(String id) {
    Teacher teacher = (Teacher) cache.getObject(id);
    if (teacher == null) {
      System.out.println("Chua chạm cache server Teacher");
      teacher = teacherDAO.findTeacherById(id);
      cache.updateFindBy(id, teacher);
    }
    return teacher;
  }

  @Override
  public Teacher findTeacherByToken(String token) {
    Teacher teacher = (Teacher) cache.getObject(token);
    if (teacher == null) {
      teacher = teacherDAO.findTeacherByToken(token);
      cache.updateFindBy(token, teacher);
    }
    return teacher;
  }

  @Override
  public String save(Teacher teacher) {
    if (teacherDAO.save(teacher) != null) {
      cache.updateFindALL(Constant.FINDALLTEACHER, teacherDAO.findAll());
    }
    return "create success!";
  }

  @Override
  public String update(String id, Teacher teacher) {
    if (teacherDAO.update(id, teacher) != null) {
      cache.updateFindALL(Constant.FINDALLTEACHER, teacherDAO.findAll());
      cache.updateFindBy(id,teacher);
    }
    return "update success";
  }

  @Override
  public String delete(Teacher teacher) {
    if (teacherDAO.delete(teacher) != null) {
      cache.updateFindALL(Constant.FINDALLTEACHER, teacherDAO.findAll());
      cache.deleteCache(teacher.getId());
    }
    return "delete success";
  }
}
