package es.iessaladillo.pedrojoya.pr012.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class AdapterViewBaseAdapter<T, VH> extends BaseAdapter {

    protected final List<T> data;
    @LayoutRes
    private final int layoutResId;

    @SuppressWarnings("SameParameterValue")
    protected AdapterViewBaseAdapter(@NonNull List<T> data, @LayoutRes int layoutResId) {
        this.data = data;
        this.layoutResId = layoutResId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        VH viewHolder;
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent,
                    false);
            viewHolder = onCreateViewHolder(itemView);
            itemView.setTag(viewHolder);
        } else {
            viewHolder = (VH) itemView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return itemView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract VH onCreateViewHolder(View itemView);

    protected abstract void onBindViewHolder(VH holder, int position);

}
