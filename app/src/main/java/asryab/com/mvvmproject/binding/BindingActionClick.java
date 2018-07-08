package asryab.com.mvvmproject.binding;

import android.view.View;

public interface BindingActionClick<T> {
    void onBindClick(final View viewClicked, final T model);
}
