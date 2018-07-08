package asryab.com.mvvmproject.api.utils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetrofitInterceptor implements Interceptor {
    private static final String LOG_TAG = "Retrofit";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int code = response.code();
        if (code==400){
//            return response.newBuilder()
//                .body(ResponseBody.create(response.body().contentType(), bodyString))
//                .build();
        }
        response.body().source().readString(Charset.forName("UTF-8"));


        return response;
    }
}
