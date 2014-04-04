package es.iessaladillo.pedrojoya.pr094;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PaginaFragment extends Fragment {

    public static final String PAR_NUM_PAGINA = "numPagina";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Se infla el layout del fragmento y se muestra el número de página en
        // el TextView.
        View v = inflater.inflate(R.layout.fragment_pagina, container, false);
        TextView lblPagina = (TextView) v.findViewById(R.id.lblPagina);
        lblPagina.setText(this.getArguments().getInt(PAR_NUM_PAGINA) + "");
        return v;
    }

}
