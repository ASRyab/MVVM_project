package asryab.com.mvvmproject.abstracts.recycler.pagination;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import asryab.com.mvvmproject.R;

public final class RefreshLayout extends SwipeRefreshLayout {

    public RefreshLayout(Context context) {
        super(context);
        initProgressColors();
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initProgressColors();
    }

    private void initProgressColors() {
        setColorSchemeColors(
                getResources().getColor(R.color.base_red),
                getResources().getColor(R.color.base_brown),
                getResources().getColor(R.color.base_green),
                getResources().getColor(R.color.base_blue));
    }

    @BindingAdapter({"onRefreshListener"})
    public static void bindRefreshLayoutListener(final RefreshLayout refreshLayout, OnRefreshListener refreshListener) {
        refreshLayout.setOnRefreshListener(refreshListener);
    }

    @BindingAdapter({"onStopRefresh"})
    public static void bindRefreshLayoutStop(final RefreshLayout refreshLayout, Boolean refreshing) {
        if (!refreshing) // if need refreshing every moment when load -> remove this line
            refreshLayout.setRefreshing(refreshing);
    }

}
