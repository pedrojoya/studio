package pedrojoya.iessaladillo.es.pr202;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class MainFragment extends Fragment implements AlumnosAdapter
        .OnItemClickListener {

    private static final long DELAY = 5000;

    private RecyclerView lstAlumnos;
    private TextView mEmptyView;
    private AlumnosAdapter mAdaptador;
    private ProgressBar progressBar;
    @SuppressWarnings("CanBeFinal")
    private ArrayList<Alumno> mData = new ArrayList<>();
    private boolean mIsLoading = false;
    private int mCurrentPage;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            initVistas(getView());
        }
    }

    // Obtiene e inicializa las vistas.
    private void initVistas(View view) {
        configToolbar(view);
        configRecyclerView(view);
    }

    // Configura la Toolbar.
    private void configToolbar(View view) {
        Toolbar toolbar = (Toolbar) ViewCompat.requireViewById(view, R.id.toolbar);
        AppCompatActivity actividad = (AppCompatActivity) requireActivity();
        actividad.setSupportActionBar(toolbar);
        if (actividad.getSupportActionBar() != null) {
            actividad.getSupportActionBar().setHomeButtonEnabled(true);
            actividad.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    // Configura el RecyclerView.
    private void configRecyclerView(View view) {
        if (mAdaptador == null) {
            mAdaptador = new AlumnosAdapter(mData);
            mAdaptador.setOnItemClickListener(this);
        }
        mAdaptador.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkAdapterIsEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkAdapterIsEmpty();
            }
        });
        lstAlumnos = (RecyclerView) ViewCompat.requireViewById(view, R.id.lstAlumnos);
        mEmptyView = (TextView) ViewCompat.requireViewById(view, R.id.lblNoHayAlumnos);
        if (lstAlumnos != null) {
            lstAlumnos.setHasFixedSize(true);
            lstAlumnos.setAdapter(mAdaptador);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireActivity(),
                    LinearLayoutManager.VERTICAL, false);
            lstAlumnos.setLayoutManager(mLayoutManager);
            lstAlumnos.setItemAnimator(new DefaultItemAnimator());
            lstAlumnos.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
                @Override
                protected void loadMoreItems() {
                    mIsLoading = true;
                    mAdaptador.addLoadingFooter();
                    // Se obtienen los nuevos datos.
                    loadNextPage();
                }

                @Override
                public boolean isLoading() {
                    return mIsLoading;
                }
            });
            checkAdapterIsEmpty();
            progressBar = (ProgressBar) ViewCompat.requireViewById(view, R.id.progressBar);
            if (mIsLoading) {
                progressBar.setVisibility(View.VISIBLE);
            }
            if (mAdaptador.getItemCount() == 0 && !mIsLoading) {
                loadFirstPage();
            }
        }
    }

    private void loadFirstPage() {
        mIsLoading = true;
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                mAdaptador.addAll(DB.getInstance().getPage(mCurrentPage));
                mIsLoading = false;
            }
        }, DELAY);
    }

    private void loadNextPage() {
        // Se simula que se tarda un tiempo.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Se obtienen los nuevos datos.
                ArrayList<Alumno> nuevos = DB.getInstance().getPage(mCurrentPage + 1);
                // Se elimina el elemento de loading del adaptador.
                mAdaptador.removeLoadingFooter();
                // Se indica que ya no se está cargando.
                mIsLoading = false;
                // Se añaden los nuevos datos al adaptador.
                mAdaptador.addAll(nuevos);
                mCurrentPage++;
            }
        }, DELAY);
    }

    // Muestra u oculta la empty view dependiendo de si el adaptador está vacío.
    private void checkAdapterIsEmpty() {
        mEmptyView.setVisibility(mAdaptador.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    // Cuando se hace click sobre un elemento de la lista.
    @Override
    public void onItemClick(View view, pedrojoya.iessaladillo.es.pr202.Alumno alumno,
                            int position) {
        Snackbar.make(lstAlumnos, getString(R.string.ha_pulsado_sobre, alumno.getNombre()),
                Snackbar.LENGTH_SHORT).show();
    }

}
