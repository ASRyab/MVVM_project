package asryab.com.mvvmproject.api.utils;
import android.util.Log;

import java.io.IOException;

import asryab.com.mvvmproject.api.ApiConst;
import asryab.com.mvvmproject.data.DataStorage;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse   = chain.proceed(chain.request());
        HttpUrl baseUrl             = HttpUrl.parse(Config.getBaseUrl());
        HttpUrl url                 = chain.request().url();

        if (baseUrl.host().equals(url.host()) && !originalResponse.headers(ApiConst.KEY_SET_COOKIE).isEmpty()) {

            for (String header : originalResponse.headers(ApiConst.KEY_SET_COOKIE))
                if (header.startsWith(ApiConst.KEY_SID_COOKIE)) {
                    final String cookie = header.split("; ")[0];
                    DataStorage.updateCookie(cookie);

                    if (!Config.isProduction) {
                        Log.v("HEADERS", "------------------------------------------------------------------------------------------------------>>>");
                        Log.e("HEADERS", "Request: " + chain.request().url().toString());
                        Log.e("HEADERS", "Response " + ApiConst.KEY_COOKIE + ": " + cookie);
                        Log.v("HEADERS", "<<<------------------------------------------------------------------------------------------------------");
                    }
                }
        }

        return originalResponse;
    }

}
