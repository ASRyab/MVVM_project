package asryab.com.mvvmproject.api.rx_tasks.auth;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.SuccessResponse;
import rx.Observable;

public class SignOut extends ApiTask<SuccessResponse> {
    @Override
    protected Observable<SuccessResponse> getObservableTask(Context _context) {
        return Api.getInst().auth().userSignOut();
    }
}
