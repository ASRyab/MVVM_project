package asryab.com.mvvmproject.api.rx_tasks.petition;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.GetListResponse;
import asryab.com.mvvmproject.models.MoreLoadingInfo;
import asryab.com.mvvmproject.models.petitions.Update;
import rx.Observable;

public class GetAllUpdates extends ApiTask<GetListResponse<Update>> {

    private int             idPetition;
    private MoreLoadingInfo moreLoadingInfo;

    public GetAllUpdates(int idPetition, MoreLoadingInfo moreLoadingInfo) {
        super();
        this.idPetition = idPetition;
        this.moreLoadingInfo = moreLoadingInfo;
    }

    @Override
    protected Observable<GetListResponse<Update>> getObservableTask(Context _context) {
        return Api.getInst().feed().getAllUpdates(idPetition, moreLoadingInfo.prepareQuery());
    }
}