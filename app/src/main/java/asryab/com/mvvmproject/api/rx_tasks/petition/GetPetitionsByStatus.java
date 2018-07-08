package asryab.com.mvvmproject.api.rx_tasks.petition;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.models.GetListResponse;
import asryab.com.mvvmproject.models.MoreLoadingInfo;
import asryab.com.mvvmproject.models.petitions.Petition;
import rx.Observable;

public final class GetPetitionsByStatus extends ApiTask<GetListResponse<Petition>> {

    private FeedStatus mFeedStatus;
    private MoreLoadingInfo moreLoadingInfo;
    private String          search;
    private String          orderBy;
    private String          order;

    public GetPetitionsByStatus(FeedStatus status, MoreLoadingInfo moreLoadingInfo) {
        super();
        this.mFeedStatus = status;
        this.moreLoadingInfo = moreLoadingInfo;
    }

    @Override
    protected Observable<GetListResponse<Petition>> getObservableTask(Context _context) {
        return Api.getInst().feed().getPetitionsByStatus(mFeedStatus.toStringValue(), moreLoadingInfo.prepareQuery());
    }
}
