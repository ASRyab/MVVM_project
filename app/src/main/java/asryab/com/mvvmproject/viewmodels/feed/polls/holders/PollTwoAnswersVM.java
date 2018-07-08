package asryab.com.mvvmproject.viewmodels.feed.polls.holders;

import android.content.Context;
import android.databinding.ObservableField;

import asryab.com.mvvmproject.models.polls.PollAnswer;

public final class PollTwoAnswersVM extends PollBaseAnswersVM {

    public final ObservableField<PollAnswer> answerFirst    = new ObservableField<>();
    public final ObservableField<PollAnswer> answerSecond   = new ObservableField<>();

    public PollTwoAnswersVM(Context context) {
        super(context);
    }

    @Override
    protected int getCountAnswers() {
        return 2;
    }

    @Override
    protected void updateContent(PollAnswer[] answers) {
        answerFirst .set(answers[0]);
        answerSecond.set(answers[1]);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
