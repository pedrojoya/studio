package es.iessaladillo.pedrojoya.pr059.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import es.iessaladillo.pedrojoya.pr059.base.BaseListAdapter;
import es.iessaladillo.pedrojoya.pr059.base.BaseViewHolder;

public class MainFragmentAdapter extends BaseListAdapter<String, MainFragmentAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<String> diffUtilItemCallback = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return TextUtils.equals(oldItem, newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return TextUtils.equals(oldItem, newItem);
        }
    };

    MainFragmentAdapter() {
        super(diffUtilItemCallback);
        setFilterPredicate((item, constraint) -> item.toUpperCase()
                .contains(constraint.toString().toUpperCase()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends BaseViewHolder {

        private final TextView text1;

        ViewHolder(@NonNull View itemView) {
            super(itemView, onItemClickListener);
            text1 = ViewCompat.requireViewById(itemView, android.R.id.text1);
        }

        void bind(String student) {
            text1.setText(student);
        }

    }

}
