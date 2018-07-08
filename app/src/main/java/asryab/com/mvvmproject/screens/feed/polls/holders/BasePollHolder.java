package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.view.LayoutInflater;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerHolder;
import asryab.com.mvvmproject.abstracts.recycler.IExtendedContent;
import asryab.com.mvvmproject.databinding.HolderPollBaseBinding;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.viewmodels.feed.polls.holders.PollsBaseHeaderVM;

public abstract class BasePollHolder extends BaseRecyclerHolder<Poll> implements IExtendedContent {

    protected PollsBaseHeaderVM mViewModelHead;
    protected HolderPollBaseBinding mBindingHead;

    public BasePollHolder(View contentView) {
        super(LayoutInflater.from(contentView.getContext()).inflate(R.layout.holder_poll_base, null, false));

        this.mBindingHead   = HolderPollBaseBinding.bind(itemView);
        this.mViewModelHead = new PollsBaseHeaderVM(contentView.getContext(), mBindingHead.layoutMoreInfo.llMoreInfoContainer);
        this.mBindingHead   .setHeaderViewModel(mViewModelHead);
        this.mBindingHead   .flAdditionalContainerHPB.addView(contentView);
        this.mBindingHead   .executePendingBindings();
    }

    @Override
    public void setIsExtended(final boolean isExtended) {
        mViewModelHead.isExtended.set(isExtended);
    }

    @Override
    protected void contentUpdate(Poll dataItem) {
        mViewModelHead.updatePoll(dataItem);
    }

}
