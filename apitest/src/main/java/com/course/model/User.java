package com.course.model;

import lombok.Data;


@Data
public class User {
    private String id;
    private String sex;
    private String age;
    private String isDelete;
    private String userName;
    private String password;
    private String permission;

    @Override
    public String toString(){
        return (
                "{id:" + id +","+
                "sex:" + sex + ","+
                "age:" + age + ","+
                "isDelete" + isDelete+","+
                "userName:" + userName+","+
                "password:" + password+","+
                "permission:" + permission + "}"
                );
    }
}
