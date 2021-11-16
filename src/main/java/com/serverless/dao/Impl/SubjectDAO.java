package com.serverless.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.dao.ISubjectDAO;
import com.serverless.model.Subject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class SubjectDAO extends AbstractDAO<Subject> implements ISubjectDAO {

  private Logger logger = Logger.getLogger(this.getClass());
  private static SubjectDAO subjectDAO = null;

  public static SubjectDAO getInstance() {
    if (subjectDAO == null) {
      subjectDAO = new SubjectDAO();
      subjectDAO.setType(Subject.class);
    }
    return subjectDAO;
  }

  @Override
  public List<Subject> findAll() {
    try {
      List<Subject> subjects = query();
      return subjects;
    } catch (Exception ex) {
      logger.error("Subject getList error! DAO");
      ex.printStackTrace();
      return null;
    }
  }

  @Override
  public Subject findSubjectById(String id) {
    Subject subject = null;
    Map<String, AttributeValue> av = new HashMap<String, AttributeValue>();
    av.put(":v1", new AttributeValue().withS(id));
    DynamoDBQueryExpression<Subject> query = new DynamoDBQueryExpression<Subject>()
        .withKeyConditionExpression("id = :v1")
        .withExpressionAttributeValues(av);
    PaginatedQueryList<Subject> results = this.getMapper().query(Subject.class, query);
    if (results.size() > 0) {
      subject = results.get(0);
      logger.debug("Subject - get(): Subject - " + subject);
    } else {
      logger.debug("Subject - get(): Subject - Not Found!");
    }
    return subject;
  }

  @Override
  public String save(Subject subject) {
    try {
      insert(subject);
      return subject.getId();
    } catch (Exception e) {
      logger.error("Subject save error");
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String update(String id, Subject subject) {
    try {
      update(subject, id);
      logger.debug("Subject update success");
      return id;
    } catch (Exception e) {
      logger.error("Subject update error");
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String delete(Subject subject) {
    try {
      if (remove(subject) != null) {
        logger.info("Subject delete success");
        return subject.getId();
      } else {
        logger.info("Delete failed!");
      }
    } catch (Exception e) {
      logger.info("Delete failed!");
    }
    return null;
  }
}
