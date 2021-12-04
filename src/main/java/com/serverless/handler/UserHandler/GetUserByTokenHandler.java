package com.serverless.handler.UserHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Student;
import com.serverless.model.Teacher;
import com.serverless.model.User;
import com.serverless.pojo.StudentPojo;
import com.serverless.pojo.TeacherPojo;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IStudentService;
import com.serverless.service.ITeacherService;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.StudentService;
import com.serverless.service.Impl.TeacherService;
import com.serverless.service.Impl.UserService;
import com.serverless.util.BearerToken;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetUserByTokenHandler implements
    RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());
  private final IUserService userService = new UserService();
  private final IStudentService studentService = new StudentService();
  private final ITeacherService teacherService = new TeacherService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      String id = null;
      Student student = null;
      Teacher teacher = null;
      int statusCode = Constant.NO_CONTENT;
      if (BearerToken.getInstance().preHandle(input)) {
        id = BearerToken.getInstance().getTokenWrapper();
        student = studentService.findStudentByToken(id);
        if (student != null) {
          User user = userService.findUserById(student.getUserId());
          if (user != null) {
            StudentPojo studentPojo = new StudentPojo();
            studentPojo.setFirstName(student.getFirstName());
            studentPojo.setLastName(student.getLastName());
            studentPojo.setMiddleName(student.getMiddleName());
            studentPojo.setRole(user.getRole());
            studentPojo.setAvatar(user.getAvatar());
            return ApiGatewayResponse.builder()
                .setHeaders(origin)
                .setStatusCode(Constant.OK)
                .setObjectBody(studentPojo)
                .build();
          }
        } else {
          teacher = teacherService.findTeacherByToken(id);
          logger.info("Teacher:" + teacher);
          User user = userService.findUserById(teacher.getUserId());
          logger.info("Teacher: " + user);
          if (teacher != null) {
            TeacherPojo teacherPojo = new TeacherPojo();
            teacherPojo.setFirstName(teacher.getFirstName());
            teacherPojo.setLastName(teacher.getLastName());
            teacherPojo.setMiddleName(teacher.getMiddleName());
            teacherPojo.setRole(user.getRole());
            return ApiGatewayResponse.builder()
                .setHeaders(origin)
                .setStatusCode(Constant.OK)
                .setObjectBody(teacherPojo)
                .build();
          }
        }
      }
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.NO_CONTENT)
          .setObjectBody("Not authorization")
          .build();
    } catch (Exception ex) {
      logger.error("Error in Get user by token: " + ex.getMessage());
      Response response = new Response("Error in get User by token: ", input);
      ex.printStackTrace();
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
  }
}
