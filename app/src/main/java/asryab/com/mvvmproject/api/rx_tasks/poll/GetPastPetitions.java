package asryab.com.mvvmproject.api.rx_tasks.poll;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.GetListResponse;
import asryab.com.mvvmproject.models.MoreLoadingInfo;
import asryab.com.mvvmproject.models.petitions.Petition;
import rx.Observable;

public final class GetPastPetitions extends ApiTask<GetListResponse<Petition>> {

    private MoreLoadingInfo moreLoadingInfo;
    private String          search;
    private String          orderBy;
    private String          order;

    public GetPastPetitions(MoreLoadingInfo moreLoadingInfo) {
        super();
        this.moreLoadingInfo = moreLoadingInfo;
    }

    @Override
    protected Observable<GetListResponse<Petition>> getObservableTask(Context _context) {
        return Api.getInst().feed().getPetitionsByStatus("past", moreLoadingInfo.prepareQuery());
    }
}
