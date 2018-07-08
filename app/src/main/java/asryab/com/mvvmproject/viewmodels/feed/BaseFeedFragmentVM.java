package asryab.com.mvvmproject.viewmodels.feed;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.VerticalSpaceItemDecoration;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.viewmodels.ErrorLoadingVM;

public abstract class BaseFeedFragmentVM extends ErrorLoadingVM {

    public Context                      context;
    public FeedStatus mFeedStatus;
    public LinearLayoutManager          layoutManager;
    public VerticalSpaceItemDecoration verticalSpaceItemDecoration;
    public final ObservableBoolean      isVisibleCenterProgress = new ObservableBoolean(false);


    public BaseFeedFragmentVM(Context context, FeedStatus feedStatus) {
        this.context                        = context;
        this.mFeedStatus                    = feedStatus;
        this.layoutManager                  = new LinearLayoutManager(context);
        this.verticalSpaceItemDecoration    = new VerticalSpaceItemDecoration((int) context.getResources().getDimension(R.dimen.app_container_padding_half));
    }

    @Override
    public void retry(View retryBtn) {
        isVisibleCenterProgress.set(true);
        refresh();
    }

    @Override
    protected void loadFinished() {
        super.loadFinished();
        isVisibleCenterProgress.set(false);
    }

    protected void showDefaultErrorIfNeed(final boolean isErrorResponse) {
        hideError();
        if (isErrorResponse) {
            if (layoutManager.getItemCount() == 0)
                showError(context.getString(R.string.error_internet_connection), true);
            else Toast.makeText(context, context.getString(R.string.error_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            if (layoutManager.getItemCount() == 0)
                showError(context.getString(R.string.error_empty_data), false);
        }
    }

    protected void showErrorResponse(final String msg) {
        hideError();
        if (layoutManager.getItemCount() == 0)
            showError(msg, true);
        else Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected LinearLayoutManager getLayoutManagerForMoreLoading() {
        return layoutManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        layoutManager = null;
        context = null;
    }
}
