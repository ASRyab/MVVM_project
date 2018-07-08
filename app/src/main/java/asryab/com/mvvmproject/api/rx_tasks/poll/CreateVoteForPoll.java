package asryab.com.mvvmproject.api.rx_tasks.poll;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.models.polls.PollVote;
import rx.Observable;

public final class CreateVoteForPoll extends ApiTask<Poll> {

    private int         pollId;
    private PollVote votePoll;

    public CreateVoteForPoll(int pollId, PollVote votePoll) {
        super();
        this.pollId = pollId;
        this.votePoll = votePoll;
    }

    @Override
    protected Observable<Poll> getObservableTask(Context _context) {
        return Api.getInst().feed().createVote(pollId, votePoll);
    }
}
