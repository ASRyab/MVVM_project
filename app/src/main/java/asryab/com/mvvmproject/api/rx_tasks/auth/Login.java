package asryab.com.mvvmproject.api.rx_tasks.auth;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.user.User;
import rx.Observable;


public class Login extends ApiTask<User> {


    private  String email;
    private  String password;

    public Login(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    @Override
    public Observable<User> getObservableTask(Context _context) {
        Map<String, String> map = new HashMap<>();
        map.put(EMAIL, email);
        map.put(PASSWORD, password);
        map.put(DEVICE_OS, "android");
        map.put(DEVICE_TOKEN, "device");
        map.put(PUSH_TOKEN, "push");
        return Api.getInst().auth().userLogin(map);
    }

}