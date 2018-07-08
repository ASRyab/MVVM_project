package asryab.com.mvvmproject.viewmodels.feed.polls.holders;

import android.content.Context;
import android.databinding.ObservableField;

import asryab.com.mvvmproject.models.polls.PollAnswer;

public final class PollAnswersListVM extends PollBaseAnswersVM {

    public final ObservableField<PollAnswer> answerFirst    = new ObservableField<>();
    public final ObservableField<PollAnswer> answerSecond   = new ObservableField<>();
    public final ObservableField<PollAnswer> answerThird    = new ObservableField<>();
    public final ObservableField<PollAnswer> answerFour     = new ObservableField<>();
    public final ObservableField<PollAnswer> answerFive     = new ObservableField<>();

    public PollAnswersListVM(Context context) {
        super(context);
    }

    @Override
    protected int getCountAnswers() {
        return 5;
    }

    @Override
    protected void updateContent(PollAnswer[] answers) {
        answerFirst     .set(answers[0]);
        answerSecond    .set(answers[1]);
        answerThird     .set(answers[2]);
        answerFour      .set(answers[3]);
        answerFive      .set(answers[4]);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
