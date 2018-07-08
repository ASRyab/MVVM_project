package asryab.com.mvvmproject.screens.feed.petitions;

import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager;

import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerAdapter;
import asryab.com.mvvmproject.models.petitions.Petition;

public final class PetitionsAdapter extends BaseRecyclerAdapter<Petition> {

    @Override
    protected void initDelegatesManager(AdapterDelegatesManager<Petition> delegatesManager) {
        delegatesManager.addDelegate(new PetitionDelegate());
    }

}
