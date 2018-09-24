package es.iessaladillo.pedrojoya.pr059.base;

import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

// M for Model, VH for ViewHolder, K for selection tracker Key.
abstract public class BaseListAdapter<M, VH extends BaseViewHolder> extends ListAdapter<M, VH>
        implements Filterable {

    private View emptyView;
    protected OnItemClickListener onItemClickListener;
    private List<M> original;
    private FilterPredicate<M> filterPredicate;

    @SuppressWarnings("SameParameterValue")
    protected BaseListAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
    }

    @Override
    public void submitList(List<M> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        original = list;
        super.submitList(list);
    }

    private void submitFilteredList(List<M> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        super.submitList(list);
    }

    public void setEmptyView(@NonNull View emptyView) {
        this.emptyView = emptyView;
        checkEmptyViewVisibility(getItemCount());
    }

    private void checkEmptyViewVisibility(int size) {
        if (emptyView != null) {
            emptyView.setVisibility(size == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public M getItem(int position) {
        return super.getItem(position);
    }

    @SuppressWarnings("unused")
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected interface FilterPredicate<T> {
        boolean test(T item, CharSequence constraint);
    }

    protected void setFilterPredicate(FilterPredicate<M> filterPredicate) {
        this.filterPredicate = filterPredicate;
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
                if (results != null) {
                    //noinspection unchecked
                    submitFilteredList((List<M>) results.values);
                }
            }
        };
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

}