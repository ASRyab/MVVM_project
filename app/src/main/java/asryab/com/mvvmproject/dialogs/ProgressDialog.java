package asryab.com.mvvmproject.dialogs;

import android.content.Context;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseDialog;

public final class ProgressDialog extends BaseDialog {

    public ProgressDialog(Context context) {
        super(context);
        setCancelable(false);
        setContentView(R.layout.layout_progress_dialog);
    }

}
