package asryab.com.mvvmproject.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BindingHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder  {

    private VB binding;

    public static <VB extends ViewDataBinding> BindingHolder<VB> newInstance(
            @LayoutRes int _layoutId, @NonNull ViewGroup _parent, boolean _attachToParent) {
        final LayoutInflater inflater = LayoutInflater.from(_parent.getContext());
        VB vb = DataBindingUtil.inflate(inflater, _layoutId, _parent, _attachToParent);
        return new BindingHolder<>(vb);
    }

    public BindingHolder(VB _binding) {
        super(_binding.getRoot());
        binding = _binding;
    }

    public BindingHolder(View _view) {
        super(_view);
        binding = DataBindingUtil.bind(_view);
    }

    public VB getBinding() {
        return binding;
    }

}
