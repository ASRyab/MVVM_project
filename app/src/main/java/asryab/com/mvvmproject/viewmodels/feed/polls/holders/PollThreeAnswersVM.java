package asryab.com.mvvmproject.viewmodels.feed.polls.holders;

import android.content.Context;
import android.databinding.ObservableField;

import asryab.com.mvvmproject.models.polls.PollAnswer;

public final class PollThreeAnswersVM extends PollBaseAnswersVM {

    public final ObservableField<PollAnswer> noAnswer       = new ObservableField<>();
    public final ObservableField<PollAnswer> notSureAnswer  = new ObservableField<>();
    public final ObservableField<PollAnswer> yesAnswer      = new ObservableField<>();

    public PollThreeAnswersVM(Context context) {
        super(context);
    }

    @Override
    protected int getCountAnswers() {
        return 3;
    }

    @Override
    protected void updateContent(PollAnswer[] answers) {
        for (PollAnswer answer: answers) {
            switch (answer.value) {
                case 0:
                    noAnswer.set(answer);
                    break;
                case 1:
                    notSureAnswer.set(answer);
                    break;
                case 2:
                    yesAnswer.set(answer);
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
