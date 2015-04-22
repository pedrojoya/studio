package es.iessaladillo.pedrojoya.pr092;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se carga el fragmento principal.
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment()).commit();
        }
    }

    // Fragmento principal
    public static class MainFragment extends Fragment implements
            OnRefreshListener {

        private static final long MILISEGUNDOS_ESPERA = 5000;
        private SwipeRefreshLayout swlPanel;
        private final SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        private ListView lstLista;
        private ArrayAdapter<String> adaptador;

        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            swlPanel = (SwipeRefreshLayout) rootView
                    .findViewById(R.id.swlPanel);
            lstLista = (ListView) rootView.findViewById(R.id.lstLista);
            return rootView;

        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            // El fragmento aportará ítems a la action bar.
            setHasOptionsMenu(true);
            // El fragmento actuará como listener del gesto de swipe.
            swlPanel.setOnRefreshListener(this);
            // Se establecen los colores que debe usar la animación.
            swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            // Se carga la lista inicialmente.
            ArrayList<String> datos = new ArrayList<>();
            datos.add(formateador.format(new Date()));
            adaptador = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, datos);
            lstLista.setAdapter(adaptador);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onRefresh() {
            refrescar();
        }

        private void refrescar() {
            // Se activa la animación.
            swlPanel.setRefreshing(true);
            // Se simula que la tarea de carga tarda unos segundos.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Se añaden los datos al adaptador.
                    adaptador.add(formateador.format(new Date()));
                    adaptador.notifyDataSetChanged();
                    // Se cancela la animación del panel.
                    swlPanel.setRefreshing(false);
                }
            }, MILISEGUNDOS_ESPERA);
        }
    }

}
