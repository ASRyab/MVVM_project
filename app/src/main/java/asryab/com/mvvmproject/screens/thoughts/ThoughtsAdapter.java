package asryab.com.mvvmproject.screens.thoughts;

import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager;

import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerAdapter;
import asryab.com.mvvmproject.models.polls.VoteComment;

public class ThoughtsAdapter extends BaseRecyclerAdapter<VoteComment> {

    private final boolean withLikes;

    public ThoughtsAdapter(boolean withLikes) {
        super(false);
        this.withLikes = withLikes;
        fillDelegatesManger(true);
    }

    @Override
    protected void initDelegatesManager(AdapterDelegatesManager<VoteComment> delegatesManager) {
        delegatesManager.addDelegate(new ThoughtsAdapterDelegate(withLikes));
    }

}
