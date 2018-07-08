package asryab.com.mvvmproject.viewmodels.feed.petitions;

import android.content.Context;

import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.petition.GetPetitionsByStatus;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.screens.feed.petitions.PetitionsAdapter;
import asryab.com.mvvmproject.viewmodels.feed.BaseFeedFragmentVM;

public class PetitionsListFragmentVM extends BaseFeedFragmentVM {

    public PetitionsAdapter petitionsAdapter;

    public PetitionsListFragmentVM(final Context context, final FeedStatus status) {
        super(context, status);

        this.petitionsAdapter = new PetitionsAdapter();

        isVisibleCenterProgress.set(true);
        refresh();
    }

    protected void loadData() {
        hideError();
        mCompositeSubscription.add(
        new GetPetitionsByStatus(mFeedStatus, moreLoadingInfo).getTask(context)
                .subscribe(
                        petitionGetListResponse -> {
                            loadFinished();

                            if (moreLoadingInfo.page == 1)
                                petitionsAdapter.clearAllData();

                            petitionsAdapter.addData(petitionGetListResponse.data);

                            if (petitionGetListResponse.data.size() == 0 || petitionsAdapter.getItemCount() >= petitionGetListResponse.total)
                                moreLoadingInfo.finishPage();

                            showDefaultErrorIfNeed(false);
                        },
                        throwable -> {
                            loadFinished();
                            moreLoadingInfo.errorLoadMore();

                            mCompositeSubscription.add(ApiTask.errorResponseObservable(context, throwable,
                                    () -> showDefaultErrorIfNeed(true), errorResponse -> showErrorResponse(errorResponse.getError())));
                        }));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        petitionsAdapter = null;
        mCompositeSubscription.unsubscribe();
    }

}
