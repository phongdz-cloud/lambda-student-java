package com.serverless.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.dao.IStudentDAO;
import com.serverless.model.Student;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class StudentDAO extends AbstractDAO<Student> implements IStudentDAO {

  private Logger logger = Logger.getLogger(this.getClass());

  public static StudentDAO getInstance() {
    if (studentDAO == null) {
      studentDAO = new StudentDAO();
      studentDAO.setType(Student.class);
    }
    return studentDAO;
  }

  @Override
  public List<Student> findAll() {
    List<Student> students = query();
    for (Student student : students) {
      logger.info("Student - list()" + student.toString());
    }
    return students;
  }

  @Override
  public Student findStudentById(String id) {
    Student student = null;
    Map<String, AttributeValue> av = new HashMap<String, AttributeValue>();
    av.put(":v1", new AttributeValue().withS(id));
    DynamoDBQueryExpression<Student> query = new DynamoDBQueryExpression<Student>()
        .withKeyConditionExpression("id = :v1")
        .withExpressionAttributeValues(av);
    PaginatedQueryList<Student> results = this.getMapper().query(Student.class, query);
    if (results.size() > 0) {
      student = results.get(0);
      logger.debug("Student - get(): Student - + " + student.toString());
    } else {
      logger.debug("Student - get(): Student - Not Found!");
    }
    return student;
  }

  private static StudentDAO studentDAO = null;


  @Override
  public String save(Student student) {
    try {
      insert(student);
      return student.getId();
    } catch (Exception ex) {
      return null;
    }
  }

  @Override
  public String update(String id, Student student) {
    try {
      update(student, id);
      logger.debug("Student - get(): Student - Update success!");
      return id;
    } catch (Exception ex) {
      logger.debug("Student - get(): Student - Update failed!");
      return null;
    }
  }

  @Override
  public String delete(String id) {
    try {
      delete(id);
      logger.debug("Student - get(): Student - Delete failed!");
      return id;
    } catch (Exception ex) {
      logger.debug("Student - get(): Student - Delete failed!");
      return null;
    }
  }
}