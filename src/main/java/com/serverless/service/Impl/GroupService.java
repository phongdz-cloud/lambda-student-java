package com.serverless.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.dao.ICacheDAO;
import com.serverless.dao.IGroupDAO;
import com.serverless.dao.Impl.CacheDAO;
import com.serverless.dao.Impl.GroupDAO;
import com.serverless.model.Group;
import com.serverless.model.Student;
import com.serverless.model.Subject;
import com.serverless.model.Teacher;
import com.serverless.service.IGroupService;
import java.util.List;
import java.util.Map;

public class GroupService implements IGroupService {

  private IGroupDAO groupDAO = GroupDAO.getInstance();

  private ICacheDAO cache = new CacheDAO();

  private ObjectMapper mapper = new ObjectMapper();


  @Override
  public List<Group> findAll() {
    List<Group> results = null;
    try {
      String jsonString = (String) cache.getObject(Constant.FINDALLGROUP);
      if (jsonString == null) {
        results = groupDAO.findAll();
        cache.setObject(Constant.FINDALLGROUP, mapper.writeValueAsString(results));
      } else {
        results = mapper.readValue(jsonString, new TypeReference<List<Group>>() {
        });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public Map<Teacher, List<Subject>> findAllSubjectByTeacher() {
    return groupDAO.findAllSubjectByTeacher();
  }

  @Override
  public Map<Student, List<Subject>> findAllSubjectByStudent() {
    return groupDAO.findAllSubjectByStudent();
  }

  @Override
  public Map<Teacher, List<Student>> findTeacherAndStudentsBySubjectId(String subjectId) {
    return groupDAO.findTeacherAndStudentsBySubjectId(subjectId);
  }

  @Override
  public String save(Group group) {
    return groupDAO.save(group);
  }
}




