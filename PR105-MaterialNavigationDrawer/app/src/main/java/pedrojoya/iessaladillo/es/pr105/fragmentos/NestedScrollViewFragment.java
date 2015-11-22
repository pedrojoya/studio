package pedrojoya.iessaladillo.es.pr105.fragmentos;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pedrojoya.iessaladillo.es.pr105.R;
import pedrojoya.iessaladillo.es.pr105.utils.HideShowNestedScrollListener;


public class NestedScrollViewFragment extends Fragment {

    private static final String ARG_TITULO = "titulo";

    private NestedScrollView nsvScroll;
    private FloatingActionButton fabAccion;
    private Snackbar mSnackbar;

    private String mTitulo;

    // Retorna una nueva intancia del fragmento.
    public static NestedScrollViewFragment newInstance(String titulo) {
        NestedScrollViewFragment fragment = new NestedScrollViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITULO, titulo);
        fragment.setArguments(args);
        return fragment;
    }

    public NestedScrollViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se obtienen los argumentos.
        if (getArguments() != null) {
            mTitulo = getArguments().getString(ARG_TITULO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nestedscrollview, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            initVistas(getView());
        }
    }

    // Obtiene e inicializa las vistas.
    private void initVistas(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity actividad = ((AppCompatActivity) getActivity());
        actividad.setSupportActionBar(toolbar);
        actividad.setTitle(mTitulo);
        if (actividad.getSupportActionBar() != null) {
            actividad.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            actividad.getSupportActionBar().setHomeButtonEnabled(true);
            actividad.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        nsvScroll = (NestedScrollView) view.findViewById(R.id.nsvScroll);
        HideShowNestedScrollListener mScrollListener = new HideShowNestedScrollListener(nsvScroll) {
            @Override
            public void onShow() {
                if (mSnackbar != null) {
                    mSnackbar.dismiss();
                }
                ViewCompat.animate(fabAccion).translationY(0);
            }

            @Override
            public void onHide() {
                if (mSnackbar != null) {
                    mSnackbar.dismiss();
                }
                int translationY = getResources().getDimensionPixelSize(R
                        .dimen.fab_margin_bottom) + fabAccion.getHeight();
                ViewCompat.animate(fabAccion).translationY(translationY);
            }
        };
        nsvScroll.getViewTreeObserver().addOnScrollChangedListener(mScrollListener);
        fabAccion = (FloatingActionButton) view.findViewById(R.id.fabAccion);
        fabAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSnackbar = Snackbar.make(nsvScroll,
                        getString(R.string.fab_pulsado),
                        Snackbar.LENGTH_SHORT).setAction(getString(R.string.deshacer),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(),
                                        getString(R.string.deshaciendo_accion),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                mSnackbar.show();
            }
        });
    }
}
