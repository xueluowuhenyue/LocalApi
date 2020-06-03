package com.course.MyHttpMethod;
import com.course.model.LoginInfo;
import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.logging.Logger;


@Log4j2
@RestController
@Api("/")
public class MyPostMethod {

    //首先获取一个执行sql语句的对象
    @Autowired
    private SqlSessionTemplate template;

    private static Cookie cookie;

    @ApiOperation(value = "用户登录，返回cookie", httpMethod = "POST")
    @RequestMapping(value = "/getCookie", method = RequestMethod.POST)
    public String login(HttpServletResponse response,
                        @RequestParam String userName,
                        @RequestParam String password) {

        //读取所有数据
        /*List<LoginInfo> info = template.selectList("loginCase");
         Logger.getLogger(info.toString());
         System.out.println(info.toString());*/
        //按条件读取数据
        try {
            LoginInfo info = template.selectOne("loginCase", userName);
            if (info.getUserName().equals(userName) && info.getPassword().equals(password)) {
                cookie = new Cookie("login", "success");
                response.addCookie(cookie);
                return "{\"code\":200,\"status\":\"success\",\"msg\":\"恭喜你登录成功\"}";
            }
            return "{\"code\":201,\"status\":\"fail\",\"msg\":\"请输入正确的用户名或密码\"}";
        } catch (NullPointerException e) {
            return "{\"code\":202,\"status\":\"fail\",\"msg\":\"请注册后再登录\"}";
        }
    }

    @ApiOperation(value = "添加用户", httpMethod = "POST")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public String addUser(HttpServletRequest request,
                           @RequestParam String userName,
                           @RequestParam String password,
                           @RequestParam String sex,
                           @RequestParam String age) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") && cookie.getValue().equals("success")) {
                if (userName.length() <= 16 && password.length() <= 16) {
                    return "{\"code\":200,\"status\":\"success\",\"msg\":\"注册成功\"}";
                }
                return "{\"code\":200,\"status\":\"success\",\"msg\":\"数据不符合要求，请重新输入\"}";
            }
            return "{\"code\":202,\"status\":\"fail\",\"msg\":\"cookie错误\"}";
        }
        return "{\"code\":202,\"status\":\"fail\",\"msg\":\"服务器错误\"}";
    }

    @ApiOperation(value = "用户登录", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletResponse response,
                        @RequestBody User user) {

        //读取所有数据
        /*List<LoginInfo> info = template.selectList("loginCase");
         Logger.getLogger(info.toString());
         System.out.println(info.toString());*/
        //按条件读取数据
        try {
            LoginInfo info = template.selectOne("loginCase", user.getUserName());
            if (info.getUserName().equals(user.getUserName()) && info.getPassword().equals(user.getPassword())) {
                cookie = new Cookie("login", "success");
                response.addCookie(cookie);
                return "{\"code\":200,\"status\":\"success\",\"msg\":\"恭喜你登录成功\"}";
            }
            return "{\"code\":201,\"status\":\"fail\",\"msg\":\"请输入正确的用户名或密码\"}";
        } catch (NullPointerException e) {
            return "{\"code\":202,\"status\":\"fail\",\"msg\":\"请注册后再登录\"}";
        }
    }

    @ApiOperation(value = "帐号注册",httpMethod = "POST")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(HttpServletRequest request,
                           @RequestBody User user){

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("login") && cookie.getValue().equals("success")
                    &&user.getUserName().length() <=16 && user.getPassword().length() <= 6){
                System.out.println("cookie正确");

                template.insert("addUser",user);
                System.out.println(user.toString());

                return "{\"code\":200,\"status\":\"success\",\"msg\":\"用户添加成功\"}";
            }
            return "{\"code\":201,\"status\":\"fail\",\"msg\":\"cookie不正确或数据长度不符合要求\"}";
        }
        return "{\"code\":202,\"status\":\"fail\",\"msg\":\"服务器错误\"}";
    }

    //        Boolean res = assertCookie(request);
//        System.out.println(">>>>>>>"+res);
//        Logger.getLogger(res.toString());
//        if(res == true) {
//            template.insert("addUser", user);
//            System.out.println(user.toString());
//        }else {
//            System.out.println("cookie错误");
//        }
//    private Boolean assertCookie(HttpServletRequest request){
//        Cookie[] cookies = request.getCookies();
//        for(Cookie cookie : cookies){
//            if(cookie.getName().equals("login") && cookie.getValue().equals("success")){
//                return true;
//            }
//            return false;
//        }
//        Logger.getLogger("cookie为空");
//        return false;
//    }
}
