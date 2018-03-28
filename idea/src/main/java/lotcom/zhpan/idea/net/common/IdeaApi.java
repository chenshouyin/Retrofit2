package lotcom.zhpan.idea.net.common;


import java.lang.reflect.Proxy;

import lotcom.zhpan.idea.net.token.IGlobalManager;
import lotcom.zhpan.idea.net.token.ProxyHandler;

/**
 * Created by zhpan on 2017/4/1.
 */

public class IdeaApi implements IGlobalManager {
   /* public static <T> T getApiService(Class<T> cls,String baseUrl) {
        return RetrofitService.getRetrofitBuilder(baseUrl)
                .build()
                .create(cls);
    }*/

    @SuppressWarnings("unchecked")
    public <T> T getApiService(Class<T> tClass,String baseUrl) {
        T t = RetrofitService.getRetrofitBuilder(baseUrl)
                .build().create(tClass);
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[] { tClass }, new ProxyHandler(t, this));
    }

    @Override
    public void exitLogin() {

    }
}
