package com.cypoem.retrofit.net;

import com.cypoem.retrofit.module.BasicResponse;
import com.cypoem.retrofit.module.request.LoginRequest;
import com.cypoem.retrofit.module.request.RefreshTokenRequest;
import com.cypoem.retrofit.module.response.LoginResponse;
import com.cypoem.retrofit.module.response.MeiZi;
import com.cypoem.retrofit.module.response.RefreshTokenResponseBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by dell on 2017/4/1.
 */

public interface IdeaApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 20000;

    String HOST = "http://gank.io/";
    String API_SERVER_URL = HOST + "api/data/";

    @Headers("Cache-Control: public, max-age=100")//设置缓存 缓存时间为100s
    @GET("福利/10/1")
    Observable<BasicResponse<List<MeiZi>>> getMezi();

    /**登录
     * @return
     */
    @POST("everySay/selectAll.do")
    Observable<LoginResponse> login(@Body LoginRequest request);

    /**登录
     * @return
     */
    @POST("everySay/selectAll.do")
    Observable<BasicResponse<RefreshTokenResponseBean>> refreshToken(@Body RefreshTokenRequest request);
}
