package com.serverless.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.dao.ITeacherDAO;
import com.serverless.model.Teacher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class TeacherDAO extends AbstractDAO<Teacher> implements ITeacherDAO {

  private Logger logger = Logger.getLogger(this.getClass());

  private static TeacherDAO teacherDAO = null;

  public static TeacherDAO getInstance() {
    if (teacherDAO == null) {
      teacherDAO = new TeacherDAO();
      teacherDAO.setType(Teacher.class);
    }
    return teacherDAO;
  }

  @Override
  public List<Teacher> findAll() {
    try {
      List<Teacher> teachers = query();
      for (Teacher teacher : teachers) {
        logger.info("Teacher - lost() " + teacher);
      }
      return teachers;
    } catch (Exception e) {
      logger.error("Teacher - getlist Error! DAO: " + e.getMessage());
      return null;
    }
  }

  @Override
  public Teacher findTeacherById(String id) {
    Teacher teacher = null;
    Map<String, AttributeValue> av = new HashMap<String, AttributeValue>();
    av.put(":v1", new AttributeValue().withS(id));
    DynamoDBQueryExpression<Teacher> query = new DynamoDBQueryExpression<Teacher>()
        .withKeyConditionExpression("id = :v1")
        .withExpressionAttributeValues(av);
    PaginatedQueryList<Teacher> results = this.getMapper().query(Teacher.class, query);
    if (results.size() > 0) {
      teacher = results.get(0);
      logger.debug("Teacher - get(): Teacher - " + teacher);
    } else {
      logger.debug("Teacher - get(): Teacher - Not found ");
    }
    return teacher;
  }

  @Override
  public String save(Teacher teacher) {
    try {
      insert(teacher);
      return teacher.getId();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public String update(String id, Teacher teacher) {
    try {
      update(teacher, id);
      logger.debug("Teacher - get(): Teacher - Update success");
      return id;
    } catch (Exception e) {
      logger.debug("Teacher - get(): Teacher - Update failed!");
      return null;
    }
  }

  @Override
  public String delete(Teacher teacher) {
    try {
      if (remove(teacher) != null) {
        logger.debug("Teacher - get(): Teacher - Delete success");
        return teacher.getId();
      } else {
        logger.info("Teacher - failed!");
      }
    } catch (Exception ex) {
      logger.error("Teacher - delete(): Delete Error exception!");
    }
    return null;
  }
}
