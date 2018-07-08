package asryab.com.mvvmproject.screens.small_lists;

import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.BaseAdapterDelegate;
import asryab.com.mvvmproject.models.polls.Poll;

public final class SmallPollsDelegate extends BaseAdapterDelegate<Poll, SmallPollsHolder> {

    @Override
    protected int getHolderLayoutRes() {
        return R.layout.holder_poll_small;
    }

    @Override
    protected SmallPollsHolder getHolder(View inflatedView) {
        return new SmallPollsHolder(inflatedView);
    }

}
