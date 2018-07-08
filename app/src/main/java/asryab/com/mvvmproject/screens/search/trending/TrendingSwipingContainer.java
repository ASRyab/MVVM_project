package asryab.com.mvvmproject.screens.search.trending;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import asryab.com.mvvmproject.models.polls.TrendingPoll;

//todo need review

public final class TrendingSwipingContainer extends FrameLayout {

    private int currentPollPosition                     = -1;
    private int currentTop                              = 0;

    private ArrayList<TrendingPoll> trendingPolls       = new ArrayList<>();
    private final TrendingHolder[] trendingHolders      = new TrendingHolder[2];
    private float cardsOffset                           = 0f;

    private FlingCardTouchEvaluator.FlingListener<TrendingPoll> trendingPollFlingListener;
    private FlingCardTouchEvaluator topTouchEvaluator;

    public TrendingSwipingContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initializeViews(final float cardsOffset, final IManualChooseAnswerListener manualChooseAnswerListener, final FlingCardTouchEvaluator.FlingListener<TrendingPoll> flingListener) {
        this.cardsOffset            = cardsOffset;
        trendingHolders[0]          = new TrendingHolder(this, manualChooseAnswerListener);
        trendingHolders[1]          = new TrendingHolder(this, manualChooseAnswerListener);
        trendingPollFlingListener   = flingListener;
    }

    public void updateData(final ArrayList<TrendingPoll> trendingPolls) {
        removeAllViews();
        this.trendingPolls = trendingPolls;
        if (this.trendingPolls.size() > 0) {
            addView(trendingHolders[1].getView());
            addView(trendingHolders[0].getView());

            currentPollPosition = 0;
            currentTop          = 0;
            updateContent();
            defaultConfigCards();
        }
    }

    private void updateContent() {
        if (currentPollPosition >= 0 && currentPollPosition < trendingPolls.size()) {
            trendingHolders[currentTop].update(trendingPolls.get(currentPollPosition));
            if (currentPollPosition + 1 < trendingPolls.size())
                trendingHolders[getForegroundPosition()].update(trendingPolls.get(currentPollPosition + 1));
            else removeView(trendingHolders[getForegroundPosition()].getView());
        } else {
            removeAllViews();
            if (currentPollPosition > 0 && currentPollPosition == trendingPolls.size() && trendingPollFlingListener != null)
                trendingPollFlingListener.onEndQueue();

        }
    }

    private void changeTop() {
        removeView(         trendingHolders[currentTop].getView());
        removeAllConfigs(   trendingHolders[currentTop].getView());
        moveForegroundView( 1f);

        currentTop = getForegroundPosition();
        initializeForegroundView();
        configFocusTouching();

        currentPollPosition++;
        addView(trendingHolders[getForegroundPosition()].getView(), 0);
        updateContent();
    }

    private void configFocusTouching() {
        trendingHolders[getForegroundPosition()].getView().setOnTouchListener(null);
        trendingHolders[getForegroundPosition()].getView().setFocusableInTouchMode(false);
        if (trendingHolders[currentTop].getView().getParent() != null) {
            trendingHolders[currentTop].getView().setFocusableInTouchMode(true);
            trendingHolders[currentTop].getView().setOnTouchListener(
                    topTouchEvaluator = new FlingCardTouchEvaluator<>(trendingHolders[currentTop].getView(), trendingPolls.get(currentPollPosition), new FlingCardTouchEvaluator.FlingListener<TrendingPoll>() {
                        @Override
                        public void onCardExited() {
                            Log.d("tag", "Change Top");
                            changeTop();
                            if (trendingPollFlingListener != null)
                                trendingPollFlingListener.onCardExited();
                        }

                        @Override
                        public void leftExit(TrendingPoll dataObject) {
                            Log.d("tag", "Left Exit");
                            if (trendingPollFlingListener != null)
                                trendingPollFlingListener.leftExit(dataObject);
                        }

                        @Override
                        public void rightExit(TrendingPoll dataObject) {
                            Log.d("tag", "Right Exit");
                            if (trendingPollFlingListener != null)
                                trendingPollFlingListener.rightExit(dataObject);
                        }

                        @Override
                        public void onClick(TrendingPoll dataObject) {
                            if (trendingPollFlingListener != null)
                                trendingPollFlingListener.onClick(dataObject);
                        }

                        @Override
                        public void onEndQueue() {

                        }

                        @Override
                        public void onScroll(float scrollProgressPercent) {
                            moveForegroundView(Math.abs(scrollProgressPercent));
                            if (trendingPollFlingListener != null)
                                trendingPollFlingListener.onScroll(scrollProgressPercent);
                        }
                    }));
        } else {
            trendingHolders[currentTop].getView().setOnTouchListener(null);
            trendingHolders[currentTop].getView().setFocusableInTouchMode(false);
        }
    }

    private void removeAllConfigs(final View targetView) {
        targetView.setX(0);
        targetView.setY(0);
        targetView.setTranslationX(0);
        targetView.setTranslationY(0);
        targetView.setRotation(0);
    }

    private int getForegroundPosition() {
        return currentTop == 0 ? 1 : 0;
    }

    private void initializeForegroundView() {
        final View targetView = trendingHolders[getForegroundPosition()].getView();
        if (targetView.getParent() != null) {
            targetView.setTranslationY(cardsOffset);

            targetView.setPivotY(targetView.getHeight());
            targetView.setPivotX(targetView.getWidth() / 2);

            targetView.setScaleX(1 - (2 * cardsOffset / getWidth()));
            targetView.setScaleY(1 - (2 * cardsOffset / getWidth()));
        }
    }

    private void moveForegroundView(final float percent) {
        final View targetView = trendingHolders[getForegroundPosition()].getView();

        targetView.setTranslationY((1f - percent) * cardsOffset);

        targetView.setPivotY(targetView.getHeight());
        targetView.setPivotX(targetView.getWidth() / 2);

        targetView.setScaleX(1 - (1f - percent) * (2 * cardsOffset / getWidth()));
        targetView.setScaleY(1 - (1f - percent) * (2 * cardsOffset / getWidth()));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        defaultConfigCards();
    }

    private void defaultConfigCards() {
        if (getWidth() > 0 && getHeight() > 0 && trendingHolders[currentTop] != null && trendingHolders[getForegroundPosition()] != null) {
            configFocusTouching();
            initializeForegroundView();
        }
    }

    public void select(final boolean left) {
        if (topTouchEvaluator != null) {
            if (left)
                topTouchEvaluator.selectLeft();
            else topTouchEvaluator.selectRight();
        }
    }

}
