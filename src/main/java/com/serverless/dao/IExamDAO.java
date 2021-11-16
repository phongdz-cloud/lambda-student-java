package com.serverless.dao;

import com.serverless.model.Exam;
import java.util.List;

public interface IExamDAO {

  List<Exam> findAll();

  Exam findExamById(String id);

  String save(Exam exam);

  String update(String id, Exam exam);

  String delete(Exam exam);

}
