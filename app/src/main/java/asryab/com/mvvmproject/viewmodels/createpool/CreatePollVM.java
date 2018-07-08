package asryab.com.mvvmproject.viewmodels.createpool;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.Toast;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.viewmodels.TopicToolbarVM;
import rx.functions.Action1;

public class CreatePollVM extends TopicToolbarVM {

    private static final int MAX_SYMBOLS = 300;

    private Context     mContext;
    private String      mPostText;
    private Poll mPoll;

    public final ObservableBoolean isProgressVisible        = new ObservableBoolean();
    public final ObservableBoolean isAnswersLayoutVisible   = new ObservableBoolean(true);
    public final ObservableField<String> countSymbolsInput  = new ObservableField<>("0/300");
    public final ObservableInt tabScale                     = new ObservableInt(1);
    public final ObservableInt tabAnswers                   = new ObservableInt(0);

    public final Action1<String> countSymbolsListener       = this::updateCountSymbols;
    public final Action1<Integer> tabScaleListener          = this::selectScaleTab;
    public final Action1<Integer> tabAnswersListener        = this::selectAnswersTab;

    public CreatePollVM(Context context) {
        mContext = context;
        initToolbarFields();
    }

    private void initToolbarFields() {
        mTitle.set(mContext.getResources().getString(R.string.post_topic));
        mLeftText.set(mContext.getResources().getString(R.string.cancel));
        mRightText.set(mContext.getResources().getString(R.string.submit));
    }

    @Override
    public void cancelPressed(View view) {
        Toast.makeText(mContext, "cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void submitPressed(View view) {
        Toast.makeText(mContext, "submit", Toast.LENGTH_SHORT).show();
    }

    public void earnMoneyPressed(View view) {
        Toast.makeText(mContext, "earn money", Toast.LENGTH_SHORT).show();
    }

    private void updateCountSymbols(final String s) {
        mPostText = s;
        countSymbolsInput.set(String.format("%s/%s", mPostText.length(), MAX_SYMBOLS));
    }

    private void selectScaleTab(int position) {
        tabScale.set(position);
    }

    private void selectAnswersTab(int position) {
        tabAnswers.set(position);
        if (position == 0)
            isAnswersLayoutVisible.set(true);
        else {
            isAnswersLayoutVisible.set(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
