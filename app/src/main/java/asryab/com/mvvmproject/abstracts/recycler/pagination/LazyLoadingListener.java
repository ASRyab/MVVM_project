package asryab.com.mvvmproject.abstracts.recycler.pagination;

import android.support.v7.widget.RecyclerView;

public abstract class LazyLoadingListener extends RecyclerView.OnScrollListener {

    private static final int MIN_ELEMENTS_COUNT_TO_NEXT_LOAD    = 1;
    private int prevLastPosition                                = -1;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        final int lastVisiblePos = getLastVisiblePosition();
        final int allCount = getAllItems();

        if (lastVisiblePos > prevLastPosition && lastVisiblePos >= allCount - MIN_ELEMENTS_COUNT_TO_NEXT_LOAD && prevLastPosition < allCount - MIN_ELEMENTS_COUNT_TO_NEXT_LOAD) {
            loadNext();
        }

        prevLastPosition = lastVisiblePos;
    }

    protected abstract void loadNext();
    protected abstract int getAllItems();
    protected abstract int getLastVisiblePosition();

}
