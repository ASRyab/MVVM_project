package asryab.com.mvvmproject.viewmodels.feed.petitions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.VerticalFillColorItemDecoration;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.petition.KeepUpdatePetition;
import asryab.com.mvvmproject.api.rx_tasks.petition.SignPetition;
import asryab.com.mvvmproject.models.petitions.Petition;
import asryab.com.mvvmproject.screens.feed.petitions.updates.UpdatesAdapter;
import asryab.com.mvvmproject.screens.home.HomeFragmentsHelper;
import asryab.com.mvvmproject.screens.home.HomeTabsActivity;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.utils.DateTimeUtils;
import asryab.com.mvvmproject.viewmodels.ViewModel;
import rx.functions.Action1;

public class PetitionVM extends ViewModel {
    private static final String LOG_TAG = PetitionVM.class.getSimpleName();
    public final ObservableField<String> location = new ObservableField<>("");
    public final ObservableField<String> description = new ObservableField<>("");
    public final ObservableField<String> title = new ObservableField<>("");
    public final ObservableField<String> leftTime = new ObservableField<>("");
    public final ObservableField<String> supporters = new ObservableField<>("");

    public final ObservableBoolean isKeepUpdated = new ObservableBoolean(false);
    public final ObservableBoolean isSigned = new ObservableBoolean(false);
    public final ObservableBoolean hasUpdates = new ObservableBoolean(false);
    public final ObservableBoolean hasUpdatesMore = new ObservableBoolean(false);
    public final ObservableBoolean isClosed = new ObservableBoolean(false);
    public final ObservableBoolean isSingle = new ObservableBoolean(false);
    public final ObservableBoolean isShowProgress = new ObservableBoolean(false);
    private final Context context;

    public Petition currentPetition;

    public RecyclerView.LayoutManager updatesLayoutManager;
    public VerticalFillColorItemDecoration updatesDividerDecoration;
    public UpdatesAdapter updatesAdapter;
    private BroadcastReceiver updateReceiver;

    public PetitionVM(Context context, boolean isSingle) {
        this.context = context;
        this.isSingle.set(isSingle);
        this.updatesLayoutManager = new LinearLayoutManager(context);
        this.updatesDividerDecoration = new VerticalFillColorItemDecoration(context, R.drawable.divider_thoughts);
        this.updatesAdapter = new UpdatesAdapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void updatePetition(Petition dataItem) {
        isShowProgress.set(false);
        currentPetition = dataItem;

        location.set(String.format("%s  \u2022  %s", currentPetition.region, DateTimeUtils.formatStringDayMonth(DateTimeUtils.parseDateStringToDate(currentPetition.createdAt))));
        title.set(currentPetition.title);
        description.set(isSingle.get() ? currentPetition.longDescription : currentPetition.shortDescription);
        isKeepUpdated.set(currentPetition.isKeepUpdated);
        isSigned.set(currentPetition.isSigned());
        isClosed.set(currentPetition.isClosedPetition());
        hasUpdates.set(hasUpdate());
        hasUpdatesMore.set(hasUpdatesMore());
        supporters.set(String.format("%s %s", currentPetition.signsCount + "", context.getString(R.string.supporters)));

        if (currentPetition.isClosedPetition()) {
            leftTime.set(context.getString(R.string.closed_petition));
        } else if (!TextUtils.isEmpty(currentPetition.closeAt))
            leftTime.set(String.format("%s %s", DateTimeUtils.formatStringLeftTime(
                    DateTimeUtils.parseDateStringToLong(currentPetition.closeAt) - System.currentTimeMillis(),
                    context.getString(R.string.days),
                    context.getString(R.string.hrs),
                    context.getString(R.string.day),
                    context.getString(R.string.hr),
                    context.getString(R.string.min)), context.getString(R.string.left)));

        updatesAdapter.clearAllData();
        if (hasUpdate())
            updatesAdapter.addData(currentPetition.updates);
        else updatesAdapter.notifyDataSetChanged();
    }

    public void tapPetition(View v) {
        Log.d(LOG_TAG, "tapPetition");
        isShowProgress.set(true);
        mCompositeSubscription.add(new SignPetition(currentPetition.id).getTask(context)
                .subscribe(petition -> {
                    updatePetition(petition);
                    if (isSingle.get())
                        sendBroadcast(petition);
                }
                        , this::showErrorResponse));
        Toast.makeText(context, "tapPetition", Toast.LENGTH_LONG).show();
    }

    private void showErrorResponse(final Throwable throwable) {
        isShowProgress.set(false);
        mCompositeSubscription.add(ApiTask.errorResponseObservable(context, throwable,true));
    }

    private void sendBroadcast(Petition petition) {
        Intent intent = new Intent(Arguments.Broadcast.EVENT_UPDATE_PETITION_READ_MORE);
        intent.putExtra(Arguments.Fragment.ARG_PETITION_READ_MORE,petition);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public Action1<Boolean> keepUpdated = this::setKeepUpdated;

    private void setKeepUpdated(Boolean aBoolean) {
        isShowProgress.set(true);
        isKeepUpdated.set(aBoolean);
        Log.d(LOG_TAG, "isKeepUpdated=" + aBoolean);
        mCompositeSubscription.add(new KeepUpdatePetition(aBoolean, currentPetition.id).getTask(context)
                .subscribe(this::updatePetition
                        , this::showErrorResponse));


    }

    public void viewAllUpdates(View v) {
        Log.d(LOG_TAG, "viewAllUpdates");
        HomeTabsActivity.startWith(context, prepareAllUpdatesBundle(), HomeFragmentsHelper.FragmentCopy.PETITION_ALL_UPDATES, false);

    }

    private Bundle prepareAllUpdatesBundle() {
        final Bundle bundle = new Bundle();
        bundle.putInt(Arguments.Fragment.ARG_PETITION_ALL_UPDATES, currentPetition.id);
        return bundle;
    }

    public void tapReadMore(View v) {
        Log.d(LOG_TAG, "tapReadMore");
        initReceiver();
        LocalBroadcastManager.getInstance(context).registerReceiver(updateReceiver, new IntentFilter(Arguments.Broadcast.EVENT_UPDATE_PETITION_READ_MORE));
        HomeTabsActivity.startWith(context, prepareReadMoreBundle(), HomeFragmentsHelper.FragmentCopy.PETITION_READ_MORE, false);
    }

    private void initReceiver() {
        updateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Petition petition = (Petition) intent.getExtras().getSerializable(Arguments.Fragment.ARG_PETITION_READ_MORE);
                updatePetition(petition);
                unregisterReceiver();
            }
        };
    }

    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(updateReceiver);
        updateReceiver = null;
    }

    private Bundle prepareReadMoreBundle() {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Arguments.Fragment.ARG_PETITION_READ_MORE, currentPetition);
        return bundle;
    }

    public boolean hasUpdate() {
        return currentPetition.updates != null && currentPetition.updates.size() > 0;
    }

    public boolean hasUpdatesMore() {
        return currentPetition.updates != null && currentPetition.updates.size() == 3;
    }

}
