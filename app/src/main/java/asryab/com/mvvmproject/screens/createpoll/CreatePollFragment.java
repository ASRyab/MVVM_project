package asryab.com.mvvmproject.screens.createpoll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentCreatePollBinding;
import asryab.com.mvvmproject.viewmodels.createpool.CreatePollVM;

public class CreatePollFragment extends BaseFragment {

    private CreatePollVM mViewModel;
    private FragmentCreatePollBinding   mBindingFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        injectDataBinding(mBindingFragment = FragmentCreatePollBinding.inflate(inflater, container, false));
        injectViewModel(mViewModel = new CreatePollVM(getContext()));

        mBindingFragment.setViewModel(mViewModel);
        mBindingFragment.executePendingBindings();

        return mBindingFragment.getRoot();
    }
}