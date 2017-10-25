package com.cypoem.retrofit.net;


import com.cypoem.retrofit.activity.BaseActivity;
import com.cypoem.retrofit.module.BasicResponse;
import com.cypoem.retrofit.module.request.RefreshTokenRequest;
import com.cypoem.retrofit.module.response.RefreshTokenResponseBean;
import com.cypoem.retrofit.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/10/12.
 * Description:
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

    public void signIn() {


        IdeaApi.getApiService()
                .sign()
                .subscribeOn(Schedulers.io())
                .compose(activity.<BasicResponse>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(activity) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        int code = response.getCode();
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
                            signIn();
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
