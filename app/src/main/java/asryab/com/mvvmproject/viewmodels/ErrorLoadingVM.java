package asryab.com.mvvmproject.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

public abstract class ErrorLoadingVM extends PaginationVM {

    public final ObservableBoolean          isShowErrorContainer    = new ObservableBoolean(false);
    public final ObservableBoolean          isShowRetryButton       = new ObservableBoolean(false);
    public final ObservableField<String>    errorMessage            = new ObservableField<>();

    protected void showError(final String msg, final boolean visibleRetry) {
        errorMessage            .set(msg);
        isShowRetryButton       .set(visibleRetry);
        isShowErrorContainer    .set(true);
    }

    protected void hideError() {
        isShowErrorContainer    .set(false);
    }

    @Override
    protected void loadData() {}

    @Override
    protected LinearLayoutManager getLayoutManagerForMoreLoading() {
        return null;
    }

    public abstract void retry(final View retryBtn);

}
