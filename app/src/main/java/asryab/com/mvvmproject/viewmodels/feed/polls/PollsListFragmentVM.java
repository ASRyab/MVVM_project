package asryab.com.mvvmproject.viewmodels.feed.polls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import asryab.com.mvvmproject.abstracts.IBroadcastImpl;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.poll.GetPollsByStatus;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.screens.feed.polls.PollsAdapter;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.feed.BaseFeedFragmentVM;

public final class PollsListFragmentVM extends BaseFeedFragmentVM implements IBroadcastImpl {

    public static final String TAG = "pollsTag";

    public PollsAdapter pollsAdapter;
    private BroadcastReceiver   pollVotedReceiver;


    public PollsListFragmentVM(final Context context, final FeedStatus status) {
        super(context, status);

        this.pollsAdapter = new PollsAdapter();
        registerBroadcast(context);

        isVisibleCenterProgress.set(true);
        refresh();
    }

    protected void loadData() {
        hideError();
        mCompositeSubscription.add(
            new GetPollsByStatus(mFeedStatus, moreLoadingInfo)
                    .getTask(context)
                    .subscribe(
                            pollGetListResponse -> {
                                loadFinished();

                                if (moreLoadingInfo.page == 1)
                                    pollsAdapter.clearAllData();

                                pollsAdapter.addData(pollGetListResponse.data);

                                if (pollGetListResponse.data.size() == 0 || pollsAdapter.getItemCount() >= pollGetListResponse.total)
                                    moreLoadingInfo.finishPage();

                                showDefaultErrorIfNeed(false);
                            },
                            throwable -> {
                                loadFinished();
                                moreLoadingInfo.errorLoadMore();

                                mCompositeSubscription.add(ApiTask.errorResponseObservable(context, throwable,
                                        () -> showDefaultErrorIfNeed(true), errorResponse -> showErrorResponse(errorResponse.getError())));
                            })
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBroadcast(context);
        context         = null;
        pollsAdapter    = null;
    }

    @Override
    public void registerBroadcast(Context context) {
        LocalBroadcastManager.getInstance(context).registerReceiver(implReceiver(), new IntentFilter(Arguments.Broadcast.EVENT_UPDATE_POLL));
    }

    @Override
    public void unRegisterBroadcast(Context context) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(implReceiver());
    }

    @Override
    public BroadcastReceiver implReceiver() {
        if (pollVotedReceiver == null)
            pollVotedReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    final Poll poll = (Poll) intent.getSerializableExtra(Arguments.Broadcast.INTENT_KEY_POLL);
                    for (int i = 0; i < pollsAdapter.getListData().size(); i++)
                        if (poll.id == pollsAdapter.getListData().get(i).id) {
                            pollsAdapter.updateItem(i, poll);
                            break;
                        }
                }
            };
        return pollVotedReceiver;
    }
}
