package asryab.com.mvvmproject.viewmodels.feed;

import android.databinding.ObservableInt;
import android.os.Bundle;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.databinding.LayoutLiveClosedContentBinding;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.screens.feed.CustomScrollTabsView;
import asryab.com.mvvmproject.screens.feed.petitions.PetitionsListFragment;
import asryab.com.mvvmproject.screens.feed.polls.PollsListFragment;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public final class FeedActionFragmentVM extends ViewModel implements CustomScrollTabsView.IScrollTabsListener {

    public final ObservableInt              currentPosition = new ObservableInt(-1);
    private  LayoutLiveClosedContentBinding layoutLiveClosedFeedsFDA;

    private LiveClosedFeedsVM liveClosedFeedsVM;
    private CustomScrollTabsView customScrollTabsView;

    public FeedActionFragmentVM(final LiveClosedFeedsVM liveClosedFeedsVM, final CustomScrollTabsView customScrollTabsView, LayoutLiveClosedContentBinding layoutLiveClosedFeedsFDA) {
        this.liveClosedFeedsVM      = liveClosedFeedsVM;
        this.customScrollTabsView   = customScrollTabsView;
        this.layoutLiveClosedFeedsFDA = layoutLiveClosedFeedsFDA;
        customScrollTabsView.setScrollTabsListener(this);
        setCurrentFeed(0);
    }

    public void setCurrentFeedFromView(final View view) {
        switch (view.getId()) {
            case R.id.tvPolls_FFA:
                setCurrentFeed(0);
                break;
            case R.id.tvPetitions_FFA:
                setCurrentFeed(1);
                break;
        }
    }

    private void setCurrentFeed(int position) {
        if (currentPosition.get() != position) {
            currentPosition.set(position);
            customScrollTabsView.scrollByPosition(position);
            switch (position) {
                case 0:
                    liveClosedFeedsVM.setLiveClosedPages(
                            new PollsListFragment().withArgs(prepareBundleStatus(FeedStatus.OPENED)),
                            new PollsListFragment().withArgs(prepareBundleStatus(FeedStatus.CLOSED)));
                    break;
                case 1:
                    liveClosedFeedsVM.setLiveClosedPages(
                            new PetitionsListFragment().withArgs(prepareBundleStatus(FeedStatus.OPENED)),
                            new PetitionsListFragment().withArgs(prepareBundleStatus(FeedStatus.CLOSED)));
                    break;
            }
            layoutLiveClosedFeedsFDA.invalidateAll();
        }
    }

    private Bundle prepareBundleStatus(final FeedStatus feedStatus) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Arguments.Fragment.ARG_POLLS_STATUS, feedStatus);
        return bundle;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void tabChosen(int position) {
        setCurrentFeed(position);
    }
}
