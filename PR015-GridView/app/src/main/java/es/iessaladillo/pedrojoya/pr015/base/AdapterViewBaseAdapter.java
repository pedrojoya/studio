package es.iessaladillo.pedrojoya.pr015.base;

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
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent,
                    false);
            viewHolder = onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }

    protected abstract VH onCreateViewHolder(View itemView);

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract void onBindViewHolder(VH holder, int position);

}
