package es.iessaladillo.pedrojoya.pr092.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragmentAdapter extends ListAdapter<String, MainFragmentAdapter.ViewHolder> {

    MainFragmentAdapter() {
        super(new DiffUtil.ItemCallback<String>() {
            @Override
            public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return TextUtils.equals(oldItem, newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return TextUtils.equals(oldItem, newItem);
            }
        });
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView text1;

        ViewHolder(View itemView) {
            super(itemView);
            text1 = ViewCompat.requireViewById(itemView, android.R.id.text1);
        }

        void bind(String item) {
            text1.setText(item);
        }

    }

}
