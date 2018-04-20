package es.iessaladillo.pedrojoya.pr164.ui.main;

import android.view.View;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr164.R;
import es.iessaladillo.pedrojoya.pr164.multityperecycleradapter.MultiTypeViewHolder;

public class StudentViewHolder extends MultiTypeViewHolder {

    public final TextView lblNombre;
    public final TextView lblCurso;

    public StudentViewHolder(View itemView) {
        super(itemView);
        lblNombre = ViewCompat.requireViewById(itemView, R.id.lblNombre);
        lblCurso = ViewCompat.requireViewById(itemView, R.id.lblCurso);
    }

}
