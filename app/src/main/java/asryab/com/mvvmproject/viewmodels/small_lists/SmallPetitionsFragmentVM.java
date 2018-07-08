package asryab.com.mvvmproject.viewmodels.small_lists;

import android.content.Context;

import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.petition.GetPetitionsByStatus;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.models.petitions.Petition;
import asryab.com.mvvmproject.screens.small_lists.SmallPetitionsAdapter;

public final class SmallPetitionsFragmentVM extends BaseSmallListVM<Petition> {

    public SmallPetitionsFragmentVM(Context context, FeedStatus feedStatus) {
        super(context, feedStatus);

        pastAdapter = new SmallPetitionsAdapter();

        isVisibleCenterProgress.set(true);
        refresh();
    }

    @Override
    protected void loadData() {
        hideError();
        mCompositeSubscription.add(
                new GetPetitionsByStatus(feedStatus, moreLoadingInfo)
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

}
