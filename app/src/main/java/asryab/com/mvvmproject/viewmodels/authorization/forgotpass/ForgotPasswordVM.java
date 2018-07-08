package asryab.com.mvvmproject.viewmodels.authorization.forgotpass;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.ErrorDialog;
import asryab.com.mvvmproject.binding.BindableString;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.utils.ValidationUtils;
import asryab.com.mvvmproject.viewmodels.ViewModel;
import rx.Subscriber;

public class ForgotPasswordVM extends ViewModel {

    private Context mContext;
    private ClickButtonListener mListener;
    public final BindableString mEmail = new BindableString();
    public final ObservableField<String> mEmailResult = new ObservableField<>();
    public final ObservableBoolean mIsProgressVisible = new ObservableBoolean(false);
    public final ObservableBoolean mIsSendButtonClickable = new ObservableBoolean(true);
    private Subscriber<R> mSubscriber;

    public ForgotPasswordVM(Context context, ClickButtonListener listener) {
        mContext    = context;
        mListener   = listener;
        initSubscriber();
    }

    public void clickButton(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnSendEmail_FPF:
                mIsSendButtonClickable.set(false);
                //TODO request from server
                sendEmail(mEmail.get());
                break;
            case R.id.btnDontReceive_FPF:
                mListener.clickButton(Arguments.Activity.DONT_RECEIVE_ACTION, mEmail.get());
                break;
            case R.id.btnStartSignin_FPF:
                mListener.clickButton(Arguments.Activity.START_SIGNIN_ACTION, mEmail.get());
                break;
        }

    }

    private void initSubscriber() {
        mSubscriber = new Subscriber<R>() {
            @Override
            public void onCompleted() {
                mIsProgressVisible.set(false);
            }

            @Override
            public void onError(Throwable e) {
                mIsProgressVisible.set(false);
                showErrorDialog(mContext.getString(R.string.internet_error));
                mIsSendButtonClickable.set(true);
            }

            @Override
            public void onNext(R r) {
                onRequestSuccess();
            }
        };
    }

    private void onRequestSuccess() {
        //TODO replace methods after response
    }

    private void sendEmail(String email) {
        if (ValidationUtils.isValidEmail(email)) {
            mListener.clickButton(Arguments.Activity.SEND_EMAIL_ACTION, email);
        } else {
            mIsSendButtonClickable.set(true);
            showErrorDialog(mContext.getString(R.string.email_invalid));
        }
    }

    public void setEmail(String type, String email) {
        mEmail.set(email);
        switch (type) {
            case Arguments.Activity.FORGOT_TYPE:
                break;
            case Arguments.Activity.CHECK_TYPE:
                String body = (mContext.getString(R.string.check_email_description_1) + " " + "%s" + " " +
                        mContext.getString(R.string.check_email_description_2));
                mEmailResult.set(String.format(body, email));
                break;
        }
    }

    private void showErrorDialog(String message) {
        ErrorDialog.show(mContext, message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext    = null;
        mListener   = null;
    }

    public interface ClickButtonListener {
        void clickButton(String type, String email);;
    }

}
