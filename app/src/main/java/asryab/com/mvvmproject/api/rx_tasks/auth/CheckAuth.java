package asryab.com.mvvmproject.api.rx_tasks.auth;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.CheckAuthResponse;
import rx.Observable;

public class CheckAuth extends ApiTask<CheckAuthResponse> {
    @Override
    protected Observable<CheckAuthResponse> getObservableTask(Context _context) {
        return Api.getInst().auth().checkAuth();
    }
}
