package asryab.com.mvvmproject.screens.authorization.forgotpass;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.BR;
import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.viewmodels.authorization.forgotpass.ForgotPasswordVM;

public class ForgotPasswordFragment extends BaseFragment {

    private String mType;
    private String mEmail;
    private ViewDataBinding mBinding;
    private ForgotPasswordVM mViewModel;
    private static final String TYPE    = "type";
    private static final String EMAIL   = "email";

    public static ForgotPasswordFragment getInstance(String type, @Nullable String email) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();

        Bundle args = new Bundle();
        args.putString(TYPE, type);
        if(!TextUtils.isEmpty(email)) {
            args.putString(EMAIL, email);
        }
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mType   = getArguments().getString(TYPE, "");
        mEmail  = getArguments().getString(EMAIL, "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding    = FragmentUtils.getForgotPasswordScreen(mType, inflater, container);
        mViewModel  = new ForgotPasswordVM(getContext(), (ForgotPasswordActivity) getActivity());

        injectDataBinding(mBinding);
        injectViewModel(mViewModel);

        mBinding.setVariable(BR.viewModel, mViewModel);
        if(!TextUtils.isEmpty(mEmail)) {
            mViewModel.setEmail(mType, mEmail);
        }

        return mBinding.getRoot();
    }
}
