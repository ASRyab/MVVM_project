package asryab.com.mvvmproject.viewmodels.language;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import asryab.com.mvvmproject.data.DataStorage;
import asryab.com.mvvmproject.models.user.Language;
import asryab.com.mvvmproject.screens.intro.IntroActivity;
import asryab.com.mvvmproject.screens.language.LanguageAdapter;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public class LanguageActivityVM extends ViewModel {

    private Context context;

    public RecyclerView.LayoutManager mManager;
    public RecyclerView.Adapter mAdapter;

    private Language mLanguage = DataStorage.getAppLanguage();

    public LanguageActivityVM(Context context) {
        this.context    = context;
        this.mManager   = new LinearLayoutManager(context);
        this.mAdapter   = new LanguageAdapter(context, Language.getAllLanguages());
    }

    public void getStarted(@NonNull View v)  {
        DataStorage.saveLanguage(mLanguage);
        IntroActivity.startIt(context);
    }

    @Override
    public void onDestroy() {
        mAdapter    = null;
        context     = null;
    }
}
