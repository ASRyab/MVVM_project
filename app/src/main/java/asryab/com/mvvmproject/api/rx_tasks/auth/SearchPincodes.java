package asryab.com.mvvmproject.api.rx_tasks.auth;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.GetListResponse;
import asryab.com.mvvmproject.models.Pincode;
import rx.Observable;

public class SearchPincodes extends ApiTask<GetListResponse<Pincode>> {

    private String search;

    public SearchPincodes(String search) {
        this.search = search;
    }

    @Override
    protected Observable<GetListResponse<Pincode>> getObservableTask(Context _context) {
        return Api.getInst().auth().getPincodes(search);
    }
}
