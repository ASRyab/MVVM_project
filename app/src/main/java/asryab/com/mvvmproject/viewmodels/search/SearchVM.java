package asryab.com.mvvmproject.viewmodels.search;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.abstracts.pager.BasePagerAdapter;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.poll.GetPollsByStatus;
import asryab.com.mvvmproject.databinding.FragmentSearchBinding;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.models.ListState;
import asryab.com.mvvmproject.models.MoreLoadingInfo;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.models.polls.TrendingPoll;
import asryab.com.mvvmproject.screens.search.PollPageAdsFragment;
import asryab.com.mvvmproject.screens.search.trending.FlingCardTouchEvaluator;
import asryab.com.mvvmproject.screens.search.trending.IManualChooseAnswerListener;
import asryab.com.mvvmproject.screens.search.trending.TrendingSwipingContainer;
import asryab.com.mvvmproject.screens.small_lists.SmallListFragment;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.ErrorLoadingVM;


public class SearchVM extends ErrorLoadingVM {

    //*********** FOR POLLS VIEWPAGER *************//

    public static final int                             DELAY_AUTO_SWIPE = 4000;

    public final ObservableBoolean                      isLoadingPollsAds           = new ObservableBoolean(false);
    public final ObservableBoolean                      isLoadingTrendingCards      = new ObservableBoolean(false);
    public final ObservableBoolean                      isVisibleTrendingContainer  = new ObservableBoolean(true);

    private final Context                               context;
    private final ViewPager                             viewPager;
    private final BasePagerAdapter<PollPageAdsFragment> pollPagerAdapter;
    private final BasePagerAdapter<BaseFragment> pastPagerAdapter;

    private final TrendingSwipingContainer swipeTrendingCardContainer;

    private Handler                                     handlerTicker = new Handler();
    private Runnable                                    runnableTicker = this::moveToNext;

    //*********************************************//


    public SearchVM(final Context context, final FragmentSearchBinding binding, final FragmentManager fragmentManager) {
        this.context                    = context;
        this.viewPager                  = binding.vpPollsNewsFES;
        this.swipeTrendingCardContainer = binding.swipeTrendingCardContainer;

        this.pollPagerAdapter           = new BasePagerAdapter<PollPageAdsFragment>(fragmentManager) {};
        this.pastPagerAdapter           = new BasePagerAdapter<>(fragmentManager);
        preparePastPages();

        this.viewPager                  .setAdapter(pollPagerAdapter);
        this.viewPager                  .addOnPageChangeListener(pagePollsChangeListener);

        binding                         .cpiCircleIndicatorsFS  .setViewPager(viewPager);
        binding                         .vpExplorePagerFS       .setAdapter(pastPagerAdapter);
        binding                         .tlTabsFAS              .setupWithViewPager(binding.vpExplorePagerFS);

        mockLoadPolls();
        mockLoadTrendingCards();
    }

    private void mockLoadPolls() {
        hideError();
        isLoadingPollsAds.set(true);
        mCompositeSubscription.add(
                new GetPollsByStatus(FeedStatus.OPENED, new MoreLoadingInfo())
                    .getTask(context)
                    .subscribe(pollGetListResponse -> {
                        isLoadingPollsAds.set(false);
                        preparePollPages(pollGetListResponse.data);

                        if (pollPagerAdapter.getCount() == 0)
                            showError(context.getString(R.string.error_empty_data), false);

                    }, throwable -> {
                        isLoadingPollsAds.set(false);
                        mCompositeSubscription.add(ApiTask.errorResponseObservable(context, throwable,
                                () -> showError(context.getString(R.string.error_internet_connection), true),null));
                        //todo need check
//                        if (pollPagerAdapter.getCount() == 0)
//                            showError(context.getString(R.string.error_internet_connection), true);

                    }));
    }

    private void mockLoadTrendingCards() {
        isLoadingTrendingCards.set(true);
        mCompositeSubscription.add(
                new GetPollsByStatus(FeedStatus.CLOSED, new MoreLoadingInfo(MoreLoadingInfo.DEFAULT_PAGE_COUNT_OTHER))
                        .getTask(context)
                        .subscribe(pollGetListResponse -> {
                            isLoadingTrendingCards.set(false);
                            mockTrendingCards(pollGetListResponse.data);
                        }, throwable -> {
                            isLoadingTrendingCards.set(false);
                            isVisibleTrendingContainer.set(false);
                        }));
    }

    private void moveToNext() {
        if (viewPager != null && pollPagerAdapter.getCount() > 0)
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1 == pollPagerAdapter.getCount() ? 0 : viewPager.getCurrentItem() + 1, true);
    }
    private void moveToPrev() {
        if (viewPager != null && pollPagerAdapter.getCount() > 0)
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1 == -1 ? pollPagerAdapter.getCount() - 1 : viewPager.getCurrentItem() - 1, true);
    }

    private void preparePollPages(final ArrayList<Poll> polls) {
        for(Poll poll: polls)
            pollPagerAdapter.addFragment(new PollPageAdsFragment().withArgs(getPollBundle(poll)));

        pollPagerAdapter.notifyDataSetChanged();
        if (pollPagerAdapter.getCount() > 0)
            handlerTicker.postDelayed(runnableTicker, DELAY_AUTO_SWIPE);
    }

    private Bundle getPollBundle(final Poll pollModel) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Arguments.Fragment.ARG_PAGE_POLL_MODEL, pollModel);
        return bundle;
    }

    private final ViewPager.OnPageChangeListener pagePollsChangeListener = new ViewPager.OnPageChangeListener() {

        private float thresholdOffset = 0f;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset != thresholdOffset || positionOffset == 0) {
                final float betweenOffset = positionOffset == 0 ? 0 : positionOffset - thresholdOffset;
                thresholdOffset = positionOffset;
                if (betweenOffset > 0) {
                    pollPagerAdapter.getItem(position).setScrollOffset(-positionOffset);
                    if (position + 1 <= pollPagerAdapter.getCount() - 1)
                        pollPagerAdapter.getItem(position + 1).setScrollOffset(1 - positionOffset);
                } else if (betweenOffset < 0) {
                    pollPagerAdapter.getItem(position + 1).setScrollOffset(1 - positionOffset);
                    pollPagerAdapter.getItem(position).setScrollOffset(-positionOffset);
                } else pollPagerAdapter.getItem(position).setScrollOffset(0);
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

    public void clickOnMoveNext(final View view) {
        moveToNext();
    }

    public void clickOnMovePrev(final View view) {
        moveToPrev();
    }


    //////////////////////////////////// TRENDING CARDS's
    private void mockTrendingCards(final ArrayList<Poll> closedPolls) {
        final ArrayList<TrendingPoll> trendingPolls = new ArrayList<>();
        for (int i = 0; i < closedPolls.size(); i++)
            trendingPolls.add(getTestTrendingPoll(i, closedPolls.get(i).title));

        swipeTrendingCardContainer.initializeViews(context.getResources().getDimension(R.dimen.app_container_padding_half), manualChooseAnswerListener, flingListener);
        swipeTrendingCardContainer.updateData(trendingPolls);
    }

    private TrendingPoll getTestTrendingPoll(final int pos, final String msg) {
        final TrendingPoll trendingPoll = new TrendingPoll();
        trendingPoll.id             = pos;
        trendingPoll.answerNoId     = 0;
        trendingPoll.answerYesId    = 1;
        trendingPoll.message        = msg + " [ " + pos + " ]";
        return trendingPoll;
    }

    private final FlingCardTouchEvaluator.FlingListener<TrendingPoll> flingListener = new FlingCardTouchEvaluator.FlingListener<TrendingPoll>() {
        @Override
        public void onCardExited() {

        }

        @Override
        public void leftExit(TrendingPoll dataObject) {
            voteTrendingCard(dataObject, false);
        }

        @Override
        public void rightExit(TrendingPoll dataObject) {
            voteTrendingCard(dataObject, true);
        }

        @Override
        public void onClick(TrendingPoll dataObject) {

        }

        @Override
        public void onEndQueue() {
            isVisibleTrendingContainer.set(false);
        }

        @Override
        public void onScroll(float scrollProgressPercent) {

        }
    };

    private final IManualChooseAnswerListener manualChooseAnswerListener = new IManualChooseAnswerListener() {
        @Override
        public void yes(TrendingPoll trendingPoll) {
            swipeTrendingCardContainer.select(false);
        }

        @Override
        public void no(TrendingPoll trendingPoll) {
            swipeTrendingCardContainer.select(true);
        }
    };

    private void voteTrendingCard(final TrendingPoll trendingPoll, final boolean yes) {
//        if (yes)
//            Toast.makeText(context, "Voting YES - " + trendingPoll.id, Toast.LENGTH_SHORT).show();
//        else Toast.makeText(context, "Voting NO - " + trendingPoll.id, Toast.LENGTH_SHORT).show();
    }

    ////////////////////////////////////////////////////////////////////////////

    private void preparePastPages() {
        pastPagerAdapter.addFragment(new SmallListFragment()
                .withArgs(prepareBundle(ListState.POLLS, FeedStatus.CLOSED))
                ,context.getString(R.string.past_polls));
        pastPagerAdapter.addFragment(new SmallListFragment()
                .withArgs(prepareBundle(ListState.PETITIONS, FeedStatus.CLOSED))
                ,context.getString(R.string.past_petitions));
    }

    @NonNull
    private Bundle prepareBundle(ListState listState, FeedStatus feedStatus) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Arguments.Fragment.ARG_LIST_STATE, listState);
        bundle.putSerializable(Arguments.Fragment.ARG_POLLS_STATUS, feedStatus);
        return bundle;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerTicker   .removeCallbacks(runnableTicker);
        runnableTicker  = null;
        handlerTicker   = null;
    }

    @Override
    public void retry(View retryBtn) {
        mockLoadPolls();
    }
}
