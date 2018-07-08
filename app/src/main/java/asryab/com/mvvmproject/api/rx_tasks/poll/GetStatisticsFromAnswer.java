package asryab.com.mvvmproject.api.rx_tasks.poll;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.polls.PollStatistics;
import rx.Observable;

public final class GetStatisticsFromAnswer extends ApiTask<PollStatistics> {

    private final int answerId;

    public GetStatisticsFromAnswer(int answerId) {
        super();
        this.answerId = answerId;
    }

    @Override
    protected Observable<PollStatistics> getObservableTask(Context _context) {
        return Api.getInst().feed().getStatisticsFromAnswer(answerId);
    }
}
