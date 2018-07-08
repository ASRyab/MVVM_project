package asryab.com.mvvmproject.api.rx_tasks.poll;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.models.GetListResponse;
import asryab.com.mvvmproject.models.MoreLoadingInfo;
import asryab.com.mvvmproject.models.polls.Poll;
import rx.Observable;

public final class GetPollsByStatus extends ApiTask<GetListResponse<Poll>> {

    private FeedStatus mFeedStatus;
    private MoreLoadingInfo moreLoadingInfo;
    private String          search;
    private String          orderBy;
    private String          order;

    public GetPollsByStatus(FeedStatus status, MoreLoadingInfo moreLoadingInfo) {
        super();
        this.mFeedStatus = status;
        this.moreLoadingInfo = moreLoadingInfo;
    }

    @Override
    protected Observable<GetListResponse<Poll>> getObservableTask(Context _context) {
        return Api.getInst().feed().getPollsByStatus(mFeedStatus.toStringValue(), moreLoadingInfo.prepareQuery());
    }
}
