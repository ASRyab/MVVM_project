package asryab.com.mvvmproject.screens.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseActivity;
import asryab.com.mvvmproject.databinding.ActivityHomeBinding;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.home.HomeActivityVM;

public final class HomeTabsActivity extends BaseActivity {

    private Bundle                              bundleForChildFragment;
    private HomeFragmentsHelper.FragmentCopy    fragmentCopy;

    public static void startWith(final Context contextWith, final Bundle bundleChildFragment, final HomeFragmentsHelper.FragmentCopy fragmentCopy, final boolean clearStack) {
        final Bundle sendBundle = new Bundle();
        sendBundle.putBundle(Arguments.Activity.BUNDLE_FOR_HOME_CHILD, bundleChildFragment);
        sendBundle.putSerializable(Arguments.Activity.FRAGMENT_COPY_HOME_CHILD, fragmentCopy);

        final Intent intent = new Intent(contextWith, HomeTabsActivity.class);
        intent.putExtras(sendBundle);
        if (clearStack) intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        contextWith.startActivity(intent);
    }

    private void readExtras() {
        if (getIntent().getExtras() == null) {
            return;
        }
        bundleForChildFragment  = getIntent().getExtras().getBundle(Arguments.Activity.BUNDLE_FOR_HOME_CHILD);

        if(getIntent().getExtras().containsKey(Arguments.Activity.FRAGMENT_COPY_HOME_CHILD))
            fragmentCopy = (HomeFragmentsHelper.FragmentCopy) getIntent().getExtras().getSerializable(Arguments.Activity.FRAGMENT_COPY_HOME_CHILD);
    }

    private ActivityHomeBinding     mBindingHomeActivity;
    private HomeActivityVM mViewModelHomeActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readExtras();
        injectDataBinding(mBindingHomeActivity = DataBindingUtil.setContentView(this, R.layout.activity_home));
        injectViewModel(mViewModelHomeActivity = new HomeActivityVM(this, getSupportFragmentManager()));
        mBindingHomeActivity    .setViewModel(mViewModelHomeActivity);
        mViewModelHomeActivity  .readAndPrepareFragment(bundleForChildFragment, fragmentCopy);
    }
}
