package asryab.com.mvvmproject.screens.feed.graphics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentAnswerStatisticsBinding;
import asryab.com.mvvmproject.models.polls.PollAnswer;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;
import asryab.com.mvvmproject.viewmodels.feed.graphics.AnswerStatisticsFragmentVM;

public final class AnswerStatisticsFragment extends BaseFragment implements ToolbarVM.OnBackPressedListener {

    private FragmentAnswerStatisticsBinding mBindingAnswerStatistics;
    private AnswerStatisticsFragmentVM mViewModelAnswerStatistics;

    private PollAnswer mPollAnswer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        injectDataBinding(mBindingAnswerStatistics = FragmentAnswerStatisticsBinding.inflate(inflater, container, false));
        injectViewModel(mViewModelAnswerStatistics = new AnswerStatisticsFragmentVM(getContext(), mBindingAnswerStatistics.flContainerGraphicsFAS, this, mPollAnswer));
        mBindingAnswerStatistics.setViewModel(mViewModelAnswerStatistics);

        return mBindingAnswerStatistics.getRoot();
    }

    @Override
    protected void readFromBundle(Bundle bundle) {
        mPollAnswer = (PollAnswer) bundle.getSerializable(Arguments.Fragment.ARG_POLL_ANSWER);
    }

    @Override
    public void onBackButtonPressed() {
        onBackPressed();
    }
}
