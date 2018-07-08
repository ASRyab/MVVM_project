package asryab.com.mvvmproject.api.utils;

import okhttp3.OkHttpClient;

public final class Config {

    public static final boolean isProduction            = false;

    private static final String SANDBOX_URL             = "http://";
    private static final String PRODUCTION_URL          = "http://1111111";

    public static String getBaseUrl(){
        return isProduction ? PRODUCTION_URL : SANDBOX_URL;
    }

    public static OkHttpClient.Builder getClientBuilderWithCookieAction(){
        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addNetworkInterceptor(new AddCookiesInterceptor());
        clientBuilder.addNetworkInterceptor(new ReceivedCookiesInterceptor());

//        if (!Config.isProduction)
//            clientBuilder.addNetworkInterceptor(new StethoInterceptor());

        return clientBuilder;
    }
}