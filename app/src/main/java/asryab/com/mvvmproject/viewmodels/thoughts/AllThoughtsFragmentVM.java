package asryab.com.mvvmproject.viewmodels.thoughts;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.VerticalFillColorItemDecoration;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.poll.GetAllThoughts;
import asryab.com.mvvmproject.models.MoreLoadingInfo;
import asryab.com.mvvmproject.screens.thoughts.ThoughtsAdapter;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;

public final class AllThoughtsFragmentVM extends ToolbarVM {

    public LinearLayoutManager              thoughtsLayoutManager;
    public ThoughtsAdapter thoughtsAdapter;
    public VerticalFillColorItemDecoration thoughtsDividerDecoration;
    public final ObservableBoolean          isVisibleCenterProgress = new ObservableBoolean(false);

    private Context                         context;
    private OnBackPressedListener           backPressedListener;
    private final int                       pollId;

    public AllThoughtsFragmentVM(final Context context, final int pollId, final OnBackPressedListener backPressedListener) {
        this.context                    = context;
        this.pollId                     = pollId;
        this.backPressedListener        = backPressedListener;
        this.thoughtsLayoutManager      = new LinearLayoutManager(context);
        this.thoughtsAdapter            = new ThoughtsAdapter(true);
        this.thoughtsDividerDecoration  = new VerticalFillColorItemDecoration(context, R.drawable.divider_thoughts);

        mTitle                          .set(context.getString(R.string.all_thoughts));

        this.isVisibleCenterProgress    .set(true);
        refresh();
    }

    @Override
    public void onBackArrowPressed(View view) {
        backPressedListener.onBackButtonPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context = null;
    }

    @Override
    protected void loadData() {
        hideError();
        mCompositeSubscription.add(
                new GetAllThoughts(pollId, moreLoadingInfo)
                        .getTask(context)
                        .subscribe(voteCommentGetListResponse -> {
                            loadFinished();

                            if (moreLoadingInfo.page == 1)
                                thoughtsAdapter.clearAllData();

                            thoughtsAdapter.addData(voteCommentGetListResponse.data);

                            if (voteCommentGetListResponse.data.size() == 0 || thoughtsAdapter.getItemCount() >= voteCommentGetListResponse.total)
                                moreLoadingInfo.finishPage();

                            showDefaultErrorIfNeed(false);
                        }, throwable -> {
                            loadFinished();

                            moreLoadingInfo.errorLoadMore();
                            mCompositeSubscription.add(ApiTask.errorResponseObservable(context, throwable,
                                    () -> showDefaultErrorIfNeed(true), errorResponse -> showErrorResponse(errorResponse.getError())));
                        }));
    }

    @Override
    protected void loadFinished() {
        super.loadFinished();
        isVisibleCenterProgress.set(false);
    }

    @Override
    protected LinearLayoutManager getLayoutManagerForMoreLoading() {
        return thoughtsLayoutManager;
    }

    @Override
    public void retry(View retryBtn) {
        isVisibleCenterProgress.set(true);
        refresh();
    }

    @Override
    protected int getMoreLoadingCount() {
        return MoreLoadingInfo.DEFAULT_PAGE_COUNT_OTHER;
    }

    private void showDefaultErrorIfNeed(final boolean isErrorResponse) {
        hideError();
        if (isErrorResponse) {
            if (thoughtsAdapter.getItemCount() == 0)
                showError(context.getString(R.string.error_internet_connection), true);
            else Toast.makeText(context, context.getString(R.string.error_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            if (thoughtsAdapter.getItemCount() == 0)
                showError(context.getString(R.string.error_empty_data), false);
        }
    }

    private void showErrorResponse(final String msg) {
        hideError();
        if (thoughtsAdapter.getItemCount() == 0)
            showError(msg, true);
        else Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
