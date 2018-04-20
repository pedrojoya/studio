package pedrojoya.iessaladillo.es.pr232.recycleradapter;

import android.support.v7.util.DiffUtil;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

// V is ViewModel type, M is Model type.
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class FilterableListAdapter<M, V extends BaseViewHolder<M>> extends
        BaseListAdapter<M, V> implements Filterable {

    private List<M> dataList;
    private List<M> filteredList;

    public FilterableListAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
    }

    @Override
    public void submitList(List<M> list) {
        dataList = list;
        filteredList = list;
        super.submitList(list);
    }

    private void updateList(List<M> list) {
        super.submitList(list);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString();
                if (filterString.isEmpty()) {
                    filteredList = dataList;
                } else {
                    List<M> list = new ArrayList<>();
                    for (M item : dataList) {
                        if (includeInFilter(item, filterString)) {
                            list.add(item);
                        }

                    }
                    filteredList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //noinspection unchecked
                filteredList = (List<M>) results.values;
                updateList(filteredList);
            }
        };
    }

    public abstract boolean includeInFilter(M item, String filterString);

}
