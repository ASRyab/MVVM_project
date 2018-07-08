package asryab.com.mvvmproject.viewmodels.feed.polls.holders;

import android.content.Context;
import android.databinding.ObservableField;

import asryab.com.mvvmproject.models.polls.PollAnswer;

public final class PollAnswersGridVM extends PollBaseAnswersVM {

    public final ObservableField<PollAnswer> answerFirst    = new ObservableField<>();
    public final ObservableField<PollAnswer> answerSecond   = new ObservableField<>();
    public final ObservableField<PollAnswer> answerThird    = new ObservableField<>();
    public final ObservableField<PollAnswer> answerFour     = new ObservableField<>();

    public PollAnswersGridVM(Context context) {
        super(context);
    }

    @Override
    protected int getCountAnswers() {
        return 4;
    }

    @Override
    protected void updateContent(PollAnswer[] answers) {
        answerFirst     .set(answers[0]);
        answerSecond    .set(answers[1]);
        answerThird     .set(answers[2]);
        answerFour      .set(answers[3]);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
