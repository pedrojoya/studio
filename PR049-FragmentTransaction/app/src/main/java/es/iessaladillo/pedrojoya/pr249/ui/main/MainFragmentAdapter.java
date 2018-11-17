package es.iessaladillo.pedrojoya.pr249.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.DiffUtil;
import es.iessaladillo.pedrojoya.pr249.base.BaseListAdapter;
import es.iessaladillo.pedrojoya.pr249.base.BaseViewHolder;
import es.iessaladillo.pedrojoya.pr249.base.PositionalDetailsLookup;
import es.iessaladillo.pedrojoya.pr249.base.PositionalItemDetails;

public class MainFragmentAdapter extends BaseListAdapter<String, MainFragmentAdapter.ViewHolder, Long> {

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
    @LayoutRes
    private final int layoutResId;

    MainFragmentAdapter(@LayoutRes int layoutResId) {
        super(diffUtilItemCallback);
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public MainFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
            int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(layoutResId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainFragmentAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(getItem(position), selectionTracker != null && selectionTracker
                .isSelected((long) position));
    }

    class ViewHolder extends BaseViewHolder implements PositionalDetailsLookup.DetailsProvider {

        private final TextView text1;

        ViewHolder(@NonNull View itemView) {
            super(itemView, onItemClickListener);
            text1 = ViewCompat.requireViewById(itemView, android.R.id.text1);
        }

        @Override
        public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
            return new PositionalItemDetails(getAdapterPosition());
        }

        void bind(String item, boolean selected) {
            itemView.setActivated(selected);
            text1.setText(item);
        }
    }

}
