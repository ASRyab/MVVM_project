package asryab.com.mvvmproject.screens.small_lists;

import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager;

import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerAdapter;
import asryab.com.mvvmproject.models.polls.Poll;

public final class SmallPollsAdapter extends BaseRecyclerAdapter<Poll> {

    @Override
    protected void initDelegatesManager(AdapterDelegatesManager<Poll> delegatesManager) {
        delegatesManager.addDelegate(new SmallPollsDelegate());
    }

}
