package asryab.com.mvvmproject.screens.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.databinding.FragmentSearchBinding;
import asryab.com.mvvmproject.viewmodels.search.SearchVM;

public class SearchFragment extends BaseFragment {

    private FragmentSearchBinding   mBindingSearch;
    private SearchVM mViewModelSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        injectDataBinding(mBindingSearch = FragmentSearchBinding.inflate(inflater, container, false));
        injectViewModel(mViewModelSearch = new SearchVM(getContext(), mBindingSearch, getChildFragmentManager()));
        mBindingSearch.setSearchVM(mViewModelSearch);

        return mBindingSearch.getRoot();
    }
}