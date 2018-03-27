package lotcom.zhpan.idea.net.common;


/**
 * Created by zhpan on 2017/4/1.
 */

public class IdeaApi {
    public static <T> T getApiService(Class<T> cls,String baseUrl) {
        return RetrofitService.getRetrofitBuilder(baseUrl)
                .build()
                .create(cls);
    }
}
