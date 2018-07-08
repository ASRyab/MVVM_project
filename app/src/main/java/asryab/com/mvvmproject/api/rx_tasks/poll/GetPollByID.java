package asryab.com.mvvmproject.api.rx_tasks.poll;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.polls.Poll;
import rx.Observable;

public final class GetPollByID extends ApiTask<Poll> {

    private int id;

    public GetPollByID(int pollId) {
        super();
        this.id = pollId;
    }

    @Override
    protected Observable<Poll> getObservableTask(Context _context) {
        return Api.getInst().feed().getPollByID(id);
    }

}
