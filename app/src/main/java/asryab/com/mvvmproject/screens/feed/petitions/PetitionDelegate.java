package asryab.com.mvvmproject.screens.feed.petitions;

import android.support.annotation.NonNull;
import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.BaseAdapterDelegate;
import asryab.com.mvvmproject.models.petitions.Petition;

public class PetitionDelegate extends BaseAdapterDelegate<Petition, PetitionHolder> {

    @Override
    public boolean isForViewType(@NonNull Petition item, int position) {
        return true;
    }

    @Override
    protected int getHolderLayoutRes() {
        return R.layout.holder_petition;
    }

    @Override
    protected PetitionHolder getHolder(View inflatedView) {
        return new PetitionHolder(inflatedView);
    }

}

