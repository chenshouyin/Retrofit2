package com.cypoem.retrofit.net;


import com.cypoem.retrofit.activity.BaseActivity;
import com.cypoem.retrofit.module.BasicResponse;
import com.cypoem.retrofit.module.request.LoginRequest;
import com.cypoem.retrofit.module.request.RefreshTokenRequest;
import com.cypoem.retrofit.module.response.LoginResponse;
import com.cypoem.retrofit.module.response.RefreshTokenResponseBean;
import com.cypoem.retrofit.utils.SharedPreferencesHelper;
import com.cypoem.retrofit.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/10/12.
 * Description:登录和刷新token的请求
 */

public class RequestHelper {
    private BaseActivity activity;
    private int times;
    private RequestCallback callback;

    public RequestHelper(BaseActivity activity) {
        this.activity = activity;
    }

    public RequestHelper(BaseActivity activity, RequestCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void login() {
        LoginRequest loginRequest = new LoginRequest(activity);
        loginRequest.setUserId("123456");
        loginRequest.setPassword("123123");
        IdeaApi.getApiService()
                .login(loginRequest)
                .subscribeOn(Schedulers.io())
                .compose(activity.<LoginResponse>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<LoginResponse>(activity) {
                    @Override
                    public void onSuccess(LoginResponse response) {
                        ToastUtils.show("登录成功！获取到token"+response.getToken()+",可以存储到本地了");
                        /**
                         * 可以将这些数据存储到User中，User存储到本地数据库
                         */
                        SharedPreferencesHelper.put(activity,"token",response.getToken());
                        SharedPreferencesHelper.put(activity,"refresh_token",response.getRefresh_token());
                        SharedPreferencesHelper.put(activity,"refresh_secret",response.getRefresh_secret());
                    }
                });
    }

    //  刷新token
    public void refreshToken() {
        IdeaApi.getApiService()
                .refreshToken(new RefreshTokenRequest())
                .subscribeOn(Schedulers.io())
                .compose(activity.<BasicResponse<RefreshTokenResponseBean>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<RefreshTokenResponseBean>>(activity) {
                    @Override
                    public void onSuccess(BasicResponse<RefreshTokenResponseBean> response) {
                        if (null != callback) {
                            callback.onTokenUpdateSucceed();
                        }
                    }

                    @Override
                    public void onException(ExceptionReason reason) {
                        super.onException(reason);
                        if (times < 3) {
                            login();
                            ToastUtils.show("出错了..." + times);
                        }
                        times++;
                    }
                });
    }

    public interface RequestCallback {
        /**
         * token验证完成，API访问已经ready
         */
        void onTokenUpdateSucceed();
    }
}
