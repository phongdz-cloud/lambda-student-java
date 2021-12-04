package com.serverless.handler.GroupHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.constant.Constant;
import com.serverless.model.Group;
import com.serverless.model.Student;
import com.serverless.model.Teacher;
import com.serverless.model.User;
import com.serverless.response.ApiGatewayRequest;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.IGroupService;
import com.serverless.service.IStudentService;
import com.serverless.service.ITeacherService;
import com.serverless.service.IUserService;
import com.serverless.service.Impl.GroupService;
import com.serverless.service.Impl.StudentService;
import com.serverless.service.Impl.TeacherService;
import com.serverless.service.Impl.UserService;
import com.serverless.util.BearerToken;
import com.serverless.util.JWTUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetGroupByTokenHandler implements
    RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

  private final Logger logger = Logger.getLogger(this.getClass());

  private final IUserService userService = new UserService();

  private final IStudentService studentService = new StudentService();

  private final ITeacherService teacherService = new TeacherService();

  private final IGroupService groupService = new GroupService();

  @Override
  public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
    Map<String, String> origin = new HashMap<>();
    origin.put("Access-Control-Allow-Origin", "*");
    try {
      String id = null;
      String idStudentOrTeacher = null;
      Student student = null;
      Teacher teacher = null;
      if (BearerToken.getInstance().preHandle(input)) {
        id = BearerToken.getInstance().getTokenWrapper();
        String idUser = (String) JWTUtil.decodeJWT(id).get("jti");
        User user = userService.findUserById(idUser);
        if (user != null) {
          if (user.getRole().getName().equals("student")) {
            student = studentService.findStudentByToken(id);
            idStudentOrTeacher = student.getId();
          }
          if (user.getRole().getName().equals("teacher")) {
            teacher = teacherService.findTeacherByToken(id);
            idStudentOrTeacher = teacher.getId();
          }

          List<Group> groupList = groupService.findAllGroupByStudentOrTeacherById(
              idStudentOrTeacher);
          return ApiGatewayResponse.builder()
              .setHeaders(origin)
              .setStatusCode(Constant.OK)
              .setObjectBody(groupList)
              .build();
        }
      } else {
        return ApiGatewayResponse.builder()
            .setHeaders(origin)
            .setStatusCode(Constant.NO_CONTENT)
            .setObjectBody("Not authorization!")
            .build();
      }
    } catch (Exception ex) {
      logger.error("Error in Get all group by token: " + ex.getMessage());
      Response response = new Response("Error in get all group by token: ", input);
      ex.printStackTrace();
      return ApiGatewayResponse.builder()
          .setHeaders(origin)
          .setStatusCode(Constant.ERROR)
          .setObjectBody(response)
          .build();
    }
    return null;
  }
}
