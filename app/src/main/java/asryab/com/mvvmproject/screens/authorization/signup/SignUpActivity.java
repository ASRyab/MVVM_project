package asryab.com.mvvmproject.screens.authorization.signup;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseActivity;
import asryab.com.mvvmproject.databinding.ActivitySignupBinding;
import asryab.com.mvvmproject.viewmodels.authorization.signup.SignUpVM;

public class SignUpActivity extends BaseActivity {

    public ActivitySignupBinding mBinding;
    private SignUpVM mViewModel;

    public static void startIt(final Context context) {
        final Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDataBinding(mBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup));
        injectViewModel(mViewModel = new SignUpVM(this));

        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.socialVM.onActivityResult(requestCode, resultCode, data);
    }

}
