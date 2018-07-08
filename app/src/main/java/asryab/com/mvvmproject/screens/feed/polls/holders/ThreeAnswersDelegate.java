package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.support.annotation.NonNull;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.polls.Poll;

public final class ThreeAnswersDelegate extends ExtendedContentBaseDelegate<Poll, ThreeAnswersHolder> {

    public ThreeAnswersDelegate(boolean isExtended) {
        super(isExtended);
    }

    @Override
    public boolean isForViewType(@NonNull Poll item, int position) {
        return (!item.voted && !item.isClosedPoll()) && item.category_count == 3;
    }

    @Override
    protected int getHolderLayoutRes() {
        return R.layout.holder_poll_three_answers;
    }

    @Override
    protected ThreeAnswersHolder getExtendedHolder(View inflatedView) {
        return new ThreeAnswersHolder(inflatedView);
    }

}
