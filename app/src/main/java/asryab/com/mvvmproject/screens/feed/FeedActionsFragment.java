package asryab.com.mvvmproject.screens.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentFeedActionsBinding;
import asryab.com.mvvmproject.viewmodels.feed.FeedActionFragmentVM;
import asryab.com.mvvmproject.viewmodels.feed.LiveClosedFeedsVM;

public final class FeedActionsFragment extends BaseFragment {

    private FragmentFeedActionsBinding  mBindingFeedActionsFragment;
    private FeedActionFragmentVM mViewModelFeedActionsFragment;
    private LiveClosedFeedsVM mViewModelLiveClosedFeeds;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        injectDataBinding(mBindingFeedActionsFragment = FragmentFeedActionsBinding.inflate(inflater, container, false));
        injectViewModel(mViewModelLiveClosedFeeds = new LiveClosedFeedsVM(mBindingFeedActionsFragment.layoutLiveClosedFeedsFDA.vpFeedPagesLVCC, getChildFragmentManager()));
        injectViewModel(mViewModelFeedActionsFragment = new FeedActionFragmentVM(mViewModelLiveClosedFeeds, mBindingFeedActionsFragment.sctvContainerFeedTabsFDA, mBindingFeedActionsFragment.layoutLiveClosedFeedsFDA));

        mBindingFeedActionsFragment.setViewFeedModel(mViewModelFeedActionsFragment);
        mBindingFeedActionsFragment.setViewLiveClosedModel(mViewModelLiveClosedFeeds);
        mBindingFeedActionsFragment.executePendingBindings();

        return mBindingFeedActionsFragment.getRoot();
    }
}
