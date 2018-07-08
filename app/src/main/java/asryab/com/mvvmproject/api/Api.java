package asryab.com.mvvmproject.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import asryab.com.mvvmproject.api.requests.ApiAuth;
import asryab.com.mvvmproject.api.requests.ApiFeed;
import asryab.com.mvvmproject.api.requests.ApiIntro;
import asryab.com.mvvmproject.api.requests.ApiProfile;
import asryab.com.mvvmproject.api.utils.Config;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api
{
    static final int CONNECTION_TIMEOUT = 20;


    private static Api instance;
    private Retrofit retrofit;
    private Gson gson;

    private Api(){}

    public synchronized static Api getInst() {
        if (instance == null) {
            instance = new Api();
            instance.build();
        }
        return instance;
    }

    private void build() {
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new RetrofitInterceptor()).build();
        final OkHttpClient.Builder clientBuilder = Config.getClientBuilderWithCookieAction();

        clientBuilder.addNetworkInterceptor(new StethoInterceptor());
        clientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        final OkHttpClient client = clientBuilder.build();

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Config.getBaseUrl())
                .client(client)
                .build();

        gson = new GsonBuilder().create();
    }

    public Gson getGson() {
        return gson;
    }

    public ApiAuth auth() {
        return retrofit.create(ApiAuth.class);
    }

    public ApiIntro intro() {
        return retrofit.create(ApiIntro.class);
    }

    public ApiFeed feed() {
        return retrofit.create(ApiFeed.class);
    }

    public ApiProfile profile() {
        return retrofit.create(ApiProfile.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
