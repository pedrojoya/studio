package es.iessaladillo.pedrojoya.pr153.base.bindinglistadapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;

public class BindingViewHolder extends RecyclerView.ViewHolder {


    private final ViewDataBinding binding;
    private final int modelBRId;

    public BindingViewHolder(ViewDataBinding binding, int modelBRId) {
        super(binding.getRoot()); // Root corresponds to itemview.
        this.binding = binding;
        this.modelBRId = modelBRId;
    }

    @SuppressWarnings("unused")
    public ViewDataBinding getBinding() {
        return binding;
    }

    @CallSuper
    public void bind(Object item) {
        binding.setVariable(modelBRId, item);
        binding.executePendingBindings();
    }

}
