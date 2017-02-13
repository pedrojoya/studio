package pedrojoya.iessaladillo.es.pr105.fragmentos;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pedrojoya.iessaladillo.es.pr105.R;
import pedrojoya.iessaladillo.es.pr105.adaptadores.AlumnosAdapter;
import pedrojoya.iessaladillo.es.pr105.data.Alumno;
import pedrojoya.iessaladillo.es.pr105.data.DB;
import pedrojoya.iessaladillo.es.pr105.utils.HideShowRecyclerScrollListener;


public class Tab1Fragment extends Fragment implements AlumnosAdapter.OnItemClickListener {

    private RecyclerView lstAlumnos;
    private FloatingActionButton fabAccion;
    private Snackbar mSnackbar;

    // Retorna una nueva intancia del fragmento.
    public static Tab1Fragment newInstance() {
        return new Tab1Fragment();
    }

    public Tab1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab1, container, false);
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
        lstAlumnos = (RecyclerView) view.findViewById(R.id.lstAlumnos);
        fabAccion = (FloatingActionButton) getActivity().findViewById(R.id.fabAccion);
        configRecyclerView();
        configFab();
    }

    // Configura el RecyclerView.
    private void configRecyclerView() {
        lstAlumnos.setHasFixedSize(true);
        final AlumnosAdapter mAdaptador = new AlumnosAdapter(DB.getAlumnos());
        mAdaptador.setOnItemClickListener(this);
        lstAlumnos.setAdapter(mAdaptador);
        lstAlumnos.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        // Hide & Show FAB.
        lstAlumnos.addOnScrollListener(new HideShowRecyclerScrollListener() {
            @Override
            public void onHide() {
                if (mSnackbar != null) {
                    mSnackbar.dismiss();
                }
                //                int translationY = getResources().getDimensionPixelSize(R
                //                        .dimen.fab_margin) + fabAccion.getHeight();
                //                ViewCompat.animate(fabAccion).translationY(translationY);
                fabAccion.hide();
            }

            @Override
            public void onShow() {
                if (mSnackbar != null) {
                    mSnackbar.dismiss();
                }
                //                ViewCompat.animate(fabAccion).translationY(0);
                fabAccion.show();
            }
        });
        // Drag & drop y Swipe to dismiss.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        mAdaptador.swapItems(fromPos, toPos);
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        // Se elimina el elemento.
                        mAdaptador.removeItem(viewHolder.getAdapterPosition());
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstAlumnos);
    }

    // Configura el FAB.
    private void configFab() {
        fabAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSnackbar = Snackbar.make(lstAlumnos, getString(R.string.fab_pulsado),
                        Snackbar.LENGTH_SHORT).setAction(getString(R.string.deshacer),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(),
                                        getString(R.string.deshaciendo_accion), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                mSnackbar.show();
            }
        });
    }

    // Cuando se hace click sobre un elemento de la lista.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        Toast.makeText(getActivity(),
                getString(R.string.ha_pulsado_sobre) + " " + alumno.getNombre(), Toast.LENGTH_SHORT)
                .show();
    }

}
