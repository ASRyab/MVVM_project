package asryab.com.mvvmproject.screens.feed.petitions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentPetitionsListBinding;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.feed.petitions.PetitionsListFragmentVM;

public class PetitionsListFragment extends BaseFragment {
    private FragmentPetitionsListBinding mFragmentPetitionsListBinding;
    private PetitionsListFragmentVM mPetitionsListFragmentVM;

    private FeedStatus mFeedStatusLoad; // Live or Closed

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        injectDataBinding(mFragmentPetitionsListBinding = FragmentPetitionsListBinding.inflate(inflater, container, false));
        injectViewModel(mPetitionsListFragmentVM = new PetitionsListFragmentVM(getContext(), mFeedStatusLoad));
        mFragmentPetitionsListBinding.setViewModel(mPetitionsListFragmentVM);

        return mFragmentPetitionsListBinding.getRoot();
    }

    @Override
    protected void readFromBundle(Bundle bundle) {
        super.readFromBundle(bundle);
        mFeedStatusLoad = (FeedStatus) bundle.getSerializable(Arguments.Fragment.ARG_POLLS_STATUS);
    }
}