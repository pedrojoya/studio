package es.iessaladillo.pedrojoya.pr164.multityperecycleradapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MultiTypeListAdapter<V extends MultiTypeViewHolder>
        extends ListAdapter<Item, V> {

    public MultiTypeListAdapter() {
        super(new DiffUtil.ItemCallback<Item>() {
            @Override
            public boolean areItemsTheSame(Item oldItem, Item newItem) {
                if (oldItem.getLayoutResIdForType() != newItem.getLayoutResIdForType()) {
                    return false;
                }
                return oldItem.areItemsTheSame(newItem);
            }

            @Override
            public boolean areContentsTheSame(Item oldItem, Item newItem) {
                return oldItem.areContentsTheSame(newItem);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getLayoutResIdForType();
    }

    private Item<V> getItemForViewType(@LayoutRes int layoutResId) {
        for (int i = 0; i < getItemCount(); i++) {
            Item item = getItem(i);
            if (item.getLayoutResIdForType() == layoutResId) {
                return item;
            }
        }
        throw new IllegalStateException("Could not find model for view type: " + layoutResId);
    }

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Item<V> item = getItemForViewType(viewType);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                item.getLayoutResIdForType(), parent, false);
        return item.createViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        Item<V> item = getItem(position);
        item.bind(holder, position);
    }

}
