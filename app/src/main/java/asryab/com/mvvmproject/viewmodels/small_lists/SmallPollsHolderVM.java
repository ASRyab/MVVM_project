package asryab.com.mvvmproject.viewmodels.small_lists;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.polls.Level;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.models.polls.PollAnswer;
import asryab.com.mvvmproject.screens.home.HomeFragmentsHelper;
import asryab.com.mvvmproject.screens.home.HomeTabsActivity;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.utils.DateTimeUtils;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public final class SmallPollsHolderVM extends ViewModel {

    public final ObservableBoolean isSelfVoted              = new ObservableBoolean();
    public final ObservableField<String> image              = new ObservableField<>();
    public final ObservableField<String> locationTimePosted = new ObservableField<>();
    public final ObservableField<String> pollOfThe          = new ObservableField<>();
    public final ObservableField<String> title              = new ObservableField<>();
    public final ObservableField<String> bestAnswer         = new ObservableField<>();
    private Poll poll;

    private final Context context;

    public SmallPollsHolderVM(final Context context) {
        this.context = context;
    }

    public void updateItem(final Poll poll) {
        this.poll           = poll;
        isSelfVoted         .set(poll.voted);
        image               .set(poll.url);
        locationTimePosted  .set(String.format("%s  \u2022  %s",
                                poll.getLevel().equals(Level.NATIONWIDE) ? context.getString(R.string.country_without_star) : poll.location,
                                DateTimeUtils.formatStringDayMonth(DateTimeUtils.parseDateStringToDate(poll.publish_at))));
        pollOfThe           .set(context.getString(poll.getCategory().getStringResByCategory()));
        title               .set(poll.title);
        bestAnswer          .set(getBestAnswer(poll.answers, poll.category_count));
    }

    private String getBestAnswer(PollAnswer[] answers, final int pollAnswersCount) {
        String best     = "";
        int percent     = -1;

        for (PollAnswer answer: answers) {
            if (percent < answer.percents) {
                percent = answer.percents;
                best = answer.getLabelText(context, pollAnswersCount);
            }
        }
        return best;
    }

    public void clickOnPoll(final View view) {
        Log.d("polls", "Open poll with ID: " + poll.id);
        HomeTabsActivity.startWith(context, prepareDetailPollBundle(), HomeFragmentsHelper.FragmentCopy.POLL_DETAIL, false);
    }

    private Bundle prepareDetailPollBundle() {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Arguments.Fragment.ARG_POLL, poll);
        return bundle;
    }

}
