package asryab.com.mvvmproject.viewmodels.feed.polls;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.poll.GetPollByID;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.screens.feed.polls.PollsAdapter;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;

public class PollDetailFragmentVM extends ToolbarVM {

    public final PollsAdapter pollsAdapter = new PollsAdapter(true);
    public final LinearLayoutManager    pollsLayoutManager;
    private final OnBackPressedListener mBackPressedListener;
    private Poll mDisplayedPoll;

    private final Context               mContext;

    public PollDetailFragmentVM(final Context context, final Poll displayedPoll, final OnBackPressedListener backPressedListener) {
        this.mContext               = context;
        this.mTitle                 .set(context.getString(R.string.poll_details));
        this.mDisplayedPoll         = displayedPoll;
        this.mBackPressedListener   = backPressedListener;
        this.pollsLayoutManager     = new LinearLayoutManager(mContext);

        pollsAdapter.addData(new ArrayList<>(Collections.singletonList(mDisplayedPoll)));
    }

    @Override
    protected void loadData() {
        mCompositeSubscription.add(
                new GetPollByID(mDisplayedPoll.id)
                    .getTask(mContext)
                    .subscribe(poll -> {
                        loadFinished();

                        // TODO if need save previous state tab
//                        poll.statisticViewNowPos    = mDisplayedPoll.statisticViewNowPos;
//                        poll.isShowMore             = mDisplayedPoll.isShowMore;
                        mDisplayedPoll              = poll;

                        pollsAdapter.clearAllData();
                        pollsAdapter.addData(new ArrayList<>(Collections.singletonList(mDisplayedPoll)));
                    }, throwable -> {
                        loadFinished();
                        mCompositeSubscription.add(ApiTask.errorResponseObservable(mContext, throwable,
                                () -> showError(mContext.getString(R.string.error_internet_connection)),
                                errorResponse -> showError(errorResponse.getError())));
                    })
        );
    }

    private void showError(final String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public Poll getActualPoll() {
        return mDisplayedPoll;
    }

    @Override
    public void onBackArrowPressed(View view) {
        mBackPressedListener.onBackButtonPressed();
    }

}
