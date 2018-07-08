package asryab.com.mvvmproject.viewmodels.authorization;

import android.content.Intent;
import android.text.Editable;
import android.view.View;

import java.util.concurrent.TimeUnit;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.ErrorDialog;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.auth.Login;
import asryab.com.mvvmproject.data.DataStorage;
import asryab.com.mvvmproject.dialogs.ProgressDialog;
import asryab.com.mvvmproject.models.ErrorResponse;
import asryab.com.mvvmproject.models.user.User;
import asryab.com.mvvmproject.screens.authorization.forgotpass.ForgotPasswordActivity;
import asryab.com.mvvmproject.screens.authorization.login.LoginActivity;
import asryab.com.mvvmproject.screens.authorization.signup.SignUpActivity;
import asryab.com.mvvmproject.screens.home.HomeTabsActivity;
import asryab.com.mvvmproject.utils.KeyboardUtils;
import asryab.com.mvvmproject.utils.ValidationUtils;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;
import rx.Subscriber;
import rx.Subscription;

public class LoginActivityVM extends ToolbarVM implements SocialListener {

    private static final String LOG_TAG = LoginActivityVM.class.getSimpleName();

    private LoginActivity mActivity;
    public SocialVM socialVM;
    private Subscriber<Object> subscriber;
    private String mEmail;
    private String mPassword;
    private Subscription subClickLogIn;

    public final ProgressDialog progressDialog;

    public LoginActivityVM(LoginActivity activity) {
        mActivity       = activity;
        progressDialog  = new ProgressDialog(activity);
        mTitle          .set(activity.getString(R.string.log_in));

        initSocialButton();
    }

    private void initSocialButton() {
        socialVM = new SocialVM(mActivity, this);
        mActivity.mBindingLoginActivity.setSocialVM(socialVM);
    }

    public void setEmail(Editable s){
        mEmail = s.toString();
    }

    public void setPassword(Editable s){
        mPassword = s.toString();
    }

    public void clickLogIn(View v) {
        KeyboardUtils.hideSoftKeyboard(mActivity);
        if (validData()) {
            progressDialog.show();
            mCompositeSubscription.add(
            subClickLogIn = new Login(mEmail, mPassword).getTask(mActivity)
                    .delaySubscription(3000, TimeUnit.MILLISECONDS)
                    .subscribe(this::saveUser
                            , throwable -> {
                                mCompositeSubscription.add(ApiTask.errorResponseObservable(mActivity, throwable,false));
                                //todo need refactor
                                ErrorResponse throwable1 = (ErrorResponse) throwable;
                                ErrorDialog.show(mActivity, throwable1.getMessage());
                                progressDialog.dismiss();
                            }
                            , progressDialog::dismiss
                    ));
        }
    }

    private void saveUser(User user) {
        DataStorage.saveUser(user);
        HomeTabsActivity.startWith(mActivity, null, null, true);
    }

    private boolean validData() {
        if (!ValidationUtils.isValidEmail(mEmail)) {
            ErrorDialog.show(mActivity, mActivity.getString(R.string.email_invalid));
            return false;
        }
        if (!ValidationUtils.isValidPassword(mPassword)) {
            ErrorDialog.show(mActivity, mActivity.getString(R.string.password_invalid));
            return false;
        }
        return true;
    }

    public void clickForgot(View v) {
        mActivity.startActivity(new Intent(mActivity, ForgotPasswordActivity.class));
    }

    public void clickSignUp(View v) {
        SignUpActivity.startIt(mActivity);
        mActivity.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackArrowPressed(View view) {
        mActivity.onBackPressed();
    }

    @Override
    public void socialUser(User user) {

    }
}
