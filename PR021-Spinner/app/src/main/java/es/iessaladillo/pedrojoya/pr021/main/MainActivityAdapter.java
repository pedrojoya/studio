package es.iessaladillo.pedrojoya.pr021.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr021.R;
import es.iessaladillo.pedrojoya.pr021.data.model.Country;

class MainActivityAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private final ArrayList<Country> mData;

    public MainActivityAdapter(Context contexto, @NonNull ArrayList<Country> data) {
        mData = data;
        mLayoutInflater = LayoutInflater.from(contexto);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_main_item_collapsed, parent,
                    false);
            viewHolder = onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }

    private ViewHolder onCreateViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    private void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bind(mData.get(position));
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_main_item_expanded, parent,
                    false);
            viewHolder = onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }

    static class ViewHolder {

        private final ImageView imgFlag;
        private final TextView lblName;

        public ViewHolder(View itemView) {
            imgFlag = itemView.findViewById(R.id.imgFlag);
            lblName = itemView.findViewById(R.id.lblName);
        }

        public void bind(Country country) {
            imgFlag.setImageResource(country.getFlagResId());
            lblName.setText(country.getName());
        }

    }

}