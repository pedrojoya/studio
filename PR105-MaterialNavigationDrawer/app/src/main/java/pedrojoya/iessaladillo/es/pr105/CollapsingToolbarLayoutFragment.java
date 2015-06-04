package pedrojoya.iessaladillo.es.pr105;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class CollapsingToolbarLayoutFragment extends Fragment {

    private static final String ARG_PARAM1 = "opcion";

    private String mOpcion;
    private NestedScrollView nsvScroll;
    private FloatingActionButton fabAccion;
    private Snackbar mSnackbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    public static CollapsingToolbarLayoutFragment newInstance(String param1) {
        CollapsingToolbarLayoutFragment fragment = new CollapsingToolbarLayoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public CollapsingToolbarLayoutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOpcion = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collapsingtoolbarlayout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            initVistas(getView());
        }
    }

    private void initVistas(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity actividad = ((AppCompatActivity) getActivity());
        actividad.setSupportActionBar(toolbar);
        actividad.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actividad.getSupportActionBar().setHomeButtonEnabled(true);
        actividad.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mOpcion);
        nsvScroll = (NestedScrollView) view.findViewById(R.id.nsvScroll);
        fabAccion = (FloatingActionButton) view.findViewById(R.id.fabAccion);
        fabAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSnackbar = Snackbar.make(nsvScroll, "Quillo que", Snackbar.LENGTH_SHORT).setAction("Deshacer", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Quieres deshacer", Toast.LENGTH_SHORT).show();
                    }
                });
                mSnackbar.show();
            }
        });
        //lblTexto.setText(mOpcion);
    }
}
