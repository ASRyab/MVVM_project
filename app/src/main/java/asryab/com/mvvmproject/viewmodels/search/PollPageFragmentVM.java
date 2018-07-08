package asryab.com.mvvmproject.viewmodels.search;

import android.content.Context;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.polls.Level;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.screens.home.HomeFragmentsHelper;
import asryab.com.mvvmproject.screens.home.HomeTabsActivity;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.utils.DateTimeUtils;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public class PollPageFragmentVM extends ViewModel {

    public final Poll pollModel;
    public final ObservableField<Float>     alphaTitles         = new ObservableField<>();
    public final ObservableField<Float>     offsetXTitle        = new ObservableField<>();
    public final ObservableField<Float>     offsetXSubTitles    = new ObservableField<>();

    public final ObservableField<String>    pollOfThe           = new ObservableField<>();
    public final ObservableField<String>    location            = new ObservableField<>();
    public final ObservableField<String>    timeToEnd           = new ObservableField<>();
    private final Context                   context;

    private final float maxOffset;

    public PollPageFragmentVM(final Context context, final Poll poll) {
        this.context                = context;
        this.pollModel              = poll;
        this.alphaTitles            .set(1f);
        this.offsetXTitle           .set(0f);
        this.offsetXSubTitles       .set(0f);
        this.maxOffset              = context.getResources().getDimension(R.dimen.app_intro_title_max_offset_from_default_state);

        fillPollPageInfo(context);
    }

    private void fillPollPageInfo(final Context context) {
        pollOfThe   .set(context.getString(pollModel.getCategory().getStringResByCategory()));
        location    .set(String.format("%s  \u2022  %s",
                        pollModel.getLevel().equals(Level.NATIONWIDE) ? context.getString(R.string.country_without_star) : pollModel.location,
                        DateTimeUtils.formatStringDayMonth(DateTimeUtils.parseDateStringToDate(pollModel.publish_at))));

        if (pollModel.isClosedPoll())
            timeToEnd.set(context.getString(R.string.closed_poll));
        else if (!TextUtils.isEmpty(pollModel.close_at))
            timeToEnd.set(String.format("%s %s", DateTimeUtils.formatStringLeftTime(
                    DateTimeUtils.parseDateStringToLong(pollModel.close_at) - System.currentTimeMillis(),
                    context.getString(R.string.days),
                    context.getString(R.string.hrs),
                    context.getString(R.string.day),
                    context.getString(R.string.hr),
                    context.getString(R.string.min)), context.getString(R.string.left)));
        else timeToEnd.set("");
    }

    public void setOffsetTitles(final float offset) {
        alphaTitles         .set(1 - Math.abs(offset));
        offsetXTitle        .set(maxOffset * offset);
        offsetXSubTitles    .set(maxOffset * offset / 2);
    }

    public void clickOnPoll(final View view) {
        HomeTabsActivity.startWith(context, prepareDetailPollBundle(), HomeFragmentsHelper.FragmentCopy.POLL_DETAIL, false);
    }

    private Bundle prepareDetailPollBundle() {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Arguments.Fragment.ARG_POLL, pollModel);
        return bundle;
    }

    @Override
    public void onDestroy() {
    }

}
