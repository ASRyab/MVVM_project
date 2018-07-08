package asryab.com.mvvmproject.screens.small_lists;

import android.view.View;

import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerHolder;
import asryab.com.mvvmproject.databinding.HolderPollSmallBinding;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.viewmodels.small_lists.SmallPollsHolderVM;

public final class SmallPollsHolder extends BaseRecyclerHolder<Poll> {

    private HolderPollSmallBinding   mBindingPastPolls;
    private SmallPollsHolderVM mViewModelPastPolls;

    public SmallPollsHolder(View itemView) {
        super(itemView);
        this.mBindingPastPolls    = HolderPollSmallBinding.bind(itemView);
        this.mViewModelPastPolls  = new SmallPollsHolderVM(itemView.getContext());
        this.mBindingPastPolls    .setViewModel(mViewModelPastPolls);
    }

    @Override
    protected void contentUpdate(Poll dataItem) {
        mViewModelPastPolls.updateItem(dataItem);
    }

}
