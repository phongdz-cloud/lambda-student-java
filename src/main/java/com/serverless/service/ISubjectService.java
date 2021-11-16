package com.serverless.service;

import com.serverless.model.Subject;
import java.util.List;

public interface ISubjectService {

  List<Subject> findAll();

  Subject findSubjectById(String id);

  String save(Subject subject);

  String update(String id, Subject subject);

  String delete(Subject subject);
}
