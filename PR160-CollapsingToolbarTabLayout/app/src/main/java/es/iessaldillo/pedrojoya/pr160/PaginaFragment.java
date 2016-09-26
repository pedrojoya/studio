package es.iessaldillo.pedrojoya.pr160;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PaginaFragment extends Fragment {

    private static final String ARG_NUMERO_PAGINA = "numero_pagina";
    private static final String STATE_TEXTO = "state_texto";

    private TextView lblTexto;
    private String mTexto;

    public PaginaFragment() {
    }

    // Retorna el fragmento configurado. Recibe el número de página.
    public static PaginaFragment newInstance(int numeroPagina) {
        PaginaFragment fragment = new PaginaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMERO_PAGINA, numeroPagina);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pagina, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Se escribe el texto adecuado.
        if (savedInstanceState != null) {
            mTexto = savedInstanceState.getString(STATE_TEXTO);
        } else {
            mTexto = getString(R.string.numero_pagina, getArguments().getInt(ARG_NUMERO_PAGINA));
        }
        if (getView() != null) {
            lblTexto = (TextView) getView().findViewById(R.id.lblTexto);
            lblTexto.setText(mTexto);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se almacena el texto mostrado.
        outState.putString(STATE_TEXTO, mTexto);
    }

    // Cuando se hace click sobre el fab estando en dicho fragmento.
    public void fabOnClick(View view) {
        mTexto = getString(R.string.has_pulsado_sobre_el_fab, lblTexto.getText());
        lblTexto.setText(mTexto);
        Snackbar.make(view, mTexto, Snackbar.LENGTH_LONG).show();
    }

}
