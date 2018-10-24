package es.iessaladillo.pedrojoya.pr021.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

// T for Model, CVH for Collapsed ViewHolder, EVH for Expanded ViewHolder
public abstract class DropDownBaseAdapter<T, CVH, EVH> extends BaseAdapter {

    @NonNull
    protected final List<T> data;
    @LayoutRes
    private final int collapsedLayoutResId;
    private final int expandedLayoutResId;

    @SuppressWarnings("SameParameterValue")
    protected DropDownBaseAdapter(@NonNull List<T> data, @LayoutRes int collapsedLayoutResId,
            @LayoutRes int expandedLayoutResId) {
        this.data = data;
        this.collapsedLayoutResId = collapsedLayoutResId;
        this.expandedLayoutResId = expandedLayoutResId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        CVH viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(collapsedLayoutResId, parent,
                    false);
            viewHolder = onCreateCollapsedViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CVH) convertView.getTag();
        }
        onBindCollapsedViewHolder(viewHolder, position);
        return convertView;
    }

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

    protected abstract CVH onCreateCollapsedViewHolder(@NonNull View itemView);

    protected abstract void onBindCollapsedViewHolder(@NonNull CVH holder, int position);

    @SuppressWarnings("unchecked")
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        EVH viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(expandedLayoutResId,
                    parent,
                    false);
            viewHolder = onCreateExpandedViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EVH) convertView.getTag();
        }
        onBindExpandedViewHolder(viewHolder, position);
        return convertView;
    }

    protected abstract EVH onCreateExpandedViewHolder(@NonNull View itemView);

    protected abstract void onBindExpandedViewHolder(@NonNull EVH holder, int position);

}

