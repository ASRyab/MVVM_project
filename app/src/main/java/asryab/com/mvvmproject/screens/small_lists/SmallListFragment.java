package asryab.com.mvvmproject.screens.small_lists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentSmallItemListBinding;
import asryab.com.mvvmproject.models.FeedStatus;
import asryab.com.mvvmproject.models.ListState;
import asryab.com.mvvmproject.utils.Arguments;
import asryab.com.mvvmproject.viewmodels.small_lists.BaseSmallListVM;
import asryab.com.mvvmproject.viewmodels.small_lists.SmallPetitionsFragmentVM;
import asryab.com.mvvmproject.viewmodels.small_lists.SmallPollsFragmentVM;

public class SmallListFragment extends BaseFragment {

    private FragmentSmallItemListBinding mBindingFragmentPastPolls;
    private BaseSmallListVM mViewModelFragmentPastPolls;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        injectDataBinding(mBindingFragmentPastPolls = FragmentSmallItemListBinding.inflate(inflater, container, false));
        injectViewModel(mViewModelFragmentPastPolls);
        mBindingFragmentPastPolls.setViewModel(mViewModelFragmentPastPolls);

        return mBindingFragmentPastPolls.getRoot();
    }

    @Override
    protected void readFromBundle(Bundle bundle) {
        super.readFromBundle(bundle);
        ListState listState = (ListState) bundle.getSerializable(Arguments.Fragment.ARG_LIST_STATE);
        FeedStatus feedStatus = (FeedStatus) bundle.getSerializable(Arguments.Fragment.ARG_POLLS_STATUS);
        switch (listState){
            case POLLS:
                mViewModelFragmentPastPolls = new SmallPollsFragmentVM(getContext(),feedStatus);
                break;
            case PETITIONS:
                mViewModelFragmentPastPolls = new SmallPetitionsFragmentVM(getContext(),feedStatus);
                break;
        }
    }
}
