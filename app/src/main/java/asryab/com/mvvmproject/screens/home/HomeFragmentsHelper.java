package asryab.com.mvvmproject.screens.home;

import android.os.Bundle;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.screens.activity.ActivityFragment;
import asryab.com.mvvmproject.screens.createpoll.CreatePollFragment;
import asryab.com.mvvmproject.screens.feed.FeedActionsFragment;
import asryab.com.mvvmproject.screens.feed.graphics.AnswerStatisticsFragment;
import asryab.com.mvvmproject.screens.feed.petitions.AllUpdatesPetitionFragment;
import asryab.com.mvvmproject.screens.feed.petitions.ReadMorePetitionFragment;
import asryab.com.mvvmproject.screens.feed.polls.PollDetailFragment;
import asryab.com.mvvmproject.screens.profile.ProfileFragment;
import asryab.com.mvvmproject.screens.search.SearchFragment;
import asryab.com.mvvmproject.screens.thoughts.AllThoughtsFragment;

public abstract class HomeFragmentsHelper {

    public enum FragmentCopy {
        HOME_POLLS,
        HOME_SEARCH,
        HOME_CREATE,
        HOME_ACTIVITY,
        HOME_PROFILE,

        ANSWER_STATISTICS,
        PETITION_READ_MORE, PETITION_ALL_UPDATES,
        ALL_THOUGHTS,
        POLL_DETAIL
    }

    public static int getPositionTabIfIsHomeFragment(final FragmentCopy fragmentCopy) {
        switch (fragmentCopy) {
            case HOME_POLLS:
                return 0;
            case HOME_SEARCH:
                return 1;
            case HOME_CREATE:
                return 2;
            case HOME_ACTIVITY:
                return 3;
            case HOME_PROFILE:
                return 4;
        }
        return -1;
    }

    public static FragmentCopy getFragmentCopyIfIsValidPosition(final int position) {
        switch (position) {
            case 0:
                return FragmentCopy.HOME_POLLS;
            case 1:
                return FragmentCopy.HOME_SEARCH;
            case 2:
                return FragmentCopy.HOME_CREATE;
            case 3:
                return FragmentCopy.HOME_ACTIVITY;
            case 4:
                return FragmentCopy.HOME_PROFILE;
        }
        return null;
    }

    public static BaseFragment instantiate(final Bundle bundle, FragmentCopy copyFragmentType) {
        BaseFragment baseFragment = null;
        switch (copyFragmentType) {
            case HOME_POLLS:
                baseFragment = new FeedActionsFragment();
                break;
            case HOME_SEARCH:
                baseFragment = new SearchFragment();
                break;
            case HOME_CREATE:
                baseFragment = new CreatePollFragment();
                break;
            case HOME_ACTIVITY:
                baseFragment = new ActivityFragment();
                break;
            case HOME_PROFILE:
                baseFragment = new ProfileFragment();
                break;
            case ANSWER_STATISTICS:
                baseFragment = new AnswerStatisticsFragment();
                break;
            case ALL_THOUGHTS:
                baseFragment = new AllThoughtsFragment();
                break;
            case PETITION_READ_MORE:
                baseFragment = new ReadMorePetitionFragment();
                break;
            case PETITION_ALL_UPDATES:
                baseFragment = new AllUpdatesPetitionFragment();
                break;
            case POLL_DETAIL:
                baseFragment = new PollDetailFragment();
                break;
        }
        if (baseFragment != null) baseFragment.setArguments(bundle);
        return baseFragment;
    }
}
