package es.iessaladillo.pedrojoya.pr017.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

// M for Model, VH for ViewHolder
public abstract class AdapterViewBaseAdapter<M, VH> extends BaseAdapter implements Filterable {

    private List<M> data;
    private final List<M> original;
    @LayoutRes
    private final int layoutResId;
    private FilterPredicate<M> filterPredicate;

    @SuppressWarnings("SameParameterValue")
    protected AdapterViewBaseAdapter(@NonNull List<M> data, @LayoutRes int layoutResId) {
        this.data = data;
        this.original = data;
        this.layoutResId = layoutResId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        VH viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent,
                    false);
            viewHolder = onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public M getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract VH onCreateViewHolder(View itemView);

    protected abstract void onBindViewHolder(VH holder, int position);

    protected void setFilterPredicate(FilterPredicate<M> filterPredicate) {
        this.filterPredicate = filterPredicate;
    }

    private void submitFilteredList(List<M> filteredList) {
        data = filteredList;
        notifyDataSetChanged();
    }

    protected interface FilterPredicate<T> {
        boolean test(T item, CharSequence constraint);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && filterPredicate != null) {
                    List<M> filtered = new ArrayList<>();
                    for (M element: original) {
                        if (filterPredicate.test(element, constraint)) {
                            filtered.add(element);
                        }
                    }
                    filterResults.values = filtered;
                    filterResults.count = filtered.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    //noinspection unchecked
                    submitFilteredList((List<M>) results.values);
                }
            }
        };
    }


}
