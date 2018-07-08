package asryab.com.mvvmproject.screens.thoughts;

import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.BaseAdapterDelegate;
import asryab.com.mvvmproject.models.polls.VoteComment;

public class ThoughtsAdapterDelegate extends BaseAdapterDelegate<VoteComment, ThoughtsHolder> {

    private final boolean withLikes;

    public ThoughtsAdapterDelegate(boolean withLikes) {
        this.withLikes = withLikes;
    }

    @Override
    protected int getHolderLayoutRes() {
        return R.layout.row_thought_comment;
    }

    @Override
    protected ThoughtsHolder getHolder(View inflatedView) {
        return new ThoughtsHolder(inflatedView, withLikes);
    }
}
