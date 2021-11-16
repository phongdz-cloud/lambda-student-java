package com.serverless.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.dao.ICacheDAO;
import com.serverless.dao.ISubjectDAO;
import com.serverless.dao.Impl.CacheDAO;
import com.serverless.dao.Impl.SubjectDAO;
import com.serverless.model.Subject;
import com.serverless.service.ISubjectService;
import java.util.List;

public class SubjectService implements ISubjectService {

  private final ISubjectDAO subjectDAO = SubjectDAO.getInstance();

  private ICacheDAO cache = new CacheDAO();

  private ObjectMapper mapper = new ObjectMapper();


  @Override
  public List<Subject> findAll() {
    List<Subject> results = null;
    try {
      String jsonString = (String) cache.getObject(Constant.FINDALLSUBJECT);
      if (jsonString == null) {
        results = subjectDAO.findAll();
        cache.setObject(Constant.FINDALLSUBJECT, mapper.writeValueAsString(results));
      } else {
        results = mapper.readValue(jsonString, new TypeReference<List<Subject>>() {
        });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public Subject findSubjectById(String id) {
    Subject subject = (Subject) cache.getObject(id);
    if (subject == null) {
      subject = subjectDAO.findSubjectById(id);
      cache.setObject(id, subject);
    }
    return subject;
  }

  @Override
  public String save(Subject subject) {
    if (subjectDAO.save(subject) != null) {
      cache.updateFindALL(Constant.FINDALLSUBJECT, subjectDAO.findAll());
    }
    return "created success!";
  }

  @Override
  public String update(String id, Subject subject) {
    subjectDAO.update(subject, id);
    cache.updateFindALL(Constant.FINDALLSUBJECT, subjectDAO.findAll());
    cache.updateFindBy(id,subject);
    return subjectDAO.update(id, subject);
  }

  @Override
  public String delete(Subject subject) {
    if (subjectDAO.delete(subject) != null) {
      cache.updateFindALL(Constant.FINDALLUSER, subjectDAO.findAll());
      cache.deleteCache(subject.getId());
    }
    return "deleted success!";
  }
}
