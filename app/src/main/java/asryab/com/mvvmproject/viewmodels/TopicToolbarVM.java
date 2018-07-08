package asryab.com.mvvmproject.viewmodels;

import android.databinding.ObservableField;
import android.view.View;

public abstract class TopicToolbarVM extends ViewModel {
    public final ObservableField<String> mTitle      = new ObservableField<>();
    public final ObservableField<String> mLeftText   = new ObservableField<>();
    public final ObservableField<String> mRightText  = new ObservableField<>();

    public abstract void cancelPressed(View view);
    public abstract void submitPressed(View view);
}
