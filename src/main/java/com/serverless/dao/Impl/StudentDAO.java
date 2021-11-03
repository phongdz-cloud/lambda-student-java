package com.serverless.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.dao.IStudentDAO;
import com.serverless.model.Student;
import com.serverless.util.JWTUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class StudentDAO extends AbstractDAO<Student> implements IStudentDAO {

  private Logger logger = Logger.getLogger(this.getClass());

  private static StudentDAO studentDAO = null;


  public static StudentDAO getInstance() {
    if (studentDAO == null) {
      studentDAO = new StudentDAO();
      studentDAO.setType(Student.class);
    }
    return studentDAO;
  }


  @Override
  public List<Student> findAll() {
    try {
      List<Student> students = query();
      for (Student student : students) {
        logger.info("Student - list()" + student.toString());
      }
      return students;
    } catch (Exception ex) {
      logger.debug("Student getList error! DAO: " + ex.getMessage());
      return null;
    }
  }

  @Override
  public Student findStudentByToken(String token) {
    Student student = null;
    try {
      String idUser = (String) JWTUtil.decodeJWT(token).get("jti");
      logger.info("User Id: " + idUser);
      List<Student> students = findAll();
      for (Student s : students) {
        if (s.getUserId().equals(idUser)) {
          student = findStudentById(s.getId());
          break;
        }
      }
    } catch (Exception ex) {
      logger.error("Student error token: " + token);
    }
    return student;
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
  public String delete(Student student) {
    try {
      logger.debug("Student Dao Alive Code!");
      if (remove(student) != null) {
        logger.debug("Student - get(): Student - Delete failed!");
        return student.getId();
      } else {
        logger.info("Delete failed!");
        return null;
      }
    } catch (Exception ex) {
      logger.debug("Student - get(): Student - Delete failed!");
      return null;
    }
  }
}
