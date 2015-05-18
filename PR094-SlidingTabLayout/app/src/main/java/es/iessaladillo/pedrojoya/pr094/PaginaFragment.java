package es.iessaladillo.pedrojoya.pr094;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

public class PaginaFragment extends Fragment {

    private static final String PAR_NUM_PAGINA = "numPagina";
    private static final String PAR_COLOR = "color";
    private static final int UMBRAL_SCROLL = 20;
    private TextView lblPagina;
    private ScrollView svScrollView;
    private int mScrollAnterior;
    private boolean mVistasOcultas;
    private HideShowInterface mListener;

    public interface HideShowInterface {
        public void onHide();
        public void onShow();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (HideShowInterface) activity;
        } catch (Exception e) {
            Log.e(getString(R.string.app_name),
                    "La actividad debe implementar la interfaz del fragmento");
        }
    }

    public static PaginaFragment newInstance(int numPagina, int color) {
        PaginaFragment frg = new PaginaFragment();
        Bundle argumentos = new Bundle();
        argumentos.putInt(PAR_NUM_PAGINA, numPagina);
        argumentos.putInt(PAR_COLOR, color);
        frg.setArguments(argumentos);
        return frg;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            initVistas(getView());
        }
    }

    private void initVistas(View v) {
        // Se actualiza el TextView.
        lblPagina = (TextView) v.findViewById(R.id.lblPagina);
        //lblPagina.setText(getArguments().getInt(PAR_NUM_PAGINA) + "");
        lblPagina.setTextColor(getArguments().getInt(PAR_COLOR));
        svScrollView = (ScrollView) v.findViewById(R.id.svScrollView);
        svScrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver
                        .OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        int diferencia = svScrollView.getScrollY() - mScrollAnterior;
                        if (mVistasOcultas && diferencia < -UMBRAL_SCROLL) {
                            mVistasOcultas = false;
                            showVistas();
                        } else if (!mVistasOcultas && diferencia > UMBRAL_SCROLL) {
                            mVistasOcultas = true;
                            hideVistas();
                        }
                        mScrollAnterior = svScrollView.getScrollY();
                    }
                });

    }

    private void hideVistas() {
        mListener.onHide();
    }

    private void showVistas() {
        mListener.onShow();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pagina, container, false);
    }

}
