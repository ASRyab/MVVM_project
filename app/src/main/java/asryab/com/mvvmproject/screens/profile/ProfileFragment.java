package asryab.com.mvvmproject.screens.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentProfileBinding;
import asryab.com.mvvmproject.viewmodels.profile.ProfileVM;

public class ProfileFragment extends BaseFragment {
    private FragmentProfileBinding mProfileBinding;
    private ProfileVM mProfileVM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injectDataBinding(mProfileBinding = FragmentProfileBinding.inflate(inflater,container,false));
        mProfileVM = new ProfileVM(getActivity(),mProfileBinding);
        injectViewModel(mProfileVM);
        mProfileBinding.setViewModel(mProfileVM);

        return mProfileBinding.getRoot();
    }
}