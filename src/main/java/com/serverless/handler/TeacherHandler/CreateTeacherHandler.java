package com.serverless.handler.TeacherHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.model.Role;
import com.serverless.model.Teacher;
import com.serverless.model.User;
import com.serverless.pojo.UserOfTeacherPojo;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IRoleService;
import com.serverless.service.ITeacherService;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.RoleService;
import com.serverless.service.Impl.TeacherService;
import com.serverless.service.Impl.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.log4j.Logger;

public class CreateTeacherHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final ITeacherService teacherService = new TeacherService();

  private final IUserService userService = new UserService();

  private final IRoleService roleService = new RoleService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    String mstc = "01191" + getRandomNumberUsingInts();
    try {
      Teacher teacher = objectMapper.readValue(input.getBody(), Teacher.class);
      Role role = roleService.findRoleByName("teacher");
      User user = new User();
      user.setUsername(mstc);
      user.setPassword("123");
      user.setRole(role);
      if (userService.save(user) != null) {
        User userOfTeacher = userService.findUserByUsername(user.getUsername());
        if (userOfTeacher != null) {
          teacher.setUserId(userOfTeacher.getId());
          teacherService.save(teacher);
          UserOfTeacherPojo userOfTeacherPojo = new UserOfTeacherPojo();
          userOfTeacherPojo.setUsername(userOfTeacher.getUsername());
          userOfTeacherPojo.setTeacher(teacher);
          return ApiGatewayResponse.builder()
              .setHeaders(origin)
              .setStatusCode(Constant.OK)
              .setObjectBody(userOfTeacherPojo)
              .build();
        }
      }
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.NO_CONTENT)
          .setObjectBody("Not create teacher!")
          .build();
    } catch (JsonProcessingException e) {
      logger.error("Error in create techer: " + e.getMessage());
      e.printStackTrace();
      Response response = new Response("Error in save teacher", input);
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
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
