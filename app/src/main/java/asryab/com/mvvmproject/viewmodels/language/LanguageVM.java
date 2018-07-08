package asryab.com.mvvmproject.viewmodels.language;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import asryab.com.mvvmproject.viewmodels.ViewModel;

public class LanguageVM extends ViewModel {

    public final ObservableField<String> mLanguage  = new ObservableField<>();
    public final ObservableBoolean isCheckVisible   = new ObservableBoolean(false);

    public LanguageVM(String language) {
        mLanguage.set(language);
    }

    @Override
    public void onDestroy() {

    }
}
