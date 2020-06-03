package com.course.cases;

import com.course.config.testConfig;
import com.course.model.User;
import com.course.model.loginCase;
import com.course.utils.databaseUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;


public class mysqlSelete {

    @Test
    //@Insert({"insert into user(userName,password,sex,age) VALUES('haha',12345,'男',20);"})
    public void loginSuccess() throws IOException {
        SqlSession session = databaseUtil.getSqlSession();
//        loginCase LoginCase = session.selectOne("loginCase", 1);
        User user = new User();
        session.insert("addUser",user);
        System.out.println(user.toString());
        //session.close();
    }

    public void insert(){

    }
}