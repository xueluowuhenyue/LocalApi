package com.course.cases;


import com.course.config.testConfig;
import com.course.model.InterFaceName;
import com.course.model.addUserCase;
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

@Log4j2
public class addUserTest {

    @BeforeTest
    public void beforeTest(){
        testConfig.addUserUrl= GetUrl.addressConfig(InterFaceName.ADDUSER);
        System.out.println(testConfig.addUserUrl);
        testConfig.client = new DefaultHttpClient();
    }

    @Test
    public void addUserSuccess() throws IOException {
        SqlSession session = databaseUtil.getSqlSession();
        addUserCase addusercase = session.selectOne("selectUser",1);
//        System.out.println(addusercase.toString());
//        System.out.println(addusercase.getExpected());

        String res = getResult(addusercase);
        //断言
        Assert.assertEquals(res,addusercase.getExpected());
    }

    @Test
    public void addUserError() throws IOException {
        SqlSession session = databaseUtil.getSqlSession();
        addUserCase addusercase = session.selectOne("selectUser",2);

        String res = getResult(addusercase);
        //断言
        Assert.assertEquals(res,addusercase.getExpected());
    }

    private String getResult(addUserCase addusercase) throws IOException {
        HttpPost post = new HttpPost(testConfig.addUserUrl);

        // 添加参数
        JSONObject param = new JSONObject();
        param.put("userName", addusercase.getUserName());
        param.put("password", addusercase.getPassword());
        param.put("age", addusercase.getAge());
        param.put("sex", addusercase.getSex());
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        // 设置请求头
        post.setHeader("content-type", "application/json");

        //添加cookie
        testConfig.client.setCookieStore(testConfig.cookie);

        //发送请求
        HttpResponse response = testConfig.client.execute(post);
        //获取返回数据
        String resBody = EntityUtils.toString(response.getEntity());
        JSONObject responseBody = new JSONObject(resBody);
//        String result = EntityUtils.toString(response.getEntity());
//        System.out.println(result);

        return responseBody.getString("msg");
    }
}
