package asryab.com.mvvmproject.api.rx_tasks.auth;

import android.content.Context;

import java.util.ArrayList;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.intro.IntroPageModel;
import rx.Observable;

public final class GetIntros extends ApiTask<ArrayList<IntroPageModel>> {

    public GetIntros() {
        super(true);
    }

    @Override
    protected Observable<ArrayList<IntroPageModel>> getObservableTask(Context _context) {
        return Api.getInst().intro().getIntros();
    }
}
