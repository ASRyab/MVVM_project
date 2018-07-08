package asryab.com.mvvmproject.screens.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentIntroPageBinding;
import asryab.com.mvvmproject.models.intro.IntroPageModel;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.intro.IntroFragmentVM;

public final class IntroFragment extends BaseFragment {

    private FragmentIntroPageBinding    mBindingFragmentIntroPage;
    private IntroFragmentVM mViewModelIntroFragment;
    private IntroPageModel mModelIntroPage;

    @Override
    protected void readFromBundle(Bundle bundle) {
        mModelIntroPage = (IntroPageModel) bundle.getSerializable(Arguments.Fragment.ARG_PAGE_INTRO_MODEL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injectDataBinding(mBindingFragmentIntroPage = FragmentIntroPageBinding.inflate(inflater, container, false));
        injectViewModel(mViewModelIntroFragment = new IntroFragmentVM(getContext(), mModelIntroPage));
        mBindingFragmentIntroPage.setViewModel(mViewModelIntroFragment);

        return mBindingFragmentIntroPage.getRoot();
    }

    public void setScrollOffset(final float pageOffset) {
        if (mViewModelIntroFragment != null)
            mViewModelIntroFragment.setOffsetTitles(pageOffset);
    }

}