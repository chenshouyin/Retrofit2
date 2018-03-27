package com.cypoem.retrofit.net;


import lotcom.zhpan.idea.net.common.Constants;
import lotcom.zhpan.idea.net.common.IdeaApi;

/**
 * Created by zhpan on 2018/3/22.
 */

public class RetrofitHelper {
    private static IdeaApiService mIdeaApiService;

    public static IdeaApiService getApiService(){
        return mIdeaApiService;
    }
    static {
        mIdeaApiService = IdeaApi.getApiService(IdeaApiService.class, Constants.API_SERVER_URL);
    }
}
