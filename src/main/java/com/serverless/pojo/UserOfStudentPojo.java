package com.serverless.pojo;

import com.serverless.model.Student;

public class UserOfStudentPojo {

  private String username;

  private Student student;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }
}
