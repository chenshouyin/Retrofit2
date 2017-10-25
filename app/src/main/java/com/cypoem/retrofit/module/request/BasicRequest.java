package com.cypoem.retrofit.module.request;

import com.cypoem.retrofit.utils.SharedPreferencesHelper;
import com.cypoem.retrofit.utils.Utils;

/**
 * Created by zhpan on 2017/10/25.
 * Description:
 */

public class BasicRequest {
    public String token = (String) SharedPreferencesHelper.get(Utils.getContext(), "token", "");

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
