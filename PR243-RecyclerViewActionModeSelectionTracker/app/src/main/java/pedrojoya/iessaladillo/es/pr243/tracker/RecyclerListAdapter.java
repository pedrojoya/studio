package pedrojoya.iessaladillo.es.pr243.tracker;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;

public abstract class RecyclerListAdapter<M extends Parcelable,
        V extends RecyclerListAdapter.ViewHolder>
        extends ListAdapter<M, V> {

    private List<M> list;
    private SelectionTracker selectionTracker;

    public interface OnItemClickListener<M> {
        void onItemClick(RecyclerView.Adapter<?> adapter, View view, M item,
                int position, long id);
    }

    private OnItemClickListener<M> onItemClickListener;
    private View emptyView;
    private final RecyclerView.AdapterDataObserver adapterDataObserver =
            new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    checkEmptyViewVisibility();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    checkEmptyViewVisibility();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    checkEmptyViewVisibility();
                }
            };

    public RecyclerListAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback, RecyclerView
            recyclerView, Class<M> modelClass) {
        super(diffUtilItemCallback);
        selectionTracker = new SelectionTracker.Builder<M>("my-selection-id", recyclerView,
                new ItemKeyProvider<M>(ItemKeyProvider.SCOPE_CACHED) {
                    @Nullable
                    @Override
                    public M getKey(int position) {
                        return getItem(position);
                    }

                    @Override
                    public int getPosition(@NonNull M key) {
                        return getPositionForItem(key);
                    }
                },
                new ItemDetailsLookup<M>() {
                    @Nullable
                    @Override
                    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
                        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (view != null) {
                            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                            if (viewHolder instanceof RecyclerListAdapter.ViewHolder) {
                                return ((RecyclerListAdapter.ViewHolder) viewHolder).getItemDetails();
                            }
                        }
                        return null;
                    }
                },
                StorageStrategy.createParcelableStorage(modelClass))
                .build();
    }

    public void setOnItemClickListener(OnItemClickListener<M> listener) {
        this.onItemClickListener = listener;
    }

    public void setEmptyView(@NonNull View emptyView) {
        if (this.emptyView != null) {
            unregisterAdapterDataObserver(adapterDataObserver);
        }
        this.emptyView = emptyView;
        registerAdapterDataObserver(adapterDataObserver);
        checkEmptyViewVisibility();
    }

    private void checkEmptyViewVisibility() {
        if (emptyView != null) {
            emptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE :
                                    View.INVISIBLE);
        }
    }

    @Override
    public M getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public void submitList(@Nullable List<M> list) {
        this.list = list;
        super.submitList(list);
    }

    public int getPositionForItem(M item) {
        return list.indexOf(item);
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        M item = getItem(position);
        holder.bind(item, selectionTracker.isSelected(item));
    }

    public SelectionTracker getSelectionTracker() {
        return selectionTracker;
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            if (onItemClickListener != null) {
                itemView.setOnClickListener(
                        v -> onItemClickListener.onItemClick(
                                RecyclerListAdapter.this, v,
                                getItem(getAdapterPosition()),
                                getAdapterPosition(), getItemId()));
            }
        }

        public ItemDetailsLookup.ItemDetails getItemDetails() {
            return new ItemDetailsLookup.ItemDetails<M>() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }

                @Nullable
                @Override
                public M getSelectionKey() {
                    return getItem(getAdapterPosition());
                }
            };
        }

        public abstract void bind(M item, boolean isActive);

    }

}