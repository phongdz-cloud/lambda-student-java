package com.serverless.dao;

import com.serverless.model.Subject;
import java.util.List;

public interface ISubjectDAO extends GenericDAO<Subject> {

  List<Subject> findAll();

  Subject findSubjectById(String id);

  String save(Subject subject);

  String update(String id, Subject subject);

  String delete(Subject subject);
}
