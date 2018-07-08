package asryab.com.mvvmproject.api.utils;
import android.util.Log;

import java.io.IOException;

import asryab.com.mvvmproject.api.ApiConst;
import asryab.com.mvvmproject.data.DataStorage;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HttpUrl baseUrl         = HttpUrl.parse(Config.getBaseUrl());
        HttpUrl url             = chain.request().url();

        String cookie = DataStorage.getCookies();

        if (baseUrl.host().equals(url.host()) && cookie != null) {
            builder.addHeader(ApiConst.KEY_COOKIE, cookie);

            if (!Config.isProduction) {
                Log.v("HEADERS", "------------------------------------------------------------------------------------------------------>>>");
                Log.e("HEADERS", "Request: " + chain.request().url().toString());
                Log.e("HEADERS", ApiConst.KEY_COOKIE + ": " + cookie);
                Log.v("HEADERS", "<<<------------------------------------------------------------------------------------------------------");
            }
        }

        return chain.proceed(builder.build());
    }
}