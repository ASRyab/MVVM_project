package asryab.com.mvvmproject.screens.feed.polls;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentPollDetailBinding;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;
import asryab.com.mvvmproject.viewmodels.feed.polls.PollDetailFragmentVM;

public final class PollDetailFragment extends BaseFragment implements ToolbarVM.OnBackPressedListener {

    private Poll mReceivedPoll;
    private FragmentPollDetailBinding   mBindingFragmentPoll;
    private PollDetailFragmentVM mViewModelFragmentPoll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injectDataBinding(mBindingFragmentPoll = FragmentPollDetailBinding.inflate(inflater, container, false));
        injectViewModel(mViewModelFragmentPoll = new PollDetailFragmentVM(getContext(), mReceivedPoll, this));
        mBindingFragmentPoll.setViewModel(mViewModelFragmentPoll);
        return mBindingFragmentPoll.getRoot();
    }

    @Override
    protected void readFromBundle(Bundle bundle) {
        mReceivedPoll = (Poll) bundle.getSerializable(Arguments.Fragment.ARG_POLL);
    }

    @Override
    public void onBackButtonPressed() {
        onBackPressed();
    }

    @Override
    protected void onBackPressed() {
        super.onBackPressed();
        sendNewestPoll();
    }

    private void sendNewestPoll() {
        final Intent intent = new Intent(Arguments.Broadcast.EVENT_UPDATE_POLL);
        intent.putExtra(Arguments.Broadcast.INTENT_KEY_POLL, mViewModelFragmentPoll.getActualPoll());
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

}
