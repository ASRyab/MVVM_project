package asryab.com.mvvmproject.api.rx_tasks.poll;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.GetListResponse;
import asryab.com.mvvmproject.models.MoreLoadingInfo;
import asryab.com.mvvmproject.models.polls.VoteComment;
import rx.Observable;

public final class GetAllThoughts extends ApiTask<GetListResponse<VoteComment>> {

    private int             pollId;
    private MoreLoadingInfo moreLoadingInfo;

    public GetAllThoughts(int pollId, MoreLoadingInfo moreLoadingInfo) {
        super();
        this.pollId = pollId;
        this.moreLoadingInfo = moreLoadingInfo;
    }

    @Override
    protected Observable<GetListResponse<VoteComment>> getObservableTask(Context _context) {
        return Api.getInst().feed().getAllThoughts(pollId, moreLoadingInfo.prepareQuery());
    }
}
