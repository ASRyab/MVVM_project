package asryab.com.mvvmproject.screens.small_lists;

import android.view.View;

import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerHolder;
import asryab.com.mvvmproject.databinding.HolderPetitionSmallBinding;
import asryab.com.mvvmproject.models.petitions.Petition;
import asryab.com.mvvmproject.viewmodels.small_lists.SmallPetitionsHolderVM;

public final class SmallPetitionsHolder extends BaseRecyclerHolder<Petition> {

    private HolderPetitionSmallBinding   mBindingPastPetitions;
    private SmallPetitionsHolderVM mViewModelPastPetitions;

    public SmallPetitionsHolder(View itemView) {
        super(itemView);
        this.mBindingPastPetitions    = HolderPetitionSmallBinding.bind(itemView);
        this.mViewModelPastPetitions  = new SmallPetitionsHolderVM(itemView.getContext());
        this.mBindingPastPetitions    .setViewModel(mViewModelPastPetitions);
    }

    @Override
    protected void contentUpdate(Petition dataItem) {
        mViewModelPastPetitions.updateItem(dataItem);
    }

}
