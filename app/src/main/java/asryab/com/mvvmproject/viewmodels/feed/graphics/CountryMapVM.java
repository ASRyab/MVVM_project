package asryab.com.mvvmproject.viewmodels.feed.graphics;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import asryab.com.mvvmproject.viewmodels.ViewModel;

public final class CountryMapVM extends ViewModel {

    public final ObservableField<Drawable> mapDrawable  = new ObservableField<>();
    public final ObservableBoolean isLoading            = new ObservableBoolean();

    public CountryMapVM(Context context, final ImageView boundsMapView) {

        isLoading.set(true);
    }

}
