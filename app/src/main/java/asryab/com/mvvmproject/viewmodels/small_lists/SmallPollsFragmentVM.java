package asryab.com.mvvmproject.viewmodels.small_lists;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import asryab.com.mvvmproject.abstracts.IBroadcastImpl;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.poll.GetPollsByStatus;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.screens.small_lists.SmallPollsAdapter;
import asryab.com.mvvmproject.utils.Arguments;

public final class SmallPollsFragmentVM extends BaseSmallListVM<Poll> implements IBroadcastImpl {

    private BroadcastReceiver pollUpdateReceiver;

    public SmallPollsFragmentVM(Context context, FeedStatus feedStatus) {
        super(context, feedStatus);

        registerBroadcast(context);

        pastAdapter = new SmallPollsAdapter();
        isVisibleCenterProgress.set(true);
        refresh();
    }

    @Override
    protected void loadData() {
        hideError();
        mCompositeSubscription.add(
                new GetPollsByStatus(feedStatus, moreLoadingInfo)
                .getTask(context)
                .subscribe(
                        pollGetListResponse -> {
                            loadFinished();

                            if (moreLoadingInfo.page == 1)
                                pastAdapter.clearAllData();

                            pastAdapter.addData(pollGetListResponse.data);

                            if (pollGetListResponse.data.size() == 0 || pastAdapter.getItemCount() >= pollGetListResponse.total)
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
    public void registerBroadcast(Context context) {
        LocalBroadcastManager.getInstance(context).registerReceiver(implReceiver(), new IntentFilter(Arguments.Broadcast.EVENT_UPDATE_POLL));
    }

    @Override
    public void unRegisterBroadcast(Context context) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(implReceiver());
    }

    @Override
    public BroadcastReceiver implReceiver() {
        if (pollUpdateReceiver == null) {
            pollUpdateReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    final Poll poll = (Poll) intent.getSerializableExtra(Arguments.Broadcast.INTENT_KEY_POLL);
                    Log.d("polls", "Update poll with ID: " + poll.id);
                    for (int i = 0; i < pastAdapter.getListData().size(); i++)
                        if (poll.id == pastAdapter.getListData().get(i).id) {
                            pastAdapter.updateItem(i, poll);
                            break;
                        }
                }
            };
        }
        return pollUpdateReceiver;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBroadcast(context);
    }
}
