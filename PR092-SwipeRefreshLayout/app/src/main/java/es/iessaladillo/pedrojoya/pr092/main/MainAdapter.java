package es.iessaladillo.pedrojoya.pr092.main;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.iessaladillo.pedrojoya.pr092.recycleradapter.BaseListAdapter;
import es.iessaladillo.pedrojoya.pr092.recycleradapter.BaseViewHolder;

public class MainAdapter extends BaseListAdapter<String, MainAdapter.ViewHolder> {

    public MainAdapter(List<String> data) {
        super(data);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(itemView, this);
    }

    static class ViewHolder extends BaseViewHolder<String> {

        private final TextView text1;

        ViewHolder(View itemView, MainAdapter adapter) {
            super(itemView, adapter);
            text1 = ViewCompat.requireViewById(itemView, android.R.id.text1);
        }

        @Override
        public void bind(String item) {
            text1.setText(item);
        }
    }

}
