package asryab.com.mvvmproject.abstracts.pager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import asryab.com.mvvmproject.abstracts.BaseFragment;

public class BasePagerAdapter<T extends BaseFragment> extends FragmentStatePagerAdapter {

    private final ArrayList<T> baseFragments        = new ArrayList<>();
    private final ArrayList<CharSequence> titles    = new ArrayList<>();

    public BasePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(T fragment) {
        addFragment(fragment, "");
    }

    public void addFragment(T fragment, CharSequence title) {
        baseFragments   .add(fragment);
        titles          .add(title);
    }

    public void clear() {
        baseFragments.clear();
        titles.clear();
    }

    @Override
    public T getItem(int position) {
        return baseFragments.get(position);
    }

    @Override
    public int getCount() {
        return baseFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}
