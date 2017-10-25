package com.cypoem.retrofit.module.response;

import com.cypoem.retrofit.module.BasicResponse;

/**
 * Created by zhpan on 2017/10/25.
 * Description:
 */

public class LoginResponse extends BasicResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
