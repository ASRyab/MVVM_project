package asryab.com.mvvmproject;

import android.app.Application;

import com.facebook.stetho.Stetho;

import asryab.com.mvvmproject.api.utils.Config;
import asryab.com.mvvmproject.data.DataStorage;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataStorage.initSharedPreference(this);
        initStetho();
    }

    private void initStetho() {
        if (!Config.isProduction)
            Stetho.initializeWithDefaults(this);
    }

}