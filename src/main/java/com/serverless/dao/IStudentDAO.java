package com.serverless.dao;

import com.serverless.model.Student;
import java.util.List;

public interface IStudentDAO extends GenericDAO<Student> {

  List<Student> findAll();

  Student findStudentByToken(String token);

  Student findStudentById(String id);

  String save(Student student);

  String update(String id, Student student);

  String delete(Student student);
}
