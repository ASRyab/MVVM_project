package asryab.com.mvvmproject.abstracts;

import android.content.BroadcastReceiver;
import android.content.Context;

public interface IBroadcastImpl {
    void registerBroadcast(final Context context);
    void unRegisterBroadcast(final Context context);
    BroadcastReceiver implReceiver();
}
