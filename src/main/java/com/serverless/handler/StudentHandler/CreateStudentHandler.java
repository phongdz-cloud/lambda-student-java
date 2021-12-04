package com.serverless.handler.StudentHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Role;
import com.serverless.model.Student;
import com.serverless.model.User;
import com.serverless.pojo.UserOfStudentPojo;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IRoleService;
import com.serverless.service.IStudentService;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.RoleService;
import com.serverless.service.Impl.StudentService;
import com.serverless.service.Impl.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.log4j.Logger;

public class CreateStudentHandler implements
    RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IRoleService roleService = new RoleService();

  private final IUserService userService = new UserService();

  private final IStudentService studentService = new StudentService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    String mssv = "19110" + getRandomNumberUsingInts();
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      Student student = objectMapper.readValue(input.getBody(), Student.class);
      Role role = roleService.findRoleByName("student");
      User user = new User();
      user.setUsername(mssv);
      user.setPassword("123");
      user.setRole(role);
      if (userService.save(user) != null) {
        User userOfStudent = userService.findUserByUsername(user.getUsername());
        if (userOfStudent != null) {
          student.setUserId(userOfStudent.getId());
          studentService.save(student);
          UserOfStudentPojo userOfStudentPojo = new UserOfStudentPojo();
          userOfStudentPojo.setUsername(user.getUsername());
          userOfStudentPojo.setStudent(student);
          return ApiGatewayResponse.builder()
              .setHeaders(origin)
              .setStatusCode(Constant.OK)
              .setObjectBody(userOfStudentPojo)
              .build();
        }
      }
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.NO_CONTENT)
          .setObjectBody("not create student!")
          .build();
    } catch (Exception ex) {
      logger.error("Error in tour code! " + ex.getMessage());
      Response responseBody = new Response("Error in saving Student! " + ex.getMessage(),
          input);
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.ERROR)
          .setObjectBody(responseBody)
          .build();
    }
  }

  public int getRandomNumberUsingInts() {
    Random random = new Random();
    return random.ints(100, 999)
        .findFirst()
        .getAsInt();
  }
}
