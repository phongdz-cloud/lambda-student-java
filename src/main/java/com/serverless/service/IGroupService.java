package com.serverless.service;

import com.serverless.model.Group;
import com.serverless.model.Student;
import com.serverless.model.Subject;
import com.serverless.model.Teacher;
import java.util.List;
import java.util.Map;

public interface IGroupService {

  List<Group> findAll();

  Map<Teacher, List<Subject>> findAllSubjectByTeacher();

  Map<Student, List<Subject>> findAllSubjectByStudent();

  Map<Teacher, List<Student>> findTeacherAndStudentsBySubjectId(String subjectId);

  List<Group> findAllGroupByStudentOrTeacherById(String id);

  String save(Group group);

  Group findGroupById(String id);

}
