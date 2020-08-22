package com.mirapharmacy.hifivy;

public class LoginResponse {

    private boolean status;
    private String msg;
    private String token;

    public LoginResponse(boolean status, String msg, String token) {
        this.status = status;
        this.msg = msg;
        this.token = token;
    }

    public boolean isStatus() {
        return status;
    }


    public String getMsg() {
        return msg;
    }

    public String getToken() {
        return token;
    }

}
