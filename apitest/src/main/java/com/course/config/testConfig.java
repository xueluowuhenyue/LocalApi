package com.course.config;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.CookieStore;

public class testConfig {
    //登录接口
    public static String loginUrl;
    public static String addUserUrl;

    //用例存储cookie的变量
    public static CookieStore cookie;
    //httpclient客户端声明
    public static DefaultHttpClient client;
}
