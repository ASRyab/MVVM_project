package asryab.com.mvvmproject.viewmodels;

import android.support.v4.app.FragmentManager;

import org.jetbrains.annotations.NotNull;

import asryab.com.mvvmproject.abstracts.BaseFragment;

public class FragmentSupportVM extends ViewModel {

    private FragmentManager fragmentManager;

    public FragmentSupportVM(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    protected void replaceFragment(int idLayout, @NotNull final BaseFragment baseFragment) {
        this.fragmentManager.beginTransaction()
                .replace(idLayout, baseFragment)
                .commit();
    }

    protected void addFragment(int idLayout, @NotNull final BaseFragment baseFragment) {
        this.fragmentManager.beginTransaction()
                .replace(idLayout, baseFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentManager = null;
    }

}
