package com.course.utils;

import com.course.model.InterFaceName;

import java.util.ResourceBundle;

public class GetUrl {
    private static ResourceBundle bundle=ResourceBundle.getBundle("application");

    public static String addressConfig(InterFaceName name) {
        String ip = bundle.getString("test.uri");
        String uri = "";
        String testUrl;
        if(name == InterFaceName.LOGIN){
            uri = bundle.getString("login.url");
        }
        if (name == InterFaceName.ADDUSER){
            uri = bundle.getString("add.user.url");
        }

        if(name == InterFaceName.UPDEDATAUSER){
            uri = bundle.getString("/updateUser");
        }

        if(name == InterFaceName.GETUSERLIST){
            uri = bundle.getString("/get.user.list.url");
        }

        testUrl = ip+uri;
        return testUrl;
    }
}