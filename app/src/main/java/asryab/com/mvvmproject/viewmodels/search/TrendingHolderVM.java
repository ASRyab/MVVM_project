package asryab.com.mvvmproject.viewmodels.search;

import android.databinding.ObservableField;
import android.view.View;

import asryab.com.mvvmproject.models.polls.TrendingPoll;
import asryab.com.mvvmproject.screens.search.trending.IManualChooseAnswerListener;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public final class TrendingHolderVM extends ViewModel {

    public final ObservableField<String>    message = new ObservableField<>();
    private TrendingPoll trendingPoll;

    private final IManualChooseAnswerListener chooseAnswerListener;

    public TrendingHolderVM(IManualChooseAnswerListener chooseAnswerListener) {
        this.chooseAnswerListener = chooseAnswerListener;
    }

    public void updateCard(final TrendingPoll poll) {
        trendingPoll = poll;
        if (message.get() == null || !message.get().equals(trendingPoll.message))
            message.set(trendingPoll.message);
    }

    public void clickYes(final View view) {
        this.chooseAnswerListener.yes(trendingPoll);
    }

    public void clickNo(final View view) {
        this.chooseAnswerListener.no(trendingPoll);
    }

}
