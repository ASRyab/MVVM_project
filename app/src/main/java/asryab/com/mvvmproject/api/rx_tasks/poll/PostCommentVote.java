package asryab.com.mvvmproject.api.rx_tasks.poll;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.polls.VoteComment;
import rx.Observable;

public final class PostCommentVote extends ApiTask<VoteComment> {

    private final int vote_id;

    public PostCommentVote(int vote_id) {
        this.vote_id = vote_id;
    }

    @Override
    protected Observable<VoteComment> getObservableTask(Context _context) {
        final Map<String, Integer> map = new HashMap<>();
        map.put(VOTE_ID, vote_id);
        return Api.getInst().feed().postLikeComment(map);
    }

}
