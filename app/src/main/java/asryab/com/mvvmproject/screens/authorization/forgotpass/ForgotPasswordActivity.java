package asryab.com.mvvmproject.screens.authorization.forgotpass;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseActivity;
import asryab.com.mvvmproject.databinding.ActivityForgotPasswordBinding;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;
import asryab.com.mvvmproject.viewmodels.authorization.forgotpass.ActivityForgotPasswordVM;
import asryab.com.mvvmproject.viewmodels.authorization.forgotpass.ForgotPasswordVM;

public class ForgotPasswordActivity extends BaseActivity implements ToolbarVM.OnBackPressedListener
        , ForgotPasswordVM.ClickButtonListener {

    private ActivityForgotPasswordBinding mBinding;
    private ActivityForgotPasswordVM mViewModel;

    private String mEmail                   = "";
    private boolean isFragmentCheckEmail    = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDataBinding(mBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password));
        injectViewModel(mViewModel = new ActivityForgotPasswordVM(this, getString(R.string.forgot_password)));
        mBinding.setViewModel(mViewModel);

        showFragment(ForgotPasswordFragment.getInstance(Arguments.Activity.FORGOT_TYPE, mEmail));
    }

    @Override
    public void onBackButtonPressed() {
        if(isFragmentCheckEmail) {
            isFragmentCheckEmail = false;
            showFragment(ForgotPasswordFragment.getInstance(Arguments.Activity.FORGOT_TYPE, ""));
        } else {
            onBackPressed();
        }
    }

    private void showFragment(ForgotPasswordFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(mBinding.flFragmentHolderFPA.getId(), fragment);
        transaction.commit();
    }

    @Override
    public void clickButton(String category, String email) {
        mEmail = email;
        switch (category) {
            case Arguments.Activity.SEND_EMAIL_ACTION:
                isFragmentCheckEmail = true;
                showFragment(ForgotPasswordFragment.getInstance(Arguments.Activity.CHECK_TYPE, mEmail));
                break;
            case Arguments.Activity.DONT_RECEIVE_ACTION:
                isFragmentCheckEmail = false;
                showFragment(ForgotPasswordFragment.getInstance(Arguments.Activity.FORGOT_TYPE, mEmail));
                break;
            case Arguments.Activity.START_SIGNIN_ACTION:
                finish();
                break;
        }
    }
}
