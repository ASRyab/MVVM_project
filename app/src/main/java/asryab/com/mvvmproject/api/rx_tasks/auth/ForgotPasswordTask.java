package asryab.com.mvvmproject.api.rx_tasks.auth;

import android.content.Context;

import java.util.Objects;

import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import rx.Observable;

public class ForgotPasswordTask extends ApiTask<Objects> {

    private String mEmail;

    public ForgotPasswordTask(String email) {
        mEmail = email;
    }

    @Override
    protected Observable<Objects> getObservableTask(Context _context) {
        return null;
    }

}
