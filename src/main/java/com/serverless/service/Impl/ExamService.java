package com.serverless.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.dao.ICacheDAO;
import com.serverless.dao.IExamDAO;
import com.serverless.dao.Impl.CacheDAO;
import com.serverless.dao.Impl.ExamDAO;
import com.serverless.model.Exam;
import com.serverless.service.IExamService;
import java.util.List;

public class ExamService implements IExamService {

  private final IExamDAO examDAO = ExamDAO.getInstance();

  private ICacheDAO cache = new CacheDAO();

  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public List<Exam> findAll() {
    List<Exam> results = null;
    try {
      String jsonString = (String) cache.getObject(Constant.FINDALLEXAM);
      if (jsonString == null) {
        System.out.println("Chưa chạm cache Exam");
        results = examDAO.findAll();
        cache.setObject(Constant.FINDALLEXAM, mapper.writeValueAsString(results));
      } else {
        System.out.println("chạm cache Exam");
        results = mapper.readValue(jsonString, new TypeReference<List<Exam>>() {
        });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public Exam findExamById(String id) {
    Exam exam = (Exam) cache.getObject(id);
    if (exam == null) {
      exam = examDAO.findExamById(id);
      cache.setObject(id, exam);
    }
    return exam;
  }

  @Override
  public String save(Exam exam) {
    if (examDAO.save(exam) != null) {
      cache.updateFindALL(Constant.FINDALLEXAM, examDAO.findAll());
    }
    return "create exam success!";
  }

  @Override
  public String update(String id, Exam exam) {
    if (examDAO.update(id, exam) != null) {
      cache.updateFindALL(Constant.FINDALLEXAM, examDAO.findAll());
      cache.updateFindBy(id, exam);
    }
    return "update exam success!";
  }

  @Override
  public String delete(Exam exam) {
    if(examDAO.delete(exam) != null){
      cache.updateFindALL(Constant.FINDALLEXAM, examDAO.findAll());
      cache.deleteCache(exam.getId());
    }
    return examDAO.delete(exam);
  }
}
