package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.support.annotation.NonNull;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.polls.Poll;

/**
 * Created by Asus_Dev on 5/30/2016.
 */
public final class TwoAnswersDelegate extends ExtendedContentBaseDelegate<Poll, TwoAnswersHolder> {

    public TwoAnswersDelegate(boolean isExtended) {
        super(isExtended);
    }

    @Override
    public boolean isForViewType(@NonNull Poll item, int position) {
        return (!item.voted && !item.isClosedPoll()) && item.category_count == 2;
    }

    @Override
    protected int getHolderLayoutRes() {
        return R.layout.holder_poll_two_answers;
    }

    @Override
    protected TwoAnswersHolder getExtendedHolder(View inflatedView) {
        return new TwoAnswersHolder(inflatedView);
    }

}
