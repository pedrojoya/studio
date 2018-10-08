package es.iessaladillo.pedrojoya.pr092.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import es.iessaladillo.pedrojoya.pr092.base.BaseListAdapter;
import es.iessaladillo.pedrojoya.pr092.base.BaseViewHolder;

public class MainAdapter extends BaseListAdapter<String, MainAdapter.ViewHolder> {

    MainAdapter(List<String> data) {
        super(data);
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

    class ViewHolder extends BaseViewHolder {

        private final TextView text1;

        ViewHolder(View itemView) {
            super(itemView, onItemClickListener, onItemLongClickListener);
            text1 = ViewCompat.requireViewById(itemView, android.R.id.text1);
        }

        void bind(String item) {
            text1.setText(item);
        }
    }

}
