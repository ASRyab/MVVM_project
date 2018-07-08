package asryab.com.mvvmproject.api.rx_tasks.profile;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.user.User;
import rx.Observable;

public class GetProfile extends ApiTask<User> {
    public GetProfile() {
        super();
    }
    @Override
    protected Observable<User> getObservableTask(Context _context) {
        return Api.getInst().profile().profile();
    }
}


