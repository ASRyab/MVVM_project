package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.support.annotation.NonNull;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.polls.Poll;

public class VotedAnswersDelegate extends ExtendedContentBaseDelegate<Poll, VotedAnswersHolder> {

    private final int answersCount;

    public VotedAnswersDelegate(int answersCount, final boolean isExtended) {
        super(isExtended);
        this.answersCount = answersCount;
    }

    @Override
    public boolean isForViewType(@NonNull Poll item, int position) {
        return (item.voted || item.isClosedPoll()) && (item.category_count == answersCount);
    }

    @Override
    protected int getHolderLayoutRes() {
        return R.layout.holder_answers_voted;
    }

    @Override
    protected VotedAnswersHolder getExtendedHolder(View inflatedView) {
        return new VotedAnswersHolder(inflatedView, answersCount);
    }

}