package com.bzu.auth.model;

public class AuthInfo {

    String appName;
    String Code;
    String secretKey;

    int number;

    public AuthInfo(String appName, String code, String secretKey) {
        this.appName = appName;
        this.Code = code;
        this.secretKey = secretKey;
        number = 1;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
