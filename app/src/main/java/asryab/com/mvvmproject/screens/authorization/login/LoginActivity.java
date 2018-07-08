package asryab.com.mvvmproject.screens.authorization.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseActivity;
import asryab.com.mvvmproject.databinding.ActivityLoginBinding;
import asryab.com.mvvmproject.viewmodels.authorization.LoginActivityVM;

public class LoginActivity extends BaseActivity {

    public ActivityLoginBinding mBindingLoginActivity;
    private LoginActivityVM mLoginActivityVM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDataBinding(mBindingLoginActivity = DataBindingUtil.setContentView(this, R.layout.activity_login));
        injectViewModel(mLoginActivityVM = new LoginActivityVM(this));

        mBindingLoginActivity.setViewModel(mLoginActivityVM);

    }

    public static void startIt(final Context context) {
        final Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void startItAlone(final Context context) {
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginActivityVM.socialVM.onActivityResult(requestCode, resultCode, data);
    }
}
