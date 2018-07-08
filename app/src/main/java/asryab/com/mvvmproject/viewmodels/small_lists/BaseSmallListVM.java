package asryab.com.mvvmproject.viewmodels.small_lists;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerAdapter;
import asryab.com.mvvmproject.abstracts.recycler.VerticalFillColorItemDecoration;
import asryab.com.mvvmproject.abstracts.recycler.pagination.RefreshLayout;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.models.MoreLoadingInfo;
import asryab.com.mvvmproject.viewmodels.ErrorLoadingVM;

public abstract class BaseSmallListVM<T> extends ErrorLoadingVM implements RefreshLayout.OnRefreshListener {

    public FeedStatus feedStatus;
    public Context                                  context;
    public LinearLayoutManager                      overScrollableManager;
    public BaseRecyclerAdapter<T> pastAdapter;
    public final VerticalFillColorItemDecoration itemDecoration;
    public final ObservableBoolean                  isVisibleCenterProgress = new ObservableBoolean(false);

    public BaseSmallListVM(Context context, FeedStatus feedStatus) {
        this.context                = context;
        this.feedStatus             = feedStatus;
        this.itemDecoration         = new VerticalFillColorItemDecoration(context, R.drawable.divider_thoughts);
        this.overScrollableManager  = new LinearLayoutManager(context);
    }

    @Override
    protected void loadFinished() {
        super.loadFinished();
        isVisibleCenterProgress.set(false);
    }

    protected void showDefaultErrorIfNeed(final boolean isErrorResponse) {
        hideError();
        if (isErrorResponse) {
            if (pastAdapter.getItemCount() == 0)
                showError(context.getString(R.string.error_internet_connection), true);
            else Toast.makeText(context, context.getString(R.string.error_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            if (pastAdapter.getItemCount() == 0)
                showError(context.getString(R.string.error_empty_data), false);
        }
    }

    protected void showErrorResponse(final String msg) {
        hideError();
        if (pastAdapter.getItemCount() == 0)
            showError(msg, true);
        else Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void retry(View retryBtn) {
        isVisibleCenterProgress.set(true);
        refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        overScrollableManager = null;
        context = null;
    }

    @Override
    protected LinearLayoutManager getLayoutManagerForMoreLoading() {
        return overScrollableManager;
    }

    @Override
    protected int getMoreLoadingCount() {
        return MoreLoadingInfo.DEFAULT_PAGE_COUNT_OTHER;
    }

}
