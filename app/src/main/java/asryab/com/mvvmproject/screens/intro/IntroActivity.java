package asryab.com.mvvmproject.screens.intro;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseActivity;
import asryab.com.mvvmproject.databinding.ActivityIntroBinding;
import asryab.com.mvvmproject.viewmodels.intro.IntroActivityVM;

public final class IntroActivity extends BaseActivity {

    private ActivityIntroBinding    mBindingIntroActivity;
    private IntroActivityVM mViewModelIntroActivity;

    public static void startIt(final Context context) {
        final Intent intent = new Intent(context, IntroActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDataBinding(mBindingIntroActivity = DataBindingUtil.setContentView(this, R.layout.activity_intro));
        injectViewModel(mViewModelIntroActivity = new IntroActivityVM(this, mBindingIntroActivity, getSupportFragmentManager()));

        mBindingIntroActivity.setViewModel(mViewModelIntroActivity);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mViewModelIntroActivity.permissionResult(requestCode, permissions, grantResults);
    }

}
