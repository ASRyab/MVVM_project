package asryab.com.mvvmproject.viewmodels.feed.petitions.updates;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.VerticalFillColorItemDecoration;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.petition.GetAllUpdates;
import asryab.com.mvvmproject.models.MoreLoadingInfo;
import asryab.com.mvvmproject.screens.feed.petitions.updates.UpdatesAdapter;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;

public class AllUpdatesPetitionVM extends ToolbarVM {

    private final Context                           context;
    private final OnBackPressedListener   onBackPressedListener;
    public LinearLayoutManager                      updatesLayoutManager;
    public VerticalFillColorItemDecoration updatesDividerDecoration;
    public UpdatesAdapter updatesAdapter;
    public final ObservableBoolean                  isVisibleCenterProgress = new ObservableBoolean(false);
    private int                                     idPetition;

    public AllUpdatesPetitionVM(Context context, int idPetition, OnBackPressedListener onBackPressedListener) {
        super();
        this.context = context;
        this.idPetition = idPetition;
        this.onBackPressedListener = onBackPressedListener;
        this.updatesLayoutManager = new LinearLayoutManager(context);
        this.updatesDividerDecoration = new VerticalFillColorItemDecoration(context, R.drawable.divider_thoughts);
        this.updatesAdapter = new UpdatesAdapter();
        mTitle.set(context.getString(R.string.all_updates));

        isVisibleCenterProgress.set(true);
        refresh();
    }

    @Override
    public void onBackArrowPressed(View view) {
        onBackPressedListener.onBackButtonPressed();
    }

    @Override
    protected void loadData() {
        hideError();
        mCompositeSubscription.add(
                new GetAllUpdates(idPetition, moreLoadingInfo)
                        .getTask(context)
                        .subscribe(updates -> {
                                    loadFinished();

                                    if (moreLoadingInfo.page == 1)
                                        updatesAdapter.clearAllData();

                                    updatesAdapter.addData(updates.data);

                                    if (updates.data.size() == 0 || updatesAdapter.getItemCount() >= updates.total)
                                        moreLoadingInfo.finishPage();

                                }, errorModel -> {
                                    loadFinished();

                                    moreLoadingInfo.errorLoadMore();
                            mCompositeSubscription.add(ApiTask.errorResponseObservable(context, errorModel,
                                    () -> showDefaultErrorIfNeed(true), errorResponse -> showErrorResponse(errorResponse.getError())));
                                })
                        );
    }

    @Override
    protected void loadFinished() {
        super.loadFinished();
        isVisibleCenterProgress.set(false);
    }

    @Override
    protected LinearLayoutManager getLayoutManagerForMoreLoading() {
        return updatesLayoutManager;
    }

    @Override
    protected int getMoreLoadingCount() {
        return MoreLoadingInfo.DEFAULT_PAGE_COUNT_OTHER;
    }

    private void showDefaultErrorIfNeed(final boolean isErrorResponse) {
        hideError();
        if (isErrorResponse) {
            if (updatesAdapter.getItemCount() == 0)
                showError(context.getString(R.string.error_internet_connection), true);
            else Toast.makeText(context, context.getString(R.string.error_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            if (updatesAdapter.getItemCount() == 0)
                showError(context.getString(R.string.error_empty_data), false);
        }
    }

    private void showErrorResponse(final String msg) {
        hideError();
        if (updatesAdapter.getItemCount() == 0)
            showError(msg, true);
        else Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void retry(View retryBtn) {
        isVisibleCenterProgress.set(true);
        refresh();
    }


}
