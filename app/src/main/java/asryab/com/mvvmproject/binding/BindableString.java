package asryab.com.mvvmproject.binding;

import android.databinding.BaseObservable;
import android.text.TextUtils;

public class BindableString extends BaseObservable {
    String value;

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (!TextUtils.equals(this.value,value)) {
            this.value = value;
            notifyChange();
        }
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }
}
