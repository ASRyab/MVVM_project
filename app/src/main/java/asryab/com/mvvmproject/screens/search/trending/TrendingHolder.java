package asryab.com.mvvmproject.screens.search.trending;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.databinding.LayoutTrendingCardBinding;
import asryab.com.mvvmproject.models.polls.TrendingPoll;
import asryab.com.mvvmproject.viewmodels.search.TrendingHolderVM;

public class TrendingHolder {

    private LayoutTrendingCardBinding   mBindingTrendingCard;
    private TrendingHolderVM mViewModelTrending;

    public TrendingHolder(final ViewGroup parent, final IManualChooseAnswerListener manualChooseAnswerListener) {
        mBindingTrendingCard    = LayoutTrendingCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        mViewModelTrending      = new TrendingHolderVM(manualChooseAnswerListener);
        mBindingTrendingCard    .setViewModel(mViewModelTrending);
    }

    public final View getView() {
        return mBindingTrendingCard.getRoot();
    }

    public void update(final TrendingPoll trendingPoll) {
        mViewModelTrending.updateCard(trendingPoll);
    }

}