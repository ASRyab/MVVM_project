package asryab.com.mvvmproject.viewmodels.intro;

import android.content.Context;
import android.databinding.ObservableField;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.intro.IntroPageModel;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public class IntroFragmentVM extends ViewModel {

    public final IntroPageModel introPageModel;
    public final ObservableField<Float>     alphaTitles     = new ObservableField<>();
    public final ObservableField<Float>     offsetXTitle    = new ObservableField<>();
    public final ObservableField<Float>     offsetXSubTitle = new ObservableField<>();

    private final float maxOffset;

    public IntroFragmentVM(final Context context, final IntroPageModel introPageModel) {
        this.introPageModel     = introPageModel;
        this.alphaTitles        .set(1f);
        this.offsetXTitle       .set(0f);
        this.offsetXSubTitle    .set(0f);
        this.maxOffset          = context.getResources().getDimension(R.dimen.app_intro_title_max_offset_from_default_state);
    }

    public void setOffsetTitles(final float offset) {
        alphaTitles     .set(1 - Math.abs(offset));
        offsetXTitle    .set(maxOffset * offset);
        offsetXSubTitle .set(maxOffset * offset / 2);
    }

    @Override
    public void onDestroy() {
    }

}
