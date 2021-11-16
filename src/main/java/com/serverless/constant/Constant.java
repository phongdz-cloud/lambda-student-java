package com.serverless.constant;

public class Constant {

  public static final String USER = "USER";
  public static final String ADMIN = "ADMIN";
  public static final int NO_CONTENT = 404;
  public static final int OK = 200;
  public static final int ERROR = 500;
  public static final String UNDEFINE = "undefined";
  public static final String Authorization = "Bearer";
  public static final String FINDALLROLE = "select * role";
  public static final String FINDROLENAME = " select * role where name: ";
  public static final String FINDALLSTUDENT = "select * student";
  public static final String FINDALLUSER = "select * user";
  public static final String FINDALLTEACHER = "select * teacher";
  public static final String FINDALLSUBJECT = "select * subject";
  public static final String FINDALLGROUP = "select * group";
  public static final String FINDALLSUBJECTBYTEACHER = "select * group by teacher";
  public static final String FINDALLSUBJECTBYSTUDENT = "select * group by student";
  public static final String FINDTEACHERANDSTUDENTSBYSUBJECTID = "select * teacher and student subject by id: ";
  public static final String FINDALLSCORE = "select * score";
  public static final String FINDALLEXAM = "select * exam";
  public static final String FindUserByUsernameAndPassword = "select user form user where ";
}
