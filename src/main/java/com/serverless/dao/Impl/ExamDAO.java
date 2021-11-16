package com.serverless.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.dao.IExamDAO;
import com.serverless.model.Exam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ExamDAO extends AbstractDAO<Exam> implements IExamDAO {

  private Logger logger = Logger.getLogger(this.getClass());

  private static ExamDAO examDAO = null;

  public static ExamDAO getInstance() {
    if (examDAO == null) {
      examDAO = new ExamDAO();
      examDAO.setType(Exam.class);
    }
    return examDAO;
  }

  @Override
  public List<Exam> findAll() {
    List<Exam> exams = null;
    try {
      exams = query();
      logger.info("Get success list exam!");
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Exam getExam error! DAO");
    }
    return exams;
  }

  @Override
  public Exam findExamById(String id) {
    Exam exam = null;
    Map<String, AttributeValue> av = new HashMap<String, AttributeValue>();
    av.put(":v1", new AttributeValue().withS(id));
    DynamoDBQueryExpression<Exam> query = new DynamoDBQueryExpression<Exam>()
        .withKeyConditionExpression("id = :v1")
        .withExpressionAttributeValues(av);
    PaginatedQueryList<Exam> results = this.getMapper().query(Exam.class, query);
    return results.size() > 0 ? results.get(0) : null;
  }

  @Override
  public String save(Exam exam) {
    try {
      insert(exam);
    } catch (Exception e) {
      return null;
    }
    return exam.getId();
  }

  @Override
  public String update(String id, Exam exam) {
    try {
      update(exam, id);
      logger.info("Exam updated success!");
    } catch (Exception e) {
      logger.error("Update failed exam!");
      return null;
    }
    return id;
  }

  @Override
  public String delete(Exam exam) {
    try {
      if (remove(exam) != null) {
        logger.info("deleted exam success");
        return exam.getId();
      } else {
        logger.info("Deleted failed!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
