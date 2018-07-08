package asryab.com.mvvmproject.screens.feed.petitions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentReadMorePetitionBinding;
import asryab.com.mvvmproject.models.petitions.Petition;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;
import asryab.com.mvvmproject.viewmodels.feed.petitions.PetitionVM;
import asryab.com.mvvmproject.viewmodels.feed.petitions.ReadMorePetitionVM;

public class ReadMorePetitionFragment extends BaseFragment implements ToolbarVM.OnBackPressedListener{
    private PetitionVM mPetitionVM;

    @Override
    public void onBackButtonPressed() {
        getActivity().onBackPressed();
    }

    private Petition mPetition;
    private ReadMorePetitionVM mReadMorePetitionVM;
    private FragmentReadMorePetitionBinding mReadMorePetitionBinding;
//
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injectDataBinding(mReadMorePetitionBinding = FragmentReadMorePetitionBinding.inflate(inflater,container,false));
        injectViewModel(mReadMorePetitionVM = new ReadMorePetitionVM(getContext(),mPetition,this));
        injectViewModel( mPetitionVM = new PetitionVM(getActivity(),true));
        mReadMorePetitionBinding.setViewModel(mReadMorePetitionVM);
        mReadMorePetitionBinding.setPetitionVM(mPetitionVM);
        mPetitionVM.updatePetition(mPetition);
        mReadMorePetitionBinding.executePendingBindings();
        return mReadMorePetitionBinding.getRoot();
    }

    @Override
    protected void readFromBundle(Bundle bundle) {
        mPetition = (Petition) bundle.getSerializable(Arguments.Fragment.ARG_PETITION_READ_MORE);
    }
}
