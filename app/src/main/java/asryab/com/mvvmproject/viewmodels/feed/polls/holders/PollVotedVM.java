package asryab.com.mvvmproject.viewmodels.feed.polls.holders;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.VerticalFillColorItemDecoration;
import asryab.com.mvvmproject.databinding.LayoutStatisticsDiagramBinding;
import asryab.com.mvvmproject.databinding.LayoutStatisticsMapBinding;
import asryab.com.mvvmproject.databinding.LayoutVotedAnswerItemBinding;
import asryab.com.mvvmproject.models.polls.Level;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.models.polls.PollAnswer;
import asryab.com.mvvmproject.screens.home.HomeFragmentsHelper;
import asryab.com.mvvmproject.screens.home.HomeTabsActivity;
import asryab.com.mvvmproject.screens.thoughts.ThoughtsAdapter;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.ViewModel;
import asryab.com.mvvmproject.viewmodels.feed.graphics.CountryMapVM;
import rx.functions.Action1;

public class PollVotedVM extends ViewModel {

    public final Action1<Integer>           tabsStatisticListener   = this::selectTab;
    public final ObservableInt              tabActionSender         = new ObservableInt(-1);
    public final ObservableBoolean          countryPoll             = new ObservableBoolean(false);
    public final ObservableBoolean          isVoted                 = new ObservableBoolean(true);
    public final ObservableBoolean          thoughtsVisible         = new ObservableBoolean(false);
    public final ObservableBoolean          viewAllThoughtsVisible  = new ObservableBoolean(true);

    public RecyclerView.LayoutManager       thoughtsLayoutManager;
    public VerticalFillColorItemDecoration thoughtsDividerDecoration;
    public ThoughtsAdapter thoughtsAdapter;

    private final int                       answersCount;
    private LayoutVotedAnswerItemBinding[]  bindingsVotedAnswer;
    private final Context                   context;
    private final FrameLayout               containerStatistics;
    private Poll poll;

    public PollVotedVM(final LinearLayout answersContainer, final FrameLayout containerStatistics, int answersCount) {
        this.answersCount               = answersCount;
        this.containerStatistics        = containerStatistics;
        this.context                    = containerStatistics.getContext();

        this.thoughtsLayoutManager      = new LinearLayoutManager(context);
        this.thoughtsDividerDecoration  = new VerticalFillColorItemDecoration(context, R.drawable.divider_thoughts);
        this.thoughtsAdapter            = new ThoughtsAdapter(false);

        prepareAnswers(answersContainer);
    }

    private void prepareAnswers(final LinearLayout answersContainer) {
        bindingsVotedAnswer = new LayoutVotedAnswerItemBinding[answersCount];
        for (int i = 0; i < answersCount; i++) {
            bindingsVotedAnswer[i] = LayoutVotedAnswerItemBinding.inflate(LayoutInflater.from(context));
            bindingsVotedAnswer[i].getRoot().setTag(i);
            bindingsVotedAnswer[i].setOnAnswerClick(v -> HomeTabsActivity.startWith(context, prepareAnswerBundle(poll.answers[(int) v.getTag()]), HomeFragmentsHelper.FragmentCopy.ANSWER_STATISTICS, false));
            answersContainer.addView(bindingsVotedAnswer[i].getRoot());
        }
    }

    private int getMaxPercent(final PollAnswer[] answers) {
        int max = 0;
        for (PollAnswer answer: answers)
            if (max < answer.percents)
                max = answer.percents;
        return max;
    }

    public void updateVotedPoll(final Poll poll) {
        this.poll = poll;
        final int maxPercent = getMaxPercent(poll.answers);
        for (int i = 0; i < answersCount; i++) {
            final PollAnswer pollAnswer = poll.answers[i];
            bindingsVotedAnswer[i].setIsChecked(pollAnswer.voted);
            bindingsVotedAnswer[i].setTitle(pollAnswer.getLabelText(context, answersCount));
            bindingsVotedAnswer[i].setPercent(pollAnswer.percents);
            bindingsVotedAnswer[i].setBlendPercent(maxPercent == 0 ? 0f : (float) pollAnswer.percents / maxPercent);
        }

        isVoted                 .set(poll.voted);
//      viewAllThoughtsVisible  .set(poll.comments != null && poll.comments.size() >= 3); TODO IF NEED HIDING ALL THOUGHTS BUTTON, WHEN COUNT COMMENTS < 3
        thoughtsVisible         .set(poll.comments != null && poll.comments.size() > 0);
        countryPoll             .set(poll.getLevel().equals(Level.NATIONWIDE));
        tabActionSender         .set(poll.statisticViewNowPos); // If need not saving state set 0 and not saving state position

        thoughtsAdapter         .clearAllData();
        if (poll.comments != null && poll.comments.size() > 0)
            thoughtsAdapter     .addData(poll.comments);
        else thoughtsAdapter    .notifyDataSetChanged();
    }

    private void selectTab(final int position) {
        poll.statisticViewNowPos = position;
        tabActionSender         .set(position);
        containerStatistics     .removeAllViews();
        switch (position) {
            case 0:
                // Empty container
                break;
            case 1:
                final LayoutStatisticsMapBinding bindingMap = LayoutStatisticsMapBinding.inflate(LayoutInflater.from(context), null, false);
                bindingMap.setViewModel(new CountryMapVM(context, bindingMap.mapCountry));
                containerStatistics.addView(bindingMap.getRoot());
                break;
            case 2:
                final LayoutStatisticsDiagramBinding bindingDiagram = LayoutStatisticsDiagramBinding.inflate(LayoutInflater.from(context), null, false);
                bindingDiagram.setPollAnswers(poll.answers);
                containerStatistics.addView(bindingDiagram.getRoot());
                break;
        }
    }

    public void viewAllThoughts(final View buttonView) {
        HomeTabsActivity.startWith(context, prepareAllThoughtsBundle(), HomeFragmentsHelper.FragmentCopy.ALL_THOUGHTS, false);
    }

    private Bundle prepareAnswerBundle(final PollAnswer pollAnswer) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Arguments.Fragment.ARG_POLL_ANSWER, pollAnswer);
        return bundle;
    }

    private Bundle prepareAllThoughtsBundle() {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Arguments.Fragment.ARG_POLL_ID, poll.id);
        return bundle;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (LayoutVotedAnswerItemBinding b: bindingsVotedAnswer)
            b.unbind();
    }
}
