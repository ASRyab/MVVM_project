package asryab.com.mvvmproject.viewmodels.authorization.forgotpass;

import android.view.View;

import asryab.com.mvvmproject.viewmodels.ToolbarVM;

public class ActivityForgotPasswordVM extends ToolbarVM {

    private OnBackPressedListener mBackPressedListener;

    public ActivityForgotPasswordVM(OnBackPressedListener listener, String title) {
        mBackPressedListener = listener;
        mTitle.set(title);
    }

    @Override
    public void onBackArrowPressed(View view) {
        mBackPressedListener.onBackButtonPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
