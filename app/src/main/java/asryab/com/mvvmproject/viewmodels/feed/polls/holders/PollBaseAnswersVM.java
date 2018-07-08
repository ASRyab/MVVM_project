package asryab.com.mvvmproject.viewmodels.feed.polls.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import java.util.concurrent.TimeUnit;

import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.poll.CreateVoteForPoll;
import asryab.com.mvvmproject.binding.BindingActionClick;
import asryab.com.mvvmproject.dialogs.ShareThoughtsDialog;
import asryab.com.mvvmproject.dialogs.VotingProgressDialog;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.models.polls.PollAnswer;
import asryab.com.mvvmproject.models.polls.PollVote;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public abstract class PollBaseAnswersVM extends ViewModel implements BindingActionClick<PollAnswer> {

    private static final int DURATION_DELAY = 5000;

    protected abstract int getCountAnswers();
    protected abstract void updateContent(PollAnswer[] answers);
    protected Context context;

    public PollBaseAnswersVM(Context context) {
        this.context = context;
    }

    public void updateAnswers(PollAnswer[] answers) {
        if (answers != null && answers.length == getCountAnswers())
            updateContent(answers);
    }

    @Override
    public void onBindClick(View viewClicked, PollAnswer answer) {
        voteWithThought(answer);
    }

    private void voteWithThought(final PollAnswer pollAnswer) {
        new ShareThoughtsDialog(context, pollAnswer, new ShareThoughtsDialog.IPostThoughtsListener() {
            @Override
            public void post(String thoughts, boolean isPublic) {
                vote(pollAnswer.poll_id, new PollVote(pollAnswer.id, isPublic, thoughts));
            }

            @Override
            public void skip() {
                vote(pollAnswer.poll_id, new PollVote(pollAnswer.id));
            }
        }).show();
    }

    private void vote(final int poll_id, final PollVote pollVote) {
        final VotingProgressDialog votingProgressDialog = new VotingProgressDialog(context);
        votingProgressDialog.show();
        mCompositeSubscription.add(new CreateVoteForPoll(poll_id, pollVote)
                .getTask(context)
                .delaySubscription(DURATION_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(
                    poll -> {
                        votingProgressDialog.dismiss();
                        voteCreated(poll);
                    },
                    throwable -> {
                        votingProgressDialog.dismiss();
                        mCompositeSubscription.add(ApiTask.errorResponseObservable(context, throwable,true));
                    }));
    }

    private void voteCreated(final Poll poll) {
        final Intent intent = new Intent(Arguments.Broadcast.EVENT_UPDATE_POLL);
        intent.putExtra(Arguments.Broadcast.INTENT_KEY_POLL, poll);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context = null;
    }
}
