package asryab.com.mvvmproject.screens.language;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseActivity;
import asryab.com.mvvmproject.data.DataStorage;
import asryab.com.mvvmproject.databinding.ActivityLanguageBinding;
import asryab.com.mvvmproject.screens.home.HomeTabsActivity;
import asryab.com.mvvmproject.screens.intro.IntroActivity;
import asryab.com.mvvmproject.viewmodels.language.LanguageActivityVM;

public final class LanguageActivity extends BaseActivity {

    private ActivityLanguageBinding mBindingLanguageActivity;
    private LanguageActivityVM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (DataStorage.isLanguageSelected()) {
            checkAuth();
        } else {
            injectDataBinding(mBindingLanguageActivity = DataBindingUtil.setContentView(this, R.layout.activity_language));
            injectViewModel(mViewModel = new LanguageActivityVM(this));
            mBindingLanguageActivity.setViewModel(mViewModel);
        }
    }

    private void checkAuth() {
        if (DataStorage.isLogin()) {
            HomeTabsActivity.startWith(this, null, null, true);
        } else IntroActivity.startIt(this);
    }
}
