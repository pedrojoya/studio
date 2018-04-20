package es.iessaladillo.pedrojoya.pr220.ui.views;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

@SuppressWarnings("unused")
public class RecyclerBindingAdapter<T extends RecyclerBindingAdapter.ViewModel> extends
        ListAdapter<T, RecyclerBindingAdapter.ViewHolder> {

    public interface ViewModel {
        @SuppressWarnings("SameReturnValue")
        int getLayoutId();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Object item, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Object item, int position);
    }

    private final int modelBRId;
    private View emptyView;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;

    protected RecyclerBindingAdapter(int modelBRId, @NonNull DiffCallback<T> diffCallback) {
        super(diffCallback);
        this.modelBRId = modelBRId;
    }

    public Object getItemAtPosition(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        // The itemview type corresponds to layoutId atribute of the model.
        return getItem(position).getLayoutId();
    }

    @NonNull
    @Override
    public RecyclerBindingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), viewType, parent, false);
        View itemView = binding.getRoot();
        ViewHolder viewHolder = new ViewHolder(binding, modelBRId);
        if (onItemClickListener != null) {
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(v, getItem
                    (viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition()));
        }
        if (onItemLongClickListener != null) {
            binding.getRoot().setOnLongClickListener(v -> {
                onItemLongClickListener.onItemLongClick(v, getItem
                        (viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                return true;
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerBindingAdapter.ViewHolder holder, int position) {
        final T model = getItem(position);
        holder.bind(model);
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
    public void setList(List<T> list) {
        if (emptyView != null) {
            checkIfEmpty(list == null ? 0 : list.size());
        }
        super.setList(list);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }


    static public class ViewHolder extends RecyclerView.ViewHolder {


        private final ViewDataBinding binding;
        private final int modelBRId;

        public ViewHolder(ViewDataBinding binding, int modelBRId) {
            super(binding.getRoot()); // Root corresponds to itemview.
            this.binding = binding;
            this.modelBRId = modelBRId;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void bind(Object item) {
            binding.setVariable(modelBRId, item);
            binding.executePendingBindings();
        }

    }

    @BindingAdapter("app:columns")
    public static void setColumns(RecyclerView recyclerView, int numColumns) {
        if (recyclerView != null && numColumns > 0) {
            recyclerView.setHasFixedSize(true);
            if (numColumns == 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                        LinearLayoutManager.VERTICAL, false));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(),
                        numColumns));
            }
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @BindingAdapter("app:dividerItemDecoration")
    public static void setDividerItemDecoration(RecyclerView recyclerView,
            Boolean dividerItemDecoration) {
        if (recyclerView != null && dividerItemDecoration) {
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                    LinearLayoutManager.VERTICAL));
        }
    }

    @BindingAdapter("app:onItemClick")
    public static void onItemClick(RecyclerView recyclerView,
            final RecyclerBindingAdapter.OnItemClickListener action) {
        if (recyclerView != null && recyclerView.getAdapter() instanceof RecyclerBindingAdapter) {
            RecyclerBindingAdapter adapter = (RecyclerBindingAdapter) recyclerView.getAdapter();
            adapter.setOnItemClickListener(action);
        }
    }

    @BindingAdapter("app:onItemLongClick")
    public static void onItemLongClick(RecyclerView recyclerView,
            final RecyclerBindingAdapter.OnItemLongClickListener action) {
        if (recyclerView != null && recyclerView.getAdapter() instanceof RecyclerBindingAdapter) {
            RecyclerBindingAdapter adapter = (RecyclerBindingAdapter) recyclerView.getAdapter();
            adapter.setOnItemLongClickListener(action);
        }
    }

    @BindingAdapter("app:emptyView")
    public static void addEmptyView(RecyclerView recyclerView, View emptyView) {
        if (recyclerView != null && emptyView != null
                && recyclerView.getAdapter() instanceof RecyclerBindingAdapter) {
            RecyclerBindingAdapter adapter = (RecyclerBindingAdapter) recyclerView.getAdapter();
            adapter.setEmptyView(emptyView);
        }
    }

    @BindingAdapter(value = {"app:onSwipedRight", "app:onSwipedLeft"}, requireAll = false)
    public static void setTouchHelper(RecyclerView recyclerView,
            OnSwipedListener onSwipedRight, OnSwipedListener onSwipedLeft) {

        int swipeDirs = 0;
        if (onSwipedRight != null) {
            swipeDirs = swipeDirs | ItemTouchHelper.RIGHT;
        }
        if (onSwipedLeft != null) {
            swipeDirs = swipeDirs | ItemTouchHelper.LEFT;
        }
        if (recyclerView != null && recyclerView.getAdapter() instanceof RecyclerBindingAdapter &&
                (onSwipedRight != null || onSwipedLeft != null)) {
            RecyclerBindingAdapter adapter = (RecyclerBindingAdapter) recyclerView.getAdapter();
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                    new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                            swipeDirs) {

                        @Override
                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                            if (onSwipedLeft != null && direction == ItemTouchHelper.LEFT) {
                                onSwipedLeft.onSwiped(viewHolder, direction, adapter.getItem
                                        (viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                            }
                            if (onSwipedRight != null && direction == ItemTouchHelper.RIGHT) {
                                onSwipedRight.onSwiped(viewHolder, direction, adapter.getItem
                                        (viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                            }
                        }
                    });
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }

    public interface OnSwipedListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, Object item, int position);
    }


}
