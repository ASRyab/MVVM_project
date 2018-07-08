package asryab.com.mvvmproject.viewmodels.feed.petitions;

import android.content.Context;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.petitions.Petition;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;

public class ReadMorePetitionVM extends ToolbarVM {

    private final Context context;
    private final OnBackPressedListener backPressedListener;
    private final Petition petition;

    @Override
    public void onBackArrowPressed(View view) {
        backPressedListener.onBackButtonPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public ReadMorePetitionVM(final Context context, final Petition petition, final OnBackPressedListener backPressedListener) {
        this.context                    = context;
        this.petition                   = petition;
        this.backPressedListener        = backPressedListener;
        mTitle                          .set(context.getString(R.string.petition_details));
    }
}
