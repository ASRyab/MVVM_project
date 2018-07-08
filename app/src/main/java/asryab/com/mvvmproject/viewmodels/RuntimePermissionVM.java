package asryab.com.mvvmproject.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public abstract class RuntimePermissionVM extends ViewModel {

    protected boolean isGrantedPermission(final Context context, final String[] permissions) {
        for (String permission : permissions)
            if (!isGrantedPermission(context, permission))
                return false;
        return true;
    }

    protected boolean isGrantedPermission(final Context context, final String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    protected boolean isResultGranted(final int[] grantResults) {
        for (int grant: grantResults)
            if (grant != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    protected void requestPermission(final Activity activity, final String[] permissions, int requestPermissionCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestPermissionCode);
    }

    public abstract void permissionResult(int requestCode, String[] permissions, int[] grantResults);

}
