package pedrojoya.iessaladillo.es.pr105;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class Tab1Fragment extends Fragment implements AlumnosAdapter.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private RecyclerView lstAlumnos;
    private AlumnosAdapter mAdaptador;
    private FloatingActionButton fabAccion;
    private Snackbar mSnackbar;

    public static Tab1Fragment newInstance(String param1) {
        Tab1Fragment fragment = new Tab1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public Tab1Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            initVistas(getView());
        }
    }

    private void initVistas(View view) {
        lstAlumnos = (RecyclerView) view.findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(DB.getAlumnos());
        mAdaptador.setOnItemClickListener(this);
        lstAlumnos.setAdapter(mAdaptador);
        lstAlumnos.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                        false));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        lstAlumnos.addOnScrollListener(new HideShowRecyclerScrollListener() {
            @Override
            public void onHide() {
                if (mSnackbar != null) {
                    mSnackbar.dismiss();
                }
                int translationY = getResources().getDimensionPixelSize(R
                        .dimen.fab_margin) + fabAccion.getHeight();
                ViewCompat.animate(fabAccion).translationY(translationY);
            }

            @Override
            public void onShow() {
                if (mSnackbar != null) {
                    mSnackbar.dismiss();
                }
                ViewCompat.animate(fabAccion).translationY(0);
            }
        });
        fabAccion = (FloatingActionButton) getActivity().findViewById(R.id.fabAccion);
        //fabAccion.setTranslationY(-getResources().getDimensionPixelOffset(R
        //        .dimen.fab_translationY));
        fabAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSnackbar = Snackbar.make(lstAlumnos, "Quillo que", Snackbar
                        .LENGTH_SHORT).setAction("Deshacer", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Quieres deshacer", Toast.LENGTH_SHORT).show();
                    }
                });
                mSnackbar.show();
            }
        });
    }

    // Cuando se hace click sobre un elemento de la lista.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        Toast.makeText(getActivity(), getString(R.string.ha_pulsado_sobre) +
                        " " + alumno.getNombre(), Toast.LENGTH_SHORT).show();
    }

}
