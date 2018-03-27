package lotcom.zhpan.idea.net.common;


import java.lang.reflect.Proxy;

import lotcom.zhpan.idea.net.IGlobalManager;
import lotcom.zhpan.idea.net.proxy.ProxyHandler;

/**
 * Created by zhpan on 2017/4/1.
 */

public class IdeaApi implements IGlobalManager {
   /* public static <T> T getApiService(Class<T> cls,String baseUrl) {
        return RetrofitService.getRetrofitBuilder(baseUrl)
                .build()
                .create(cls);
    }*/

    public <T> T getApiService(Class<T> tClass,String baseUrl) {
        T t = RetrofitService.getRetrofitBuilder(baseUrl)
                .build().create(tClass);
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[] { tClass }, new ProxyHandler(t, this));
    }

    @Override
    public void exitLogin() {

    }
}
