package asryab.com.mvvmproject.screens.thoughts;

import android.view.View;

import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerHolder;
import asryab.com.mvvmproject.databinding.RowThoughtCommentBinding;
import asryab.com.mvvmproject.models.polls.VoteComment;
import asryab.com.mvvmproject.viewmodels.thoughts.ThoughtsItemVM;

public final class ThoughtsHolder extends BaseRecyclerHolder<VoteComment> {

    private RowThoughtCommentBinding    mBindingThoughtsItem;
    private ThoughtsItemVM mViewModelThoughtsItem;

    public ThoughtsHolder(View itemView, final boolean withLikes) {
        super(itemView);
        mBindingThoughtsItem = RowThoughtCommentBinding.bind(itemView);
        mBindingThoughtsItem.setViewModel(mViewModelThoughtsItem = new ThoughtsItemVM(itemView.getContext(), withLikes));
    }

    @Override
    protected void contentUpdate(VoteComment dataItem) {
        mViewModelThoughtsItem.updateThought(dataItem);
    }
}
