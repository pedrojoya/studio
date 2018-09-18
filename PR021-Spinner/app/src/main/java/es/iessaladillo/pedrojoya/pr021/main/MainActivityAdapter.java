package es.iessaladillo.pedrojoya.pr021.main;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.iessaladillo.pedrojoya.pr021.R;
import es.iessaladillo.pedrojoya.pr021.base.DropDownBaseAdapter;
import es.iessaladillo.pedrojoya.pr021.data.local.model.Country;

class MainActivityAdapter extends DropDownBaseAdapter<Country, MainActivityAdapter.ViewHolder, MainActivityAdapter.ViewHolder> {

    MainActivityAdapter(@NonNull List<Country> data) {
        super(data, R.layout.activity_main_item_collapsed, R.layout.activity_main_item_expanded);
    }

    @Override
    protected ViewHolder onCreateCollapsedViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindCollapsedViewHolder(ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    protected ViewHolder onCreateExpandedViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindExpandedViewHolder(ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }


    static class ViewHolder {

        private final ImageView imgFlag;
        private final TextView lblName;

        ViewHolder(View itemView) {
            imgFlag = ViewCompat.requireViewById(itemView, R.id.imgFlag);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
        }

        void bind(Country country) {
            imgFlag.setImageResource(country.getFlagResId());
            lblName.setText(country.getName());
        }

    }

}