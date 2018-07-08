package asryab.com.mvvmproject.screens.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentPollAdsPageBinding;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.search.PollPageFragmentVM;

public final class PollPageAdsFragment extends BaseFragment {

    private FragmentPollAdsPageBinding  mBindingPollPage;
    private PollPageFragmentVM mViewModelPollPage;
    private Poll mPollModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injectDataBinding(mBindingPollPage = FragmentPollAdsPageBinding.inflate(inflater, container, false));
        injectViewModel(mViewModelPollPage = new PollPageFragmentVM(getContext(), mPollModel));
        mBindingPollPage.setViewModel(mViewModelPollPage);
        return mBindingPollPage.getRoot();
    }

    @Override
    protected void readFromBundle(Bundle bundle) {
        mPollModel = (Poll) bundle.getSerializable(Arguments.Fragment.ARG_PAGE_POLL_MODEL);
    }

    public void setScrollOffset(final float pageOffset) {
        if (mViewModelPollPage != null)
            mViewModelPollPage.setOffsetTitles(pageOffset);
    }

}
