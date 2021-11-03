package com.serverless.service;

import com.serverless.model.Student;
import java.util.List;

public interface IStudentService {

  List<Student> findAll();

  Student findStudentById(String id);

  Student findStudentByToken(String token);

  String save(Student student);

  String update(String id, Student student);

  String delete(Student student);
}
