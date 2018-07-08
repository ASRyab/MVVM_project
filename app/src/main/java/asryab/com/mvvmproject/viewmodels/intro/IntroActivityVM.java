package asryab.com.mvvmproject.viewmodels.intro;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import java.io.File;
import java.io.IOException;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.pager.BasePagerAdapter;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.auth.GetIntros;
import asryab.com.mvvmproject.databinding.ActivityIntroBinding;
import asryab.com.mvvmproject.models.intro.IntroPageModel;
import asryab.com.mvvmproject.screens.authorization.login.LoginActivity;
import asryab.com.mvvmproject.screens.authorization.signup.SignUpActivity;
import asryab.com.mvvmproject.screens.intro.IntroFragment;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.utils.DateTimeUtils;
import asryab.com.mvvmproject.utils.Permissions;
import asryab.com.mvvmproject.utils.RequestCodes;
import asryab.com.mvvmproject.utils.ResourcesHelper;
import asryab.com.mvvmproject.viewmodels.RuntimePermissionVM;
import rx.Observable;
import rx.schedulers.Schedulers;

public class IntroActivityVM extends RuntimePermissionVM {

    public static final int                     DELAY_AUTO_SWIPE = 4000;

    private Context                             context;
    private BasePagerAdapter<IntroFragment> introPagerAdapter;
    private ViewPager                           viewPager;

    private Handler                             handlerTicker = new Handler();
    private Runnable                            runnableTicker = this::moveToNext;

    public IntroActivityVM(final Activity contextActivity, final ActivityIntroBinding introBinding, final FragmentManager fragmentManager) {
        this.context            = contextActivity;
        this.introPagerAdapter  = new BasePagerAdapter<IntroFragment>(fragmentManager) {};

        prepareIntros();

        viewPager               = introBinding.vpIntrosAI;
        viewPager               .setAdapter(introPagerAdapter);
        viewPager               .addOnPageChangeListener(pageIntroChangeListener);
        handlerTicker           .postDelayed(runnableTicker, DELAY_AUTO_SWIPE);

        introBinding            .cpiCircleIndicatorsAI.setViewPager(viewPager);

        loadIntrosIfCan(contextActivity);
    }

    private void moveToNext() {
        if (viewPager != null)
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1 == introPagerAdapter.getCount() ? 0 : viewPager.getCurrentItem() + 1, true);
    }

    private void prepareIntros() {
        introPagerAdapter.addFragment(new IntroFragment().withArgs(getIntroBundle(new IntroPageModel(
                context.getString(R.string.app_intro_1_title),
                context.getString(R.string.app_intro_1_subtitle),
                getIntroImagePath(1)))));

        introPagerAdapter.addFragment(new IntroFragment().withArgs(getIntroBundle(new IntroPageModel(
                context.getString(R.string.app_intro_2_title),
                context.getString(R.string.app_intro_2_subtitle),
                getIntroImagePath(2)))));

        introPagerAdapter.addFragment(new IntroFragment().withArgs(getIntroBundle(new IntroPageModel(
                context.getString(R.string.app_intro_3_title),
                context.getString(R.string.app_intro_3_subtitle),
                getIntroImagePath(3)))));
    }

    private String getIntroImagePath(final int numberOf) {
        final String pathFromCache = ResourcesHelper.getIntroFileDrawablePath(context, numberOf);
        return TextUtils.isEmpty(pathFromCache) ? ResourcesHelper.getIntroAssetsDrawablePath(numberOf) : pathFromCache;
    }

    private ViewPager.OnPageChangeListener pageIntroChangeListener = new ViewPager.OnPageChangeListener() {

        private float thresholdOffset = 0f;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset != thresholdOffset || positionOffset == 0) {
                final float betweenOffset = positionOffset == 0 ? 0 : positionOffset - thresholdOffset;
                thresholdOffset = positionOffset;
                if (betweenOffset > 0) {
                    introPagerAdapter.getItem(position).setScrollOffset(-positionOffset);
                    if (position + 1 <= introPagerAdapter.getCount() - 1)
                        introPagerAdapter.getItem(position + 1).setScrollOffset(1 - positionOffset);
                } else if (betweenOffset < 0) {
                    introPagerAdapter.getItem(position + 1).setScrollOffset(1 - positionOffset);
                    introPagerAdapter.getItem(position).setScrollOffset(-positionOffset);
                } else introPagerAdapter.getItem(position).setScrollOffset(0);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE)
                handlerTicker.postDelayed(runnableTicker, DELAY_AUTO_SWIPE);
            else handlerTicker.removeCallbacks(runnableTicker);
        }
    };

    private Bundle getIntroBundle(final IntroPageModel introPageModel) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Arguments.Fragment.ARG_PAGE_INTRO_MODEL, introPageModel);
        return bundle;
    }

    public void signUp(final View viewClicked) {
        SignUpActivity.startIt(context);
    }

    public void logIn(final View viewClicked) {
        LoginActivity.startIt(context);
    }

    private void loadIntrosIfCan(final Activity activity) {
        if (isGrantedPermission(activity, Permissions.STORAGE_PERMISSION))
            refreshImages();
        else requestPermission(activity, Permissions.STORAGE_PERMISSION, RequestCodes.RC_PERMISSION_STORAGE);
    }
    private void refreshImages() {
        mCompositeSubscription.add(new GetIntros()
                .getTask(context)
                .observeOn(Schedulers.io())
                .flatMap(Observable::from)
                .subscribe(this::saveImageToFileByPageModel
                        , throwable -> mCompositeSubscription.add(ApiTask.errorResponseObservable(context, throwable,true))));
    }

    private void saveImageToFileByPageModel(final IntroPageModel ipm) {
        final File destinationFileImage = new File(ResourcesHelper.getIntroFileDirectory(context), ResourcesHelper.getIntroFileName(ipm.id));
        if (!destinationFileImage.exists() || (DateTimeUtils.parseDateStringToLong(ipm.updated_at) > destinationFileImage.lastModified()))
            try {
                if (!ResourcesHelper.saveUrlImageIntoFile(ipm.url, destinationFileImage))
                    destinationFileImage.delete();
            } catch (IOException e) {
                e.printStackTrace();
                destinationFileImage.delete();
            }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerTicker           .removeCallbacks(runnableTicker);
        runnableTicker          = null;
        handlerTicker           = null;
        introPagerAdapter       = null;
        viewPager               = null;
        context                 = null;
        pageIntroChangeListener = null;
    }

    @Override
    public void permissionResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case RequestCodes.RC_PERMISSION_STORAGE:
                if (isResultGranted(grantResults))
                    refreshImages();
                break;
        }
    }
}
