package asryab.com.mvvmproject.screens.small_lists;

import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager;

import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerAdapter;
import asryab.com.mvvmproject.models.petitions.Petition;

public final class SmallPetitionsAdapter extends BaseRecyclerAdapter<Petition> {

    @Override
    protected void initDelegatesManager(AdapterDelegatesManager<Petition> delegatesManager) {
        delegatesManager.addDelegate(new SmallPetitionsDelegate());
    }

}
