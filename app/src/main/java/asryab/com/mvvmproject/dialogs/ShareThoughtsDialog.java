package asryab.com.mvvmproject.dialogs;

import android.content.Context;

import asryab.com.mvvmproject.abstracts.BaseDialog;
import asryab.com.mvvmproject.databinding.DialogShareYourThoughtsBinding;
import asryab.com.mvvmproject.models.polls.PollAnswer;
import asryab.com.mvvmproject.viewmodels.dialogs.ShareThoughtsDialogVM;
import rx.functions.Action1;

public final class ShareThoughtsDialog extends BaseDialog implements Action1<Boolean> {

    private DialogShareYourThoughtsBinding  mBindingThoughtsDialog;
    private ShareThoughtsDialogVM mViewModelShareThoughts;

    public ShareThoughtsDialog(Context context, final PollAnswer pollAnswer, final IPostThoughtsListener iPostThoughtsListener) {
        super(context);
        mBindingThoughtsDialog  = DialogShareYourThoughtsBinding.inflate(getLayoutInflater());
        mViewModelShareThoughts = new ShareThoughtsDialogVM(context, iPostThoughtsListener, pollAnswer, this);
        mBindingThoughtsDialog  .setViewModel(mViewModelShareThoughts);

        setContentView(mBindingThoughtsDialog.getRoot());
    }

    @Override
    public void call(Boolean dismissDialog) {
        if (dismissDialog)
            dismiss();
    }

    public interface IPostThoughtsListener {
        void post(final String thoughts, final boolean isPublic);
        void skip();
    }

}
