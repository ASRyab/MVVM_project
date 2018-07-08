package asryab.com.mvvmproject.viewmodels.feed;

import android.databinding.ObservableInt;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.abstracts.pager.BasePagerAdapter;
import asryab.com.mvvmproject.viewmodels.ViewModel;
import rx.functions.Action1;

public class LiveClosedFeedsVM extends ViewModel {

    public final ObservableInt              lcPosition = new ObservableInt(0);
    public BasePagerAdapter<BaseFragment> fragmentsPagerAdapter;
    private ViewPager                       pollsPagerView;

    public LiveClosedFeedsVM(final ViewPager liveClosedPager, final FragmentManager fragmentManager) {
        pollsPagerView          = liveClosedPager;
        fragmentsPagerAdapter   = new BasePagerAdapter<BaseFragment>(fragmentManager) {};
    }

    public Action1<Integer> onPageSelected = lcPosition::set;

    public void setLiveClosedPages(final BaseFragment liveFragment, final BaseFragment closeFragment) {
        pollsPagerView          .setCurrentItem(0);
        fragmentsPagerAdapter   .clear();
        fragmentsPagerAdapter   .addFragment(liveFragment);
        fragmentsPagerAdapter   .addFragment(closeFragment);
        fragmentsPagerAdapter   .notifyDataSetChanged();
    }

    public void setCurrentTabFromView(final View view) {
        switch (view.getId()) {
            case R.id.tvLive_LVCC:
                pollsPagerView.setCurrentItem(0, true);
                break;
            case R.id.tvClosed_LVCC:
                pollsPagerView.setCurrentItem(1, true);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pollsPagerView          = null;
        fragmentsPagerAdapter   = null;
    }
}
