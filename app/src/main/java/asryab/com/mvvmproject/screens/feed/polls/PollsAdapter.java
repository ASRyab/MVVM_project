package asryab.com.mvvmproject.screens.feed.polls;

import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager;

import asryab.com.mvvmproject.abstracts.recycler.BaseRecyclerAdapter;
import asryab.com.mvvmproject.models.polls.Poll;
import asryab.com.mvvmproject.screens.feed.polls.holders.GridFourAnswersBaseDelegate;
import asryab.com.mvvmproject.screens.feed.polls.holders.ListAnswersBaseDelegate;
import asryab.com.mvvmproject.screens.feed.polls.holders.ThreeAnswersDelegate;
import asryab.com.mvvmproject.screens.feed.polls.holders.TwoAnswersDelegate;
import asryab.com.mvvmproject.screens.feed.polls.holders.VotedAnswersDelegate;

public final class PollsAdapter extends BaseRecyclerAdapter<Poll> {

    private boolean isExtendedPoll = false;

    public PollsAdapter(boolean isExtendedPoll) {
        super(false);
        this.isExtendedPoll = isExtendedPoll;
        fillDelegatesManger(true);
    }

    public PollsAdapter() {
        super();
    }

    @Override
    protected void initDelegatesManager(AdapterDelegatesManager<Poll> delegatesManager) {
        delegatesManager.addDelegate(new ListAnswersBaseDelegate(isExtendedPoll));
        delegatesManager.addDelegate(new GridFourAnswersBaseDelegate(isExtendedPoll));
        delegatesManager.addDelegate(new TwoAnswersDelegate(isExtendedPoll));
        delegatesManager.addDelegate(new ThreeAnswersDelegate(isExtendedPoll));

        delegatesManager.addDelegate(new VotedAnswersDelegate(2, isExtendedPoll));
        delegatesManager.addDelegate(new VotedAnswersDelegate(3, isExtendedPoll));
        delegatesManager.addDelegate(new VotedAnswersDelegate(4, isExtendedPoll));
        delegatesManager.addDelegate(new VotedAnswersDelegate(5, isExtendedPoll));
    }

}
