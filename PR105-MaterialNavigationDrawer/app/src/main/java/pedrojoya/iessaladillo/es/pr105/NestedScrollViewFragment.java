package pedrojoya.iessaladillo.es.pr105;


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
import android.widget.TextView;
import android.widget.Toast;


public class NestedScrollViewFragment extends Fragment {

    private static final String ARG_PARAM1 = "opcion";

    private String mOpcion;
    private TextView lblTexto;
    private NestedScrollView nsvScroll;
    private FloatingActionButton fabAccion;
    private HideShowNestedScrollListener mScrollListener;
    private Snackbar mSnackbar;

    public static NestedScrollViewFragment newInstance(String param1) {
        NestedScrollViewFragment fragment = new NestedScrollViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public NestedScrollViewFragment() {
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
        return inflater.inflate(R.layout.fragment_nestedscrollview, container, false);
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
        lblTexto = (TextView) view.findViewById(R.id.lblTexto);
        nsvScroll = (NestedScrollView) view.findViewById(R.id.nsvScroll);
        mScrollListener = new HideShowNestedScrollListener(nsvScroll) {
            @Override
            public void showVistas() {
                if (mSnackbar != null) {
                    mSnackbar.dismiss();
                }
                ViewCompat.animate(fabAccion).translationY(0);
            }

            @Override
            public void hideVistas() {
                if (mSnackbar != null) {
                    mSnackbar.dismiss();
                }
                int translationY = getResources().getDimensionPixelSize(R
                        .dimen.fab_margin) + fabAccion.getHeight();
                ViewCompat.animate(fabAccion).translationY(translationY);
            }
        };
        nsvScroll.getViewTreeObserver().addOnScrollChangedListener(mScrollListener);
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
