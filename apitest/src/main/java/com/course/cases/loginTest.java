package com.course.cases;

import com.course.config.testConfig;
import com.course.model.InterFaceName;
import com.course.model.loginCase;
import com.course.utils.GetUrl;
import com.course.utils.databaseUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.CookieStore;

@Log4j2
public class loginTest {

    @BeforeTest
    public void beforeTest(){
        //获取登录接口的地址
        testConfig.loginUrl = GetUrl.addressConfig(InterFaceName.LOGIN);
        // 初始化一个客户端
        testConfig.client = new DefaultHttpClient();
    }

    @Test
    public void loginSuccess() throws IOException {
        SqlSession session = databaseUtil.getSqlSession();
        loginCase LoginCase = session.selectOne("loginCase",1);
        System.out.println(LoginCase.toString());
        System.out.println(testConfig.loginUrl);

        //结果断言
        String res = getResult(LoginCase);
        Assert.assertEquals(LoginCase.getExpected(),res);
    }

    @Test
    public void loginError() throws IOException {
        SqlSession session = databaseUtil.getSqlSession();
        loginCase LoginCase = session.selectOne("loginCase",2);
        System.out.println(LoginCase.toString());
        System.out.println(testConfig.loginUrl);

        //结果断言
        String res = getResult(LoginCase);
        Assert.assertEquals(LoginCase.getExpected(),res);
    }

    private String getResult(loginCase LoginCase) throws IOException {
        HttpPost post = new HttpPost(testConfig.loginUrl);
        JSONObject param = new JSONObject();
        param.put("userName",LoginCase.getUserName());
        param.put("password",LoginCase.getPassword());
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //执行post方法
        HttpResponse response = testConfig.client.execute(post);
        //获取响应结果
        String result = EntityUtils.toString(response.getEntity());

        JSONObject res = new JSONObject(result);
        System.out.println(res.getString("msg"));
        // 获取cookie
        testConfig.cookie = testConfig.client.getCookieStore();
        System.out.println(testConfig.cookie);
        return res.getString("msg");
    }
}
