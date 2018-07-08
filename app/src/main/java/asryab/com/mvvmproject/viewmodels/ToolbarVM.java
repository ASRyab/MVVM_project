package asryab.com.mvvmproject.viewmodels;

import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

public abstract class ToolbarVM extends ErrorLoadingVM {

    public final ObservableField<String> mTitle = new ObservableField<>();

    public abstract void onBackArrowPressed(View view);

    public interface OnBackPressedListener {
        void onBackButtonPressed();
    }

    @Override
    protected void loadData() {}

    @Override
    public void retry(View retryBtn) {}

    @Override
    protected LinearLayoutManager getLayoutManagerForMoreLoading() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
