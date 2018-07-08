package asryab.com.mvvmproject.binding;

import android.databinding.BaseObservable;
import android.databinding.BindingConversion;

public class BindableBoolean extends BaseObservable{
    boolean value;

    public boolean get() {
        return value;
    }

    public void set(boolean _value) {
        if (this.value != _value) {
            this.value = _value;
            notifyChange();
        }
    }

    @BindingConversion
    public static boolean convertToBoolean(final BindableBoolean _bindableBoolean) {
        return _bindableBoolean.get();
    }
}
