package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.view.View;

import asryab.com.mvvmproject.databinding.HolderPollThreeAnswersBinding;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.viewmodels.feed.polls.holders.PollThreeAnswersVM;

public final class ThreeAnswersHolder extends BasePollHolder {

    private HolderPollThreeAnswersBinding   mBindingAnswers;
    private PollThreeAnswersVM mViewModelAnswers;

    public ThreeAnswersHolder(View view) {
        super(view);
        this.mBindingAnswers    = HolderPollThreeAnswersBinding.bind(view);
        this.mViewModelAnswers  = new PollThreeAnswersVM(view.getContext());
        this.mBindingAnswers    .setViewModel(mViewModelAnswers);
    }

    @Override
    protected void contentUpdate(Poll dataItem) {
        super.contentUpdate(dataItem);
        mViewModelAnswers.updateAnswers(dataItem.answers);
    }

}
