package es.iessaladillo.pedrojoya.pr094;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PaginaFragment extends Fragment {

    public static final String PAR_NUM_PAGINA = "numPagina";
    private static final String PAR_COLOR = "color";

    public static PaginaFragment newInstance(int numPagina, int color) {
        PaginaFragment frg = new PaginaFragment();
        Bundle argumentos = new Bundle();
        argumentos.putInt(PAR_NUM_PAGINA, numPagina);
        argumentos.putInt(PAR_COLOR, color);
        frg.setArguments(argumentos);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pagina, container, false);
        // Se actualiza el TextView.
        TextView lblPagina = (TextView) v.findViewById(R.id.lblPagina);
        lblPagina.setText(getArguments().getInt(PAR_NUM_PAGINA) + "");
        lblPagina.setTextColor(getArguments().getInt(PAR_COLOR));
        // Se retorna la vista.
        return v;
    }

}
