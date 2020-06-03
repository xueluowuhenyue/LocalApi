package com.course.model;

import lombok.Data;

@Data
public class LoginInfo {
    private String id;
    private String userName;
    private String password;
    private String expected;

    public String getExpected() {
        return expected;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }
}
