package asryab.com.mvvmproject.screens.feed.petitions.updates;

import android.view.View;

import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerHolder;
import asryab.com.mvvmproject.databinding.RowUpdatePetitionBinding;
import asryab.com.mvvmproject.models.petitions.Update;
import asryab.com.mvvmproject.viewmodels.feed.petitions.updates.UpdateItemVM;

public class UpdatesHolder extends BaseRecyclerHolder<Update> {
    private final UpdateItemVM mViewModelUpdateItem;
    private final RowUpdatePetitionBinding mBindingThoughtsItem;

    public UpdatesHolder(View itemView) {
        super(itemView);
        mBindingThoughtsItem = RowUpdatePetitionBinding.bind(itemView);
        mBindingThoughtsItem.setViewModel(mViewModelUpdateItem = new UpdateItemVM());
    }

    @Override
    protected void contentUpdate(Update dataItem) {
        mViewModelUpdateItem.updateViewModel(dataItem);
    }
}
