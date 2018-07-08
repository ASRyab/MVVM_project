package asryab.com.mvvmproject.screens.feed.petitions.updates;

import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager;

import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerAdapter;
import asryab.com.mvvmproject.models.petitions.Update;

public class UpdatesAdapter extends BaseRecyclerAdapter<Update> {
    @Override
    protected void initDelegatesManager(AdapterDelegatesManager<Update> delegatesManager) {
        delegatesManager.addDelegate(new UpdatesAdapterDelegate());
    }
}
