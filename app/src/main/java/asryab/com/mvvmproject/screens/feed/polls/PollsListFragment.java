package asryab.com.mvvmproject.screens.feed.polls;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentPollsListBinding;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.feed.polls.PollsListFragmentVM;

public final class PollsListFragment extends BaseFragment {

    private FragmentPollsListBinding    mBindingFragmentPollsList;
    private PollsListFragmentVM mViewModelFragmentPollsList;

    private FeedStatus mFeedStatusLoad; // Live or Closed

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        injectDataBinding(mBindingFragmentPollsList = FragmentPollsListBinding.inflate(inflater, container, false));
        injectViewModel(mViewModelFragmentPollsList = new PollsListFragmentVM(getContext(), mFeedStatusLoad));
        mBindingFragmentPollsList.setViewModel(mViewModelFragmentPollsList);

        return mBindingFragmentPollsList.getRoot();
    }

    @Override
    protected void readFromBundle(Bundle bundle) {
        super.readFromBundle(bundle);
        mFeedStatusLoad = (FeedStatus) bundle.getSerializable(Arguments.Fragment.ARG_POLLS_STATUS);
    }
}
