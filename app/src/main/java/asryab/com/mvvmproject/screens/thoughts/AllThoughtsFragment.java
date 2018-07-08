package asryab.com.mvvmproject.screens.thoughts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentAllThoughtsBinding;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;
import asryab.com.mvvmproject.viewmodels.thoughts.AllThoughtsFragmentVM;

public final class AllThoughtsFragment extends BaseFragment implements ToolbarVM.OnBackPressedListener {

    private FragmentAllThoughtsBinding  mBindingAllThoughts;
    private AllThoughtsFragmentVM mViewModelAllThoughts;
    private int                         mPollId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injectDataBinding(mBindingAllThoughts = FragmentAllThoughtsBinding.inflate(inflater, container, false));
        injectViewModel(mViewModelAllThoughts = new AllThoughtsFragmentVM(getContext(), mPollId, this));
        mBindingAllThoughts.setViewModel(mViewModelAllThoughts);
        return mBindingAllThoughts.getRoot();
    }

    @Override
    protected void readFromBundle(Bundle bundle) {
        mPollId = bundle.getInt(Arguments.Fragment.ARG_POLL_ID);
    }

    @Override
    public void onBackButtonPressed() {
        getActivity().onBackPressed();
    }
}
