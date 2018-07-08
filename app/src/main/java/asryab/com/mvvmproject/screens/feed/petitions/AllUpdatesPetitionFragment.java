package asryab.com.mvvmproject.screens.feed.petitions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentAllUpdatesPetitionBinding;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;
import asryab.com.mvvmproject.viewmodels.feed.petitions.updates.AllUpdatesPetitionVM;


public class AllUpdatesPetitionFragment extends BaseFragment implements ToolbarVM.OnBackPressedListener {

    @Override
    public void onBackButtonPressed() {
        getActivity().onBackPressed();
    }

    private int idPetition;
    private AllUpdatesPetitionVM mAllUpdatesPetitionVM;
    private FragmentAllUpdatesPetitionBinding mAllUpdatesPetitionBinding;

    //
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injectDataBinding(mAllUpdatesPetitionBinding = FragmentAllUpdatesPetitionBinding.inflate(inflater, container, false));
        injectViewModel(mAllUpdatesPetitionVM = new AllUpdatesPetitionVM(getContext(),idPetition, this));
        mAllUpdatesPetitionBinding.setViewModel(mAllUpdatesPetitionVM);
        return mAllUpdatesPetitionBinding.getRoot();
    }

    @Override
    protected void readFromBundle(Bundle bundle) {
        idPetition = bundle.getInt(Arguments.Fragment.ARG_PETITION_ALL_UPDATES);
    }
}

