package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.support.annotation.NonNull;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.polls.Poll;

public final class GridFourAnswersBaseDelegate extends ExtendedContentBaseDelegate<Poll, GridFourAnswersHolder> {

    public GridFourAnswersBaseDelegate(boolean isExtended) {
        super(isExtended);
    }

    @Override
    public boolean isForViewType(@NonNull Poll item, int position) {
        return (!item.voted && !item.isClosedPoll()) && item.category_count == 4;
    }

    @Override
    protected int getHolderLayoutRes() {
        return R.layout.holder_poll_answers_grid_fours;
    }

    @Override
    protected GridFourAnswersHolder getExtendedHolder(View inflatedView) {
        return new GridFourAnswersHolder(inflatedView);
    }

}
