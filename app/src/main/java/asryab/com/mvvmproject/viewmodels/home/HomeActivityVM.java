package asryab.com.mvvmproject.viewmodels.home;

import android.content.Context;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.screens.home.HomeFragmentsHelper;
import asryab.com.mvvmproject.screens.home.HomeTabsActivity;
import asryab.com.mvvmproject.viewmodels.FragmentSupportVM;
import rx.functions.Action1;

public final class HomeActivityVM extends FragmentSupportVM {

    private Context                         context;
    private int                             currentShowedPosition   = -1;

    public final ObservableInt tabActionSender              = new ObservableInt(-1);
    public final Action1<Integer> tabSelectListener         = this::selectTab;

    public HomeActivityVM(final Context context, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    public void readAndPrepareFragment(final Bundle bundleContentFragment, final HomeFragmentsHelper.FragmentCopy fragmentCopy) {
        if (fragmentCopy != null) {
            currentShowedPosition = HomeFragmentsHelper.getPositionTabIfIsHomeFragment(fragmentCopy);
            if (currentShowedPosition < 0)
                replaceFragment(HomeFragmentsHelper.instantiate(bundleContentFragment, fragmentCopy));
            else tabActionSender.set(currentShowedPosition);
        } else tabActionSender.set(currentShowedPosition = 0); //Default tab is first
    }

    private void selectTab(int position) {
        if (currentShowedPosition < 0)
            HomeTabsActivity.startWith(context, null, HomeFragmentsHelper.getFragmentCopyIfIsValidPosition(position), true);
        else {
            currentShowedPosition = position;
            replaceFragment(HomeFragmentsHelper.instantiate(null, HomeFragmentsHelper.getFragmentCopyIfIsValidPosition(currentShowedPosition)));
        }
    }

    private void replaceFragment(final BaseFragment fragmentToShow) {
        if (fragmentToShow != null) replaceFragment(R.id.flContainer_AH, fragmentToShow);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context                 = null;
        currentShowedPosition   = -1;
    }
}
