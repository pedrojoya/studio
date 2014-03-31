package es.iessaladillo.pedrojoya.pr092;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

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

        private SwipeRefreshLayout swlPanel;
        private SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss");
        private ListView lstLista;
        private ArrayAdapter<String> adaptador;
        private MenuItem mnuActualizar;

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
            // El fragmento aportar� �tems a la action bar.
            setHasOptionsMenu(true);
            // El fragmento actuar� como listener del gesto de swipe.
            swlPanel.setOnRefreshListener(this);
            // Se establecen los colores que debe usar la animaci�n.
            swlPanel.setColorScheme(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            // Se carga la lista inicialmente.
            ArrayList<String> datos = new ArrayList<String>();
            datos.add(formateador.format(new Date()));
            adaptador = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, datos);
            lstLista.setAdapter(adaptador);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.fragment_main, menu);
            mnuActualizar = menu.findItem(R.id.mnuActualizar);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.mnuActualizar) {
                refrescar();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onRefresh() {
            refrescar();
        }

        private void refrescar() {
            // Se activa el c�rculo de progreso y la animaci�n.
            mnuActualizar.setActionView(R.layout.actionview_progreso);
            swlPanel.setRefreshing(true);
            // Se simula que la tarea de carga tarda 2 segundos.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Se a�aden los datos al adaptador.
                    adaptador.add(formateador.format(new Date()));
                    adaptador.notifyDataSetChanged();
                    // Se cancela la animaci�n del panel.
                    swlPanel.setRefreshing(false);
                    // Se desactiva el c�rculo de progreso.
                    mnuActualizar.setActionView(null);
                }
            }, 2000);
        }
    }

}
