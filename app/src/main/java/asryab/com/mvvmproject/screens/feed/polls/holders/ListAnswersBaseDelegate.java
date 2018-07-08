package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.support.annotation.NonNull;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.polls.Poll;

public final class ListAnswersBaseDelegate extends ExtendedContentBaseDelegate<Poll, ListAnswersHolder> {

    public ListAnswersBaseDelegate(boolean isExtended) {
        super(isExtended);
    }

    @Override
    public boolean isForViewType(@NonNull Poll item, int position) {
        return (!item.voted && !item.isClosedPoll()) && item.category_count == 5;
    }

    @Override
    protected int getHolderLayoutRes() {
        return R.layout.holder_poll_answers_list;
    }

    @Override
    protected ListAnswersHolder getExtendedHolder(View inflatedView) {
        return new ListAnswersHolder(inflatedView);
    }

}
