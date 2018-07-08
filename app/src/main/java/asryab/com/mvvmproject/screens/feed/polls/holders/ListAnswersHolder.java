package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.view.View;

import asryab.com.mvvmproject.databinding.HolderPollAnswersListBinding;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.viewmodels.feed.polls.holders.PollAnswersListVM;

public final class ListAnswersHolder extends BasePollHolder {

    private HolderPollAnswersListBinding    mBindingAnswers;
    private PollAnswersListVM mViewModelAnswers;

    public ListAnswersHolder(View view) {
        super(view);
        this.mBindingAnswers    = HolderPollAnswersListBinding.bind(view);
        this.mViewModelAnswers  = new PollAnswersListVM(view.getContext());
        this.mBindingAnswers    .setViewModel(mViewModelAnswers);
    }

    @Override
    protected void contentUpdate(Poll dataItem) {
        super.contentUpdate(dataItem);
        mViewModelAnswers.updateAnswers(dataItem.answers);
    }

}
