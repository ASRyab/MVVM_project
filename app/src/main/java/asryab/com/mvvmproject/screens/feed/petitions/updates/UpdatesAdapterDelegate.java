package asryab.com.mvvmproject.screens.feed.petitions.updates;

import android.view.View;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.recycler.BaseAdapterDelegate;
import asryab.com.mvvmproject.models.petitions.Update;

public class UpdatesAdapterDelegate extends BaseAdapterDelegate<Update,UpdatesHolder> {
    @Override
    protected int getHolderLayoutRes() {
        return R.layout.row_update_petition;
    }

    @Override
    protected UpdatesHolder getHolder(View inflatedView) {
        return new UpdatesHolder(inflatedView);
    }
}
