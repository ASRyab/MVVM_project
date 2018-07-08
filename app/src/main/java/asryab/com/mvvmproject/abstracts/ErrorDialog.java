package asryab.com.mvvmproject.abstracts;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import asryab.com.mvvmproject.R;

public abstract class ErrorDialog {
    public static void show(Context context, String errorMessage) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.app_name))
                .setIcon(context.getResources().getDrawable(R.drawable.ic_error))
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which1) ->  {
                    dialog.dismiss();
                }).show();
    }
}
