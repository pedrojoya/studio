package es.iessaladillo.pedrojoya.pr241.ui.main;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.iessaladillo.pedrojoya.pr241.R;
import es.iessaladillo.pedrojoya.pr241.utils.recycleradapter.BaseListAdapter;
import es.iessaladillo.pedrojoya.pr241.utils.recycleradapter.BaseViewHolder;

public class MainActivityAdapter extends BaseListAdapter<String, MainActivityAdapter.ViewHolder> {

    public MainActivityAdapter(List<String> data) {
        super(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .activity_main_item, parent, false), this);
    }

    @SuppressWarnings("WeakerAccess")
    static class ViewHolder extends BaseViewHolder<String> {

        private final TextView lblName;

        public ViewHolder(View itemView,
                BaseListAdapter<String, ? extends BaseViewHolder<String>> adapter) {
            super(itemView, adapter);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
        }

        @Override
        public void bind(String item) {
            lblName.setText(item);
        }

    }

}
