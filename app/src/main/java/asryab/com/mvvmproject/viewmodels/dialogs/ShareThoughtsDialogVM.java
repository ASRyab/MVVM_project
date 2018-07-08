package asryab.com.mvvmproject.viewmodels.dialogs;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.dialogs.ShareThoughtsDialog;
import asryab.com.mvvmproject.models.polls.PollAnswer;
import asryab.com.mvvmproject.viewmodels.ViewModel;
import rx.functions.Action1;

public class ShareThoughtsDialogVM extends ViewModel {

    private static final int MAX_SYMBOLS = 250;

    private String thoughts;
    private ShareThoughtsDialog.IPostThoughtsListener iPostThoughtsListener;
    private Action1<Boolean> dismissDialogSender;
    private Context context;

    public final ObservableField<PollAnswer> pollAnswer     = new ObservableField<>();
    public final ObservableField<String> countSymbolsInput  = new ObservableField<>("");
    public final Action1<String> countSymbolsListener       = this::updateCountSymbols;
    public final ObservableBoolean forPublicReview          = new ObservableBoolean(true);

    public ShareThoughtsDialogVM(final Context context,
                                 final ShareThoughtsDialog.IPostThoughtsListener iPostThoughtsListener,
                                 final PollAnswer pollAnswer,
                                 final Action1<Boolean> dismissDialogSender) {

        this.context                = context;
        this.iPostThoughtsListener  = iPostThoughtsListener;
        this.dismissDialogSender    = dismissDialogSender;
        this.pollAnswer             .set(pollAnswer);
        this.updateCountSymbols("");
    }

    public void post(final View viewPost) {
        if (TextUtils.isEmpty(thoughts))
            Toast.makeText(context, R.string.error_empty_thoughts, Toast.LENGTH_SHORT).show();
        else {
            dismissDialog();
            iPostThoughtsListener.post(thoughts, forPublicReview.get());
        }
    }

    public void skip(final View viewSkip) {
        dismissDialog();
        iPostThoughtsListener.skip();
    }

    private void dismissDialog() {
        dismissDialogSender.call(true);
    }

    private void updateCountSymbols(final String s) {
        thoughts = s;
        countSymbolsInput.set(String.format("%s/%s", thoughts.length(), MAX_SYMBOLS));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        thoughts                = null;
        iPostThoughtsListener   = null;
        context                 = null;
    }

}
