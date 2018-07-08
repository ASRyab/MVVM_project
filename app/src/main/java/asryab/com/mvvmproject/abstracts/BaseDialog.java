package asryab.com.mvvmproject.abstracts;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
        setCancelable(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
