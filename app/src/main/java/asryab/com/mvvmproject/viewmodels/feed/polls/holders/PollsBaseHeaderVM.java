package asryab.com.mvvmproject.viewmodels.feed.polls.holders;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.polls.Level;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.screens.home.HomeFragmentsHelper;
import asryab.com.mvvmproject.screens.home.HomeTabsActivity;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.utils.DateTimeUtils;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public final class PollsBaseHeaderVM extends ViewModel {

    public final ObservableField<String> pollOfThe      = new ObservableField<>("");
    public final ObservableField<String> location       = new ObservableField<>("");
    public final ObservableField<String> title          = new ObservableField<>("");
    public final ObservableField<String> leftTime       = new ObservableField<>("");
    public final ObservableField<String> votes          = new ObservableField<>("");
    public final ObservableField<String> imageSrc       = new ObservableField<>("");
    public final ObservableField<String> titleMoreBtn   = new ObservableField<>("");
    public final ObservableBoolean isShowMoreContent    = new ObservableBoolean(false);
    public final ObservableBoolean isShowVotedContent   = new ObservableBoolean(false);
    public final ObservableBoolean visibleMoreBtn       = new ObservableBoolean(true);
    public final ObservableBoolean isExtended           = new ObservableBoolean(false);

    private Poll currentPoll;
    private Context         context;
    private LayoutInflater  layoutInflater;
    private LinearLayout    moreInfoLayout;

    public PollsBaseHeaderVM(Context context, LinearLayout moreInfoLayout) {
        this.context        = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.moreInfoLayout = moreInfoLayout;
    }

    public void updatePoll(final Poll poll) {
        currentPoll = poll;

        pollOfThe   .set(context.getString(currentPoll.getCategory().getStringResByCategory()));
        location    .set(String.format("%s  \u2022  %s",
                        currentPoll.getLevel().equals(Level.NATIONWIDE) ? context.getString(R.string.country_without_star) : currentPoll.location,
                        DateTimeUtils.formatStringDayMonth(DateTimeUtils.parseDateStringToDate(currentPoll.publish_at))));
        title       .set(currentPoll.title);

        if (poll.isClosedPoll())
            leftTime.set(context.getString(R.string.closed_poll));
        else if (!TextUtils.isEmpty(currentPoll.close_at))
            leftTime.set(String.format("%s %s", DateTimeUtils.formatStringLeftTime(
                                                        DateTimeUtils.parseDateStringToLong(currentPoll.close_at) - System.currentTimeMillis(),
                                                        context.getString(R.string.days),
                                                        context.getString(R.string.hrs),
                                                        context.getString(R.string.day),
                                                        context.getString(R.string.hr),
                                                        context.getString(R.string.min)), context.getString(R.string.left)));
        else leftTime.set("");

        votes       .set(String.format("%s %s", String.format(Locale.ENGLISH, "%,d", currentPoll.votes_count), context.getString(R.string.votes)));
        imageSrc    .set(currentPoll.url);

        buildContentMoreInfo(!poll.isShowMore);
        updateTitleMoreBtn();

        isShowVotedContent  .set(poll.voted || poll.isClosedPoll());
        isShowMoreContent   .set(poll.isShowMore);
        visibleMoreBtn      .set(!(poll.more_info == null || poll.more_info.length == 0));
    }

    private void updateTitleMoreBtn() {
        titleMoreBtn.set(currentPoll.isShowMore ? context.getString(currentPoll.voted ? R.string.see_results : R.string.vote_now) : context.getString(R.string.more_info));
    }

    public void moreInfo(final View moreView) {
        buildContentMoreInfo(isShowMoreContent.get());
        isShowMoreContent.set(!isShowMoreContent.get());
        currentPoll.isShowMore = isShowMoreContent.get();
        updateTitleMoreBtn();
    }

    public void showDetail(final View detailClickedView) {
        HomeTabsActivity.startWith(context, prepareDetailPollBundle(), HomeFragmentsHelper.FragmentCopy.POLL_DETAIL, false);
    }

    public void share(final View shareView) {
        Toast.makeText(context, "Share Poll Content", Toast.LENGTH_SHORT).show();
    }

    private void buildContentMoreInfo(boolean clear) {
        moreInfoLayout.removeAllViews();
        if (!clear) {
            for (int i = 0; i < currentPoll.more_info.length; i++)
                moreInfoLayout.addView(getInfoTextItem(currentPoll.more_info[i], i == currentPoll.more_info.length - 1));
        }
    }

    private View getInfoTextItem(final String text, final boolean isLastItem) {
        final View infoItem = layoutInflater.inflate(R.layout.layout_more_info_text_item, null, false);
        if (isLastItem)
            infoItem.setBackground(null);
        ((TextView) infoItem.findViewById(R.id.tvMoreInfoText_LMITI)).setText(text);
        return infoItem;
    }

    private Bundle prepareDetailPollBundle() {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Arguments.Fragment.ARG_POLL, currentPoll);
        return bundle;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context = null;
    }

}
