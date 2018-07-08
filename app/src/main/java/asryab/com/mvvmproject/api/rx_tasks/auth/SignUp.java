package asryab.com.mvvmproject.api.rx_tasks.auth;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.user.User;
import rx.Observable;

public class SignUp extends ApiTask<User> {

    private  String email;
    private  String password;
    private  String username;
    private  String pincode;
    private  String phone;
    private  String year;
    private  String gender;

    public SignUp(String email, String password, String username, String pincode, String phone,
                  String year, String gender) {
        super();
        this.email      = email;
        this.password   = password;
        this.username   = username;
        this.pincode    = pincode;
        this.phone      = phone;
        this.year       = year;
        this.gender     = gender;
    }

    @Override
    public Observable<User> getObservableTask(Context _context) {
        Map<String, String> map = new HashMap<>();
        map.put(EMAIL, email);
        map.put(PASSWORD, password);
        map.put(USERNAME, username);
        map.put(PIN_CODE, pincode);
        map.put(PHONE, phone);
        map.put(YEAR, String.valueOf(year));
        map.put(GENDER, String.valueOf(gender));
        return Api.getInst().auth().userSignup(map);
    }
}
