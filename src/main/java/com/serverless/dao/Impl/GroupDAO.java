package com.serverless.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.dao.IGroupDAO;
import com.serverless.model.Group;
import com.serverless.model.Student;
import com.serverless.model.Subject;
import com.serverless.model.Teacher;
import com.serverless.service.IStudentService;
import com.serverless.service.ISubjectService;
import com.serverless.service.ITeacherService;
import com.serverless.service.Impl.StudentService;
import com.serverless.service.Impl.SubjectService;
import com.serverless.service.Impl.TeacherService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class GroupDAO extends AbstractDAO<Group> implements IGroupDAO {

  private Logger logger = Logger.getLogger(this.getClass());

  private final ISubjectService subjectService = new SubjectService();

  private final IStudentService studentService = new StudentService();

  private final ITeacherService teacherService = new TeacherService();


  private static GroupDAO groupDAO = null;

  public static GroupDAO getInstance() {
    if (groupDAO == null) {
      groupDAO = new GroupDAO();
      groupDAO.setType(Group.class);
    }
    return groupDAO;
  }

  @Override
  public List<Group> findAll() {
    List<Group> groups = null;
    try {
      groups = query();
    } catch (Exception ex) {
      logger.error("Error in list Group!: ");
    }
    return groups;
  }

  @Override
  public Map<Teacher, List<Subject>> findAllSubjectByTeacher() {
    try {
      List<Teacher> teachers = teacherService.findAll();
      List<Group> groups = findAll();
      Map<Teacher, List<Subject>> teacherByList = new HashMap<Teacher, List<Subject>>();
      List<Subject> subjects;
      for (Teacher teacher : teachers) {
        subjects = new ArrayList<>();
        for (Group group : groups) {
          if (teacher.getId().equals(group.getTeacherId())) {
            subjects.add(subjectService.findSubjectById(group.getSubjectId()));
          }
        }
        teacherByList.put(teacher, subjects);
      }
      return teacherByList.size() > 0 ? teacherByList : null;
    } catch (Exception ex) {
      logger.error("Error in Find All Teacher By list");
      return null;
    }
  }

  @Override
  public Map<Student, List<Subject>> findAllSubjectByStudent() {
    try {
      List<Student> students = studentService.findAll();
      List<Group> groups = findAll();
      Map<Student, List<Subject>> studentByList = new HashMap<Student, List<Subject>>();
      List<Subject> subjects;
      for (Student student : students) {
        subjects = new ArrayList<>();
        for (Group group : groups) {
          if (student.getId().equals(group.getStudentId())) {
            subjects.add(subjectService.findSubjectById(group.getSubjectId()));
          }
        }
        studentByList.put(student, subjects);
      }
      return studentByList.size() > 0 ? studentByList : null;
    } catch (Exception ex) {
      logger.error("Error in Find All Subject By Student");
      return null;
    }
  }

  @Override
  public Map<Teacher, List<Student>> findTeacherAndStudentsBySubjectId(String subjectId) {
    try {
      Map<Teacher, List<Student>> teacherListStudents = new HashMap<Teacher, List<Student>>();
      List<String> keys = new ArrayList<String>();
      List<Student> students;
      List<Group> groups = findAll();
      for (Group group : groups) {
        if (group.getSubjectId().equals(subjectId)) {
          if (existsTeacherByTeacherId(keys, group.getTeacherId())) {
            keys.add(group.getTeacherId());
          }
        }
      }
      for (String key : keys) {
        students = new ArrayList<>();
        for (Group group : groups) {
          if (key.equals(group.getTeacherId())) {
            students.add(studentService.findStudentById(group.getStudentId()));
          }
        }
        teacherListStudents.put(teacherService.findTeacherById(key), students);
      }
      return teacherListStudents.size() > 0 ? teacherListStudents : null;
    } catch (Exception e) {
      logger.error("Error in Find Teacher And Students By SubjectId");
      return null;
    }
  }

  @Override
  public List<Group> findAllGroupByStudentOrTeacherById(String id) {
    List<Group> groupList = findAll();
    List<Group> groupListOfStudentOrTeacher = new ArrayList<>();
    for (Group g : groupList) {
      logger.info("id: " + id);
      if (g.getStudentId().equals(id) || g.getTeacherId().equals(id)) {
        logger.info("id: " + id);
        groupListOfStudentOrTeacher.add(g);
      }
    }
    return groupListOfStudentOrTeacher;
  }

  @Override
  public Group findGroupById(String id) {
    Group group = null;
    Map<String, AttributeValue> av = new HashMap<>();
    av.put(":v1", new AttributeValue().withS(id));
    DynamoDBQueryExpression<Group> query = new DynamoDBQueryExpression<Group>()
        .withKeyConditionExpression("id = :v1")
        .withExpressionAttributeValues(av);
    PaginatedQueryList<Group> results = this.getMapper().query(Group.class, query);
    if (results.size() > 0) {
      group = results.get(0);
      logger.debug("Student - get(): Student - + " + group.toString());
    } else {
      logger.debug("Student - get(): Student - Not Found!");
    }
    return group;
  }

  public boolean existsTeacherByTeacherId(List<String> keys, String techerId) {
    for (String key : keys) {
      if (key.equals(techerId)) {
        return false;
      }
    }
    return true;
  }


  @Override
  public String save(Group group) {
    try {
      insert(group);
      return group.getId();
    } catch (Exception e) {
      logger.error("Error in saving Group!");
      return null;
    }
  }
}
