package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.view.View;

import asryab.com.mvvmproject.databinding.HolderAnswersVotedBinding;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.viewmodels.feed.polls.holders.PollVotedVM;

public class VotedAnswersHolder extends BasePollHolder {

    private HolderAnswersVotedBinding   mBindingAnswersVoted;
    private PollVotedVM mViewModelVotedAnswers;

    public VotedAnswersHolder(View contentView, int answersCount) {
        super(contentView);
        this.mBindingAnswersVoted   = HolderAnswersVotedBinding.bind(contentView);
        this.mViewModelVotedAnswers = new PollVotedVM(mBindingAnswersVoted.llContainerAnswersHAV, mBindingAnswersVoted.flContainerStatisticsHAV, answersCount);
        this.mBindingAnswersVoted   .setViewModel(mViewModelVotedAnswers);
    }

    @Override
    public void updateContent(Poll data) {
        super.updateContent(data);
        mViewModelVotedAnswers.updateVotedPoll(data);
    }
}
