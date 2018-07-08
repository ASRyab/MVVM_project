package asryab.com.mvvmproject.screens.feed.polls.holders;

import android.view.View;

import asryab.com.mvvmproject.abstracts.recycler.BaseAdapterDelegate;
import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerHolder;
import asryab.com.mvvmproject.abstracts.recycler.IExtendedContent;

public abstract class ExtendedContentBaseDelegate<T, H extends BaseRecyclerHolder> extends BaseAdapterDelegate<T, H> {

    protected boolean isExtended = false;

    public ExtendedContentBaseDelegate(boolean isExtended) {
        this.isExtended = isExtended;
    }

    protected abstract H getExtendedHolder(final View inflatedView);

    @Override
    protected H getHolder(View inflatedView) {
        final H holder = getExtendedHolder(inflatedView);
        ((IExtendedContent) holder).setIsExtended(isExtended);
        return holder;
    }
}
