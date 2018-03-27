package com.cypoem.retrofit.net;


import com.cypoem.retrofit.module.request.LoginRequest;
import com.cypoem.retrofit.module.request.RefreshTokenRequest;
import com.cypoem.retrofit.module.response.LoginResponse;
import com.cypoem.retrofit.module.response.RefreshTokenResponseBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lotcom.zhpan.idea.BaseActivity;
import lotcom.zhpan.idea.net.BasicResponse;
import lotcom.zhpan.idea.net.common.DefaultObserver;
import lotcom.zhpan.idea.net.common.IdeaApi;
import lotcom.zhpan.idea.utils.SharedPreferencesHelper;
import lotcom.zhpan.idea.utils.ToastUtils;

/**
 * Created by zhpan on 2017/10/12.
 * Description:登录和刷新token的请求
 */

public class RequestHelper {
    private BaseActivity activity;
    private int times;  //  刷新token重连次数
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
        RetrofitHelper.getApiService()
                .login(loginRequest)
                .subscribeOn(Schedulers.io())
                .compose(activity.<BasicResponse<LoginResponse>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LoginResponse>>() {
                    @Override
                    public void onSuccess(BasicResponse<LoginResponse> response) {
                        LoginResponse results = response.getResults();
                        ToastUtils.show("登录成功！获取到token" + results.getToken() + ",可以存储到本地了");
                        /**
                         * 可以将这些数据存储到User中，User存储到本地数据库
                         */
                        SharedPreferencesHelper.put(activity, "token", results.getToken());
                        SharedPreferencesHelper.put(activity, "refresh_token", results.getRefresh_token());
                        SharedPreferencesHelper.put(activity, "refresh_secret", results.getRefresh_secret());
                    }
                });
    }

    //  刷新token
    public void refreshToken() {
        RetrofitHelper.getApiService()
                .refreshToken(new RefreshTokenRequest())
                .subscribeOn(Schedulers.io())
                .compose(activity.<BasicResponse<RefreshTokenResponseBean>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<RefreshTokenResponseBean>>() {
                    @Override
                    public void onSuccess(BasicResponse<RefreshTokenResponseBean> response) {
                        RefreshTokenResponseBean results = response.getResults();
                        SharedPreferencesHelper.put(activity, "token", results.getToken());
                        SharedPreferencesHelper.put(activity, "refresh_secret", results.getSecret());
                        if (null != callback) {
                            callback.onTokenUpdateSucceed();
                        }
                    }

                   /* 这样写有问题呀...times永远都是0 哈哈..
                   @Override
                    public void onException(ExceptionReason reason) {
                        super.onException(reason);
                        if (times < 3) {
                            ToastUtils.show("刷新token出现异常，正在进行第" + times+1 + "次刷新");
                        } else {
                            ToastUtils.show("刷新token出现出错，正在跳转到登录页面..." + times);
                        }
                        times++;
                    }*/
                });
    }

    public interface RequestCallback {
        /**
         * token验证完成，API访问已经ready
         */
        void onTokenUpdateSucceed();
    }
}
