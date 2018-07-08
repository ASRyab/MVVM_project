package asryab.com.mvvmproject.screens.feed;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class CustomScrollTabsView extends HorizontalScrollView {

    private int[] leftChildViews;
    private IScrollTabsListener scrollTabsListener;
    private boolean inFocusMode = false;

    public CustomScrollTabsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollTabsListener(IScrollTabsListener scrollTabsListener) {
        this.scrollTabsListener = scrollTabsListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final LinearLayout llRoot   = (LinearLayout) getChildAt(0);
        final int leftCut           = llRoot.getChildAt(0).getMeasuredWidth() / 2;
        final int rightCut          = llRoot.getChildAt(llRoot.getChildCount() - 1).getMeasuredWidth() / 2;
        final int centerX           = getMeasuredWidth() / 2;

        refreshPadding(centerX - leftCut, 0, centerX - rightCut, 0);
        updateBoundView(llRoot, leftCut, rightCut);
    }

    private void updateBoundView(final LinearLayout rootLayout, final int leftCut, final int rightCut) {
        if (leftChildViews == null) leftChildViews = new int[rootLayout.getChildCount()];

        int sumOffset = 0;
        for (int i = 0; i < rootLayout.getChildCount(); i++) {
            sumOffset = sumOffset + (i > 0 ? rootLayout.getChildAt(i - 1).getMeasuredWidth() : 0);
            leftChildViews[i] = i > 0 ? sumOffset + rootLayout.getChildAt(i).getMeasuredWidth() / 2 - leftCut : sumOffset;
        }
    }

    private void refreshPadding(int left, int top, int right, int bottom) {
        setPadding(left, top, right, bottom);
    }

    @Override
    public void fling(int velocityX) {
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (Math.abs(l - oldl) < 2)
            checkMatch(l);
    }

    private void checkMatch(int offset) {
        int chosenPos = matchCurrentPosition(offset);
        if (chosenPos >= 0 && !inFocusMode && scrollTabsListener != null)
            scrollTabsListener.tabChosen(chosenPos);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                inFocusMode = false;
                int scrollX = getScrollX();
                scrollByOffset(scrollX + getOffsetToNearestItem(scrollX), scrollX);
                break;
            default:
                inFocusMode = true;
                break;
        }
        return super.onTouchEvent(event);
    }

    private int getOffsetToNearestItem(int currentOffset) {
        int min = Integer.MAX_VALUE;
        for (int offset: leftChildViews) {
            int div = offset - currentOffset;
            if (Math.abs(div) < Math.abs(min))
                min = div;
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    // return -1 if not matches
    private int matchCurrentPosition(int currentOffset) {
        for (int i = 0; i < leftChildViews.length; i++) {
            if (leftChildViews[i] == currentOffset)
                return i;
        }
        return -1;
    }

    private void scrollByOffset(final int needScroll, final int oldScroll) {
        if (needScroll != oldScroll)
            post(() -> smoothScrollTo(needScroll, 0));
        else
            checkMatch(needScroll);
    }

    public void scrollByPosition(final int position) {
        if (leftChildViews != null) {
            int scrollX = getScrollX();
            final int needScroll = scrollX + (leftChildViews[position] - scrollX);
            if (matchCurrentPosition(scrollX) != position) scrollByOffset(needScroll, scrollX);
        }
    }

    public interface IScrollTabsListener {
        void tabChosen(final int position);
    }

}
