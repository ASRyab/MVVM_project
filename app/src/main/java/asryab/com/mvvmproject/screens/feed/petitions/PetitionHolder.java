package asryab.com.mvvmproject.screens.feed.petitions;

import android.view.View;

import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerHolder;
import asryab.com.mvvmproject.databinding.HolderPetitionBinding;
import asryab.com.mvvmproject.models.petitions.Petition;
import asryab.com.mvvmproject.viewmodels.feed.petitions.PetitionVM;

public class PetitionHolder extends BaseRecyclerHolder<Petition> {

    public HolderPetitionBinding holderPetitionBinding;
    public PetitionVM petitionVM;

    public PetitionHolder(View view) {
        super(view);
        holderPetitionBinding = HolderPetitionBinding.bind(itemView);
        petitionVM = new PetitionVM(view.getContext(), false);
        holderPetitionBinding.setViewModel(petitionVM);
    }

    @Override
    protected void contentUpdate(Petition dataItem) {
        petitionVM.updatePetition(dataItem);
    }

}
