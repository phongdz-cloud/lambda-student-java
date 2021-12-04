package com.serverless.pojo;

import com.serverless.model.Teacher;

public class UserOfTeacherPojo {
  private String username;

  private Teacher teacher;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }
}
