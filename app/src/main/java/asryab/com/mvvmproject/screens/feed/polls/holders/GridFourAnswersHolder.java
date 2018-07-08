package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.view.View;

import asryab.com.mvvmproject.databinding.HolderPollAnswersGridFoursBinding;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.viewmodels.feed.polls.holders.PollAnswersGridVM;

public final class GridFourAnswersHolder extends BasePollHolder {

    private HolderPollAnswersGridFoursBinding   mBindingAnswers;
    private PollAnswersGridVM mViewModelAnswers;

    public GridFourAnswersHolder(View view) {
        super(view);
        this.mBindingAnswers    = HolderPollAnswersGridFoursBinding.bind(view);
        this.mViewModelAnswers  = new PollAnswersGridVM(view.getContext());
        this.mBindingAnswers    .setViewModel(mViewModelAnswers);
    }

    @Override
    protected void contentUpdate(Poll dataItem) {
        super.contentUpdate(dataItem);
        mViewModelAnswers.updateAnswers(dataItem.answers);
    }

}
