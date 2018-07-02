package com.cypoem.retrofit.net;


import com.csy.net.net.common.Constants;
import com.csy.net.net.common.RetrofitApi;

public class RetrofitHelper {
    private static IdeaApiService mIdeaApiService;

    public static IdeaApiService getApiService(){
        return mIdeaApiService;
    }
    static {
       mIdeaApiService= RetrofitApi.getApiService(IdeaApiService.class, Constants.API_SERVER_URL);
    }
}
