package asryab.com.mvvmproject.abstracts.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseRecyclerHolder<T> extends RecyclerView.ViewHolder {

    protected T mDataItem;

    public BaseRecyclerHolder(View itemView) {
        super(itemView);
    }

    //Need implementing
    public void updateContent(final T data) {
        mDataItem = data;
        contentUpdate(mDataItem);
    }

    protected abstract void contentUpdate(final T dataItem);
}
