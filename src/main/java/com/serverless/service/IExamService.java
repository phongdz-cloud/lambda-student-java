package com.serverless.service;

import com.serverless.model.Exam;
import java.util.List;

public interface IExamService {

  List<Exam> findAll();

  Exam findExamById(String id);

  String save(Exam exam);

  String update(String id, Exam exam);

  String delete(Exam exam);
}
