package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.view.View;

import asryab.com.mvvmproject.databinding.HolderPollTwoAnswersBinding;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.viewmodels.feed.polls.holders.PollTwoAnswersVM;

public final class TwoAnswersHolder extends BasePollHolder {

    private HolderPollTwoAnswersBinding mBindingAnswers;
    private PollTwoAnswersVM mViewModelAnswers;

    public TwoAnswersHolder(View view) {
        super(view);
        this.mBindingAnswers    = HolderPollTwoAnswersBinding.bind(view);
        this.mViewModelAnswers  = new PollTwoAnswersVM(view.getContext());
        this.mBindingAnswers    .setViewModel(mViewModelAnswers);
    }

    @Override
    protected void contentUpdate(Poll dataItem) {
        super.contentUpdate(dataItem);
        mViewModelAnswers.updateAnswers(dataItem.answers);
    }

}
