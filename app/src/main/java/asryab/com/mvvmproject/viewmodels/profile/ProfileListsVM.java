package asryab.com.mvvmproject.viewmodels.profile;

import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.abstracts.pager.BasePagerAdapter;
import asryab.com.mvvmproject.databinding.FragmentProfileBinding;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.models.ListState;
import asryab.com.mvvmproject.models.Profile;
import asryab.com.mvvmproject.models.user.User;
import asryab.com.mvvmproject.screens.small_lists.SmallListFragment;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public class ProfileListsVM extends ViewModel {

    private User currentUser;
    private FragmentProfileBinding fragmentProfileBinding;
    private FragmentActivity fragmentActivity;
    private BasePagerAdapter<BaseFragment> adapter;
    private ViewPager viewPager;

    public ObservableInt polls = new ObservableInt(58);
    public ObservableInt petitions = new ObservableInt(23);
    public ObservableInt topics = new ObservableInt(0);
    public ObservableInt points = new ObservableInt(0);
    public TabLayout mTabLayout;

    public ProfileListsVM(FragmentActivity fragmentActivity, FragmentProfileBinding fragmentProfileBinding, User currentUser) {
        setUser(currentUser);
        this.fragmentProfileBinding = fragmentProfileBinding;
        this.fragmentActivity = fragmentActivity;
        viewPager = fragmentProfileBinding.vpProfilePL;
        mTabLayout = fragmentProfileBinding.tlTabsPL;
        initAdapter();
        initViewPager();
    }

    private void setUser(User currentUser) {
        this.currentUser = currentUser;
        Profile profile = currentUser.profile;
        polls.set(profile.votes_count);
        petitions.set(profile.petition_subscriptions_count);
        topics.set(profile.posted_topics_count);
        points.set(profile.points);
    }

    private void initViewPager() {
        mTabLayout.setupWithViewPager(viewPager);
    }

    private void initAdapter() {
        adapter = new BasePagerAdapter<>(fragmentActivity.getSupportFragmentManager());
        adapter.addFragment(new SmallListFragment().withArgs(prepareBundle(ListState.POLLS, FeedStatus.CLOSED)),
                String.format("%s\n%s",polls.get(),fragmentActivity.getString(R.string.polls)));
        adapter.addFragment(new SmallListFragment().withArgs(prepareBundle(ListState.PETITIONS, FeedStatus.CLOSED)),
                String.format("%s\n%s",petitions.get(),fragmentActivity.getString(R.string.petitions)));
        adapter.addFragment(new SmallListFragment().withArgs(prepareBundle(ListState.POLLS, FeedStatus.CLOSED)),
                String.format("%s\n%s",topics.get(),fragmentActivity.getString(R.string.polls)));
        adapter.addFragment(new SmallListFragment().withArgs(prepareBundle(ListState.PETITIONS, FeedStatus.CLOSED)),
                String.format("%s\n%s",points.get(),fragmentActivity.getString(R.string.petitions)));
        viewPager.setAdapter(adapter);
    }

    @NonNull
    private Bundle prepareBundle(ListState listState, FeedStatus feedStatus) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Arguments.Fragment.ARG_LIST_STATE, listState);
        bundle.putSerializable(Arguments.Fragment.ARG_POLLS_STATUS, feedStatus);
        return bundle;
    }
}
