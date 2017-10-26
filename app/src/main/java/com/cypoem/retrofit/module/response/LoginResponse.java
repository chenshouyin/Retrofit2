package com.cypoem.retrofit.module.response;


/**
 * Created by zhpan on 2017/10/25.
 * Description:
 */

public class LoginResponse {
    private String token;
    private String refresh_token;
    private String expired;
    private String refresh_secret;

    public String getRefresh_secret() {
        return refresh_secret;
    }

    public void setRefresh_secret(String refresh_secret) {
        this.refresh_secret = refresh_secret;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
