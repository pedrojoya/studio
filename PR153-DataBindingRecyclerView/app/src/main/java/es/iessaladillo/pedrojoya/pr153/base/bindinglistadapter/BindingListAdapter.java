package es.iessaladillo.pedrojoya.pr153.base.bindinglistadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

// M is model.
@SuppressWarnings("unused")
public class BindingListAdapter<M extends ItemViewType> extends
        ListAdapter<M, BindingViewHolder> {


    private final int modelBRId;
    private View emptyView;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;

    protected BindingListAdapter(int modelBRId, @NonNull DiffUtil.ItemCallback<M> diffCallback) {
        super(diffCallback);
        this.modelBRId = modelBRId;
    }

    @Override
    public int getItemViewType(int position) {
        // The itemview type corresponds to layoutId atribute of the model.
        return getItem(position).getLayoutId();
    }

    @Override
    public M getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), viewType, parent, false);
        View itemView = binding.getRoot();
        BindingViewHolder bindingViewHolder = new BindingViewHolder(binding, modelBRId);
        bindingViewHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getItem(bindingViewHolder.getAdapterPosition()),
                        bindingViewHolder.getAdapterPosition());
            }
        });
        bindingViewHolder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(v,
                        getItem(bindingViewHolder.getAdapterPosition()), bindingViewHolder.getAdapterPosition());
                return true;
            } else {
                return false;
            }
        });
        return bindingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private void checkIfEmpty(int itemCount) {
        if (emptyView != null) {
            emptyView.setVisibility(itemCount > 0 ? View.GONE : View.VISIBLE);
        }
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty(getItemCount());
    }

    @Override
    public void submitList(List<M> list) {
        if (emptyView != null) {
            checkIfEmpty(list == null ? 0 : list.size());
        }
        super.submitList(list);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

}
