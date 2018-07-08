package asryab.com.mvvmproject.viewmodels;

import android.databinding.ObservableBoolean;
import android.support.v7.widget.LinearLayoutManager;

import asryab.com.mvvmproject.abstracts.recycler.pagination.LazyLoadingListener;
import asryab.com.mvvmproject.abstracts.recycler.pagination.RefreshLayout;
import asryab.com.mvvmproject.models.MoreLoadingInfo;

public abstract class PaginationVM extends ViewModel implements RefreshLayout.OnRefreshListener {

    private boolean isCanLoadMore                           = true;
    public final ObservableBoolean isShowDropDownRefresh    = new ObservableBoolean(false);
    public final MoreLoadingInfo moreLoadingInfo            = new MoreLoadingInfo(getMoreLoadingCount());

    protected abstract void loadData();
    protected abstract LinearLayoutManager getLayoutManagerForMoreLoading();

    public void loadMore() {
        isCanLoadMore = false;
        moreLoadingInfo.increasePage();
        loadData();
    }

    public void refresh() {
        isCanLoadMore           = false;
        moreLoadingInfo         .resetPage();
        isShowDropDownRefresh   .set(true);
        loadData();
    }

    protected void loadFinished() {
        isShowDropDownRefresh.set(false);
        isCanLoadMore = true;
    }

    protected int getMoreLoadingCount(){
        return MoreLoadingInfo.DEFAULT_PAGE_COUNT;
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public final LazyLoadingListener lazyLoadingListener = new LazyLoadingListener() {
        @Override
        protected void loadNext() {
            if (isCanLoadMore && moreLoadingInfo.page > 0)
                loadMore();
        }

        @Override
        protected int getAllItems() {
            return getLayoutManagerForMoreLoading() != null ? getLayoutManagerForMoreLoading().getItemCount() : 0;
        }

        @Override
        protected int getLastVisiblePosition() {
            return getLayoutManagerForMoreLoading() != null ? getLayoutManagerForMoreLoading().findLastVisibleItemPosition() : 0;
        }
    };

}