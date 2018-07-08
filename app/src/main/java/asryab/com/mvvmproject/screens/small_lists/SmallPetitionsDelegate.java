package asryab.com.mvvmproject.screens.small_lists;

import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.BaseAdapterDelegate;
import asryab.com.mvvmproject.models.petitions.Petition;

public final class SmallPetitionsDelegate extends BaseAdapterDelegate<Petition, SmallPetitionsHolder> {

    @Override
    protected int getHolderLayoutRes() {
        return R.layout.holder_petition_small;
    }

    @Override
    protected SmallPetitionsHolder getHolder(View inflatedView) {
        return new SmallPetitionsHolder(inflatedView);
    }

}
