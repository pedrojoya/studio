package es.iessaladillo.pedrojoya.pr089.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import es.iessaladillo.pedrojoya.pr089.R;
import es.iessaladillo.pedrojoya.pr089.base.BaseListAdapter;
import es.iessaladillo.pedrojoya.pr089.base.BaseViewHolder;

public class MainFragmentAdapter extends BaseListAdapter<String, MainFragmentAdapter.ViewHolder> {

    MainFragmentAdapter(List<String> data) {
        super(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends BaseViewHolder {

        private final TextView lblName;

        ViewHolder(View itemView) {
            super(itemView, onItemClickListener, onItemLongClickListener);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
        }

        void bind(String student) {
            lblName.setText(student);
        }

    }

}
