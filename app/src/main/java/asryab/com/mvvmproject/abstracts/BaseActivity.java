package asryab.com.mvvmproject.abstracts;

import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import asryab.com.mvvmproject.viewmodels.ViewModel;

public abstract class BaseActivity extends AppCompatActivity {

    private ArrayList<ViewModel> injectedModels         = new ArrayList<>();
    private ArrayList<ViewDataBinding> injectedBindings = new ArrayList<>();

    protected void injectViewModel(final ViewModel viewModel) {
        injectedModels.add(viewModel);
    }

    protected void injectDataBinding(final ViewDataBinding injectedBinding) {
        injectedBindings.add(injectedBinding);
    }

    @Override
    protected void onDestroy() {
        for (ViewDataBinding viewDataBinding: injectedBindings) {
            viewDataBinding.unbind();
        }
        for (ViewModel viewModel: injectedModels) {
            viewModel.onDestroy();
        }

        injectedBindings    .clear();
        injectedModels      .clear();

        super.onDestroy();
    }
}
