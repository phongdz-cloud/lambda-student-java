package com.serverless.service.Impl;

import com.serverless.dao.IStudentDAO;
import com.serverless.dao.Impl.StudentDAO;
import com.serverless.model.Student;
import com.serverless.service.IStudentService;
import java.util.List;
import org.apache.log4j.Logger;

public class StudentService implements IStudentService {

  private Logger logger = Logger.getLogger(this.getClass());

  private IStudentDAO studentDAO = StudentDAO.getInstance();

  @Override
  public List<Student> findAll() {
    return studentDAO.findAll();
  }

  @Override
  public Student findStudentById(String id) {
    return studentDAO.findStudentById(id);
  }

  @Override
  public Student findStudentByToken(String token) {
    return studentDAO.findStudentByToken(token);
  }

  @Override
  public String save(Student student) {
    return studentDAO.save(student);
  }

  @Override
  public String update(String id, Student student) {
    return studentDAO.update(id, student);
  }

  @Override
  public String delete(Student student) {
    logger.debug("Student service Alive!");
    return studentDAO.delete(student);
  }
}
