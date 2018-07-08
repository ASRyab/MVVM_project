package asryab.com.mvvmproject.viewmodels.thoughts;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.api.rx_tasks.poll.PostCommentVote;
import asryab.com.mvvmproject.databinding.LayoutReportBinding;
import asryab.com.mvvmproject.dialogs.ProgressDialog;
import asryab.com.mvvmproject.models.polls.VoteComment;
import asryab.com.mvvmproject.utils.DateTimeUtils;
import asryab.com.mvvmproject.utils.image_transform.CircleTransform;
import asryab.com.mvvmproject.viewmodels.ViewModel;
import asryab.com.mvvmproject.viewmodels.dialogs.ReportSheetDialogVM;

public final class ThoughtsItemVM extends ViewModel {

    public final ObservableField<String>    userName        = new ObservableField<>("");
    public final ObservableField<String>    dateUpdated     = new ObservableField<>("");
    public final ObservableField<String>    avatar          = new ObservableField<>("");
    public final ObservableField<String>    comment         = new ObservableField<>("");
    public final ObservableField<String>    votes           = new ObservableField<>("");
    public final ObservableBoolean          withMyLike      = new ObservableBoolean(false);
    public final ObservableBoolean          withLikes       = new ObservableBoolean(false);
    public final CircleTransform circleTransform;

    private VoteComment voteComment;

    public ThoughtsItemVM(final Context context, final boolean withLikes) {
        this.circleTransform    = new CircleTransform(context);
        this.withLikes          .set(withLikes);
    }

    public void updateThought(final VoteComment voteComment) {
        this.voteComment = voteComment;

        userName    .set(voteComment.profile.username);
        dateUpdated .set(DateTimeUtils.formatStringDayMonthAndYear(DateTimeUtils.parseDateStringToDate(voteComment.created_at)));
        avatar      .set(voteComment.profile.avatarUrl);
        comment     .set(voteComment.comment);

        if (withLikes.get()) {
            votes       .set(voteComment.likes_count < 100 ? String.valueOf(voteComment.likes_count) : String.format("%s+", voteComment.likes_count));
            withMyLike  .set(voteComment.liked);
        }
    }

    public void report(final View view) {
        final BottomSheetDialog bottomSheetDialog   = new BottomSheetDialog(view.getContext());
        final LayoutReportBinding reportBinding     = LayoutReportBinding.inflate(LayoutInflater.from(view.getContext()));

        reportBinding.setViewModel(new ReportSheetDialogVM(new ReportSheetDialogVM.IReportNavigateListener() {
            @Override
            public void report() {
                Toast.makeText(view.getContext(), "Report on " + voteComment.profile.username, Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }

            @Override
            public void cancel() {
                bottomSheetDialog.dismiss();
            }
        }));
        bottomSheetDialog.setContentView(reportBinding.getRoot());
        bottomSheetDialog.show();
    }

    public void like(final View view) {
        if (!voteComment.liked) {
            final Context context               = view.getContext();
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.show();
            new PostCommentVote(voteComment.id)
                    .getTask(context)
                    .subscribe(voteComment -> {
                        progressDialog.dismiss();
                        updateThought(voteComment);
                        Toast.makeText(context, R.string.like_is_posted, Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        progressDialog.dismiss();
                        mCompositeSubscription.add(ApiTask.errorResponseObservable(context, throwable,true));
                    });
        }
    }

    @Override
    public void onDestroy() {

    }

}
