package es.iessaladillo.pedrojoya.pr164.ui.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr164.R;
import es.iessaladillo.pedrojoya.pr164.multityperecycleradapter.MultiTypeViewHolder;

public class GroupViewHolder extends MultiTypeViewHolder {

    public final TextView lblName;
    public final ImageView imgCollapse;

    public GroupViewHolder(View itemView) {
        super(itemView);
        lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
        imgCollapse = ViewCompat.requireViewById(itemView, R.id.imgCollapse);
    }

}
