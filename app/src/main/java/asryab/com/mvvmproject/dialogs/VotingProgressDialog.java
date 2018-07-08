package asryab.com.mvvmproject.dialogs;

import android.content.Context;

import asryab.com.mvvmproject.abstracts.BaseDialog;
import asryab.com.mvvmproject.databinding.DialogVotingProgressBinding;
import asryab.com.mvvmproject.viewmodels.dialogs.VotingProgressVM;

public final class VotingProgressDialog extends BaseDialog {

    private VotingProgressVM mViewModelProgress;
    private DialogVotingProgressBinding     mBindingVotingProgress;

    public VotingProgressDialog(Context context) {
        super(context);
        setCancelable(false);
        mBindingVotingProgress = DialogVotingProgressBinding.inflate(getLayoutInflater());
        mBindingVotingProgress.setViewModel(mViewModelProgress = new VotingProgressVM());
        setContentView(mBindingVotingProgress.getRoot());
    }

}
