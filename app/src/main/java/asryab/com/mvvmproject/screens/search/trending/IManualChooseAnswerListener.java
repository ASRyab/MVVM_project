package asryab.com.mvvmproject.screens.search.trending;

import asryab.com.mvvmproject.models.polls.TrendingPoll;

public interface IManualChooseAnswerListener {
    void yes(final TrendingPoll trendingPoll);

    void no(final TrendingPoll trendingPoll);
}
