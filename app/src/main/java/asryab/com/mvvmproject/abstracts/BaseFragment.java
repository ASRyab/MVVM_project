package asryab.com.mvvmproject.abstracts;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import asryab.com.mvvmproject.utils.KeyboardUtils;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public abstract class BaseFragment extends Fragment {

    private ArrayList<ViewModel> injectedModels         = new ArrayList<>();
    private ArrayList<ViewDataBinding> injectedBindings = new ArrayList<>();

    private Bundle receivedBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        readFromBundle(receivedBundle = getArguments());
        super.onCreate(savedInstanceState);
    }

    protected void onBackPressed() {
        getActivity().onBackPressed();
    }

    protected void injectViewModel(final ViewModel viewModel) {
        injectedModels.add(viewModel);
    }

    protected void injectDataBinding(final ViewDataBinding injectedBinding) {
        injectedBindings.add(injectedBinding);
    }

    protected void readFromBundle(final Bundle bundle){}

    @Override
    public void onDestroyView() {
        KeyboardUtils.hideSoftKeyboard(getActivity());

        for (ViewDataBinding viewDataBinding: injectedBindings) {
            viewDataBinding.unbind();
        }
        for (ViewModel viewModel: injectedModels) {
            viewModel.onDestroy();
        }

        injectedBindings    .clear();
        injectedModels      .clear();
        super.onDestroyView();
    }

    public <T extends BaseFragment> T withArgs(final Bundle bundle) {
        setArguments(bundle);
        return (T) this;
    }
}
