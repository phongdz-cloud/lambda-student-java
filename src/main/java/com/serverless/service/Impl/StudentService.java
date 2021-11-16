package com.serverless.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.dao.ICacheDAO;
import com.serverless.dao.IStudentDAO;
import com.serverless.dao.Impl.CacheDAO;
import com.serverless.dao.Impl.StudentDAO;
import com.serverless.model.Student;
import com.serverless.service.IStudentService;
import java.util.List;
import org.apache.log4j.Logger;

public class StudentService implements IStudentService {

  private Logger logger = Logger.getLogger(this.getClass());

  private IStudentDAO studentDAO = StudentDAO.getInstance();

  private ICacheDAO cache = new CacheDAO();

  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public List<Student> findAll() {
    List<Student> results = null;
    try {
      String jsonString = (String) cache.getObject(Constant.FINDALLSTUDENT);
      if (jsonString == null) {
        System.out.println("Chưa chạm cache server");
        results = studentDAO.findAll();
        cache.setObject(Constant.FINDALLSTUDENT, mapper.writeValueAsString(results));
      } else {
        System.out.println("chạm cache server");
        results = mapper.readValue(jsonString, new TypeReference<List<Student>>() {
        });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public Student findStudentById(String id) {
    Student student = (Student) cache.getObject(id);
    if (student == null) {
      student = studentDAO.findStudentById(id);
      cache.setObject(id, student);
    }
    return student;
  }

  @Override
  public Student findStudentByToken(String token) {
    Student student = (Student) cache.getObject(token);
    if (student == null) {
      student = studentDAO.findStudentByToken(token);
      cache.setObject(token, student);
    }
    return student;
  }

  @Override
  public String save(Student student) {
    if (studentDAO.save(student) != null) {
      cache.updateFindALL(Constant.FINDALLSTUDENT, studentDAO.findAll());
    }
    return "create student success!";
  }

  @Override
  public String update(String id, Student student) {
    if (studentDAO.update(id, student) != null) {
      cache.updateFindALL(Constant.FINDALLSTUDENT, studentDAO.findAll());
      cache.updateFindBy(id, student);
    }
    return "update student success!";
  }

  @Override
  public String delete(Student student) {
    if (studentDAO.delete(student) != null) {
      cache.updateFindALL(Constant.FINDALLSTUDENT, studentDAO.findAll());
      cache.deleteCache(student.getId());
    }
    return "delete student success!";
  }
}
