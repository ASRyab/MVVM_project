package asryab.com.mvvmproject.viewmodels.profile;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.profile.GetProfile;
import asryab.com.mvvmproject.databinding.FragmentProfileBinding;
import asryab.com.mvvmproject.models.Profile;
import asryab.com.mvvmproject.models.user.User;
import asryab.com.mvvmproject.utils.image_transform.CircleTransform;
import asryab.com.mvvmproject.viewmodels.ErrorLoadingVM;

public  class ProfileVM extends ErrorLoadingVM {


    public final ObservableField<String>    mTitle      = new ObservableField<>();
    public final ObservableField<String>    avatar          = new ObservableField<>();
    public final ObservableField<String>    address          = new ObservableField<>();
    public final ObservableField<String>    webAddress          = new ObservableField<>();
    public final ObservableField<String>    name          = new ObservableField<>();
    public final ObservableField<String>    about          = new ObservableField<>();
    public final ObservableField<String>    rank          = new ObservableField<>();
    public final ObservableInt    numberFollowing          = new ObservableInt(0);
    public final ObservableInt    numberFollow         = new ObservableInt(0);
    public final ObservableBoolean isFollow = new ObservableBoolean(true);
    public final ObservableBoolean isLoadingNow = new ObservableBoolean(false);

    private final FragmentActivity mFragmentActivity;
    private final FragmentProfileBinding fragmentProfileBinding;
    private ProfileListsVM profileListsVM;
    public final CircleTransform circleTransform;
    private User currentUser;

    public ProfileVM(FragmentActivity fragmentActivity, FragmentProfileBinding fragmentProfileBinding) {
        this.circleTransform    = new CircleTransform(fragmentActivity);
        this.mFragmentActivity = fragmentActivity;
        this.fragmentProfileBinding = fragmentProfileBinding;
        mTitle.set(mFragmentActivity.getString(R.string.my_profile));
        loadData();
    }

    protected void loadData() {
        hideError();
        isLoadingNow.set(true);
        mCompositeSubscription.add(
                new GetProfile().getTask(mFragmentActivity)
                .subscribe(this::updateUser,throwable -> {
                    mCompositeSubscription.add(
                            ApiTask.errorResponseObservable(
                                    mFragmentActivity,throwable,() -> showError(mFragmentActivity.getString(R.string.internet_error),true)
                                    ,errorResponse -> showError(errorResponse.getError(),true)));
                    isLoadingNow.set(false);
                })
        );
    }

    @Override
    public void retry(View retryBtn) {
        loadData();
    }

    private void updateUser(User user) {
        isLoadingNow.set(false);
        this.currentUser = user;

        Profile profile = user.profile;
        avatar.set(profile.avatarUrl);
        address.set(profile.country);
        webAddress.set(profile.link);
        name.set(profile.username);
        about.set(profile.bio);
        numberFollow.set(profile.followers_count);
        numberFollowing.set(profile.followings_count);
        loadComleted();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void loadComleted() {
        profileListsVM = new ProfileListsVM(mFragmentActivity,fragmentProfileBinding, currentUser);
        fragmentProfileBinding.setListsVM(profileListsVM);
        fragmentProfileBinding.invalidateAll();
    }

    public  void onClickLeaderboard(View view) {
        Toast.makeText(mFragmentActivity,"onClickLeaderboard",Toast.LENGTH_SHORT).show();
    }
    public  void onClickSetting(View view) {
        Toast.makeText(mFragmentActivity,"onClickSetting",Toast.LENGTH_SHORT).show();
    }
    public void onClickShare(View view) {
        Toast.makeText(mFragmentActivity,"onClickShare",Toast.LENGTH_SHORT).show();
    }

    public void onClickFollow(View view){
        Toast.makeText(mFragmentActivity,"onClickFollow",Toast.LENGTH_SHORT).show();
    }

    public void onClickFollowing(View view){
        Toast.makeText(mFragmentActivity,"onClickFollowing",Toast.LENGTH_SHORT).show();
    }
    public void onClickFollowers(View view){
        Toast.makeText(mFragmentActivity,"onClickFollow",Toast.LENGTH_SHORT).show();
    }
}
