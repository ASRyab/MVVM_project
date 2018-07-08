package asryab.com.mvvmproject.viewmodels.feed.graphics;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.poll.GetStatisticsFromAnswer;
import asryab.com.mvvmproject.databinding.LayoutStatisticsGraphicBinding;
import asryab.com.mvvmproject.models.polls.PollAnswer;
import asryab.com.mvvmproject.models.polls.PollStatistics;
import asryab.com.mvvmproject.screens.feed.graphics.GraphicType;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;
import rx.functions.Action1;

public final class AnswerStatisticsFragmentVM extends ToolbarVM {

    private Context                             context;
    private OnBackPressedListener               onBackPressedListener;
    private int                                 currentChosenTab;

    public final ObservableField<PollAnswer>    pollAnswer = new ObservableField<>();
    public final ObservableBoolean              isLoadingNow = new ObservableBoolean(false);
    public final Action1<Integer>               tabSelectListener = this::tabChoose;

    private final FrameLayout                   containerGraphicsLayout;

    private PollStatistics pollStatistics;

    public AnswerStatisticsFragmentVM(final Context context, final FrameLayout containerGraphics, final OnBackPressedListener onBackPressedListener, final PollAnswer pollAnswer) {
        this.context                    = context;
        this.containerGraphicsLayout    = containerGraphics;
        this.onBackPressedListener      = onBackPressedListener;
        this.pollAnswer                 .set(pollAnswer);
        mTitle                          .set(context.getString(R.string.answer_details));

        loadStatistics();
    }

    private void tabChoose(final int position) {
        currentChosenTab = position;
        containerGraphicsLayout.removeAllViews();
        if (pollStatistics != null) {
            LayoutStatisticsGraphicBinding graphicBinding = LayoutStatisticsGraphicBinding.inflate(LayoutInflater.from(context), null, false);
            switch (currentChosenTab) {
                case 0:
                    graphicBinding.setViewModel(new GraphicLayoutVM(GraphicType.AGE, pollStatistics));
                    containerGraphicsLayout.addView(graphicBinding.getRoot());
                    break;
                case 1:
                    graphicBinding.setViewModel(new GraphicLayoutVM(GraphicType.GENDER, pollStatistics));
                    containerGraphicsLayout.addView(graphicBinding.getRoot());
                    break;
            }
        }
    }

    private void loadStatistics() {
        isLoadingNow.set(true);
        mCompositeSubscription.add(
        new GetStatisticsFromAnswer(pollAnswer.get().id)
                .getTask(context)
                .subscribe(
                        pollStatistics -> {
                            isLoadingNow.set(false);
                            this.pollStatistics = pollStatistics;
                            tabChoose(currentChosenTab);
                        }, throwable -> {
                            isLoadingNow.set(false);
                            mCompositeSubscription.add(ApiTask.errorResponseObservable(context, throwable,true));
                        }));
    }

    @Override
    public void onBackArrowPressed(View view) {
        onBackPressedListener.onBackButtonPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context = null;
        onBackPressedListener = null;
    }
}
