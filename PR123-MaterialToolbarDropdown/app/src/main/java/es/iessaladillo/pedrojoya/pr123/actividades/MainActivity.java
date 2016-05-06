package es.iessaladillo.pedrojoya.pr123.actividades;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import es.iessaladillo.pedrojoya.pr123.R;
import es.iessaladillo.pedrojoya.pr123.fragmentos.FotoFragment;
import es.iessaladillo.pedrojoya.pr123.fragmentos.InfoFragment;
import es.iessaladillo.pedrojoya.pr123.utils.ToolbarSpinnerAdapter;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    // Constantes.
    private static final int POS_OPCION_FOTO = 0;
    private static final int POS_OPCION_INFO = 1;
    private static final String TAG_FRG_FOTO = "fotoFragment";
    private static final String TAG_FRG_INFO = "infoFragment";
    private static final String STATE_OPCION = "opcion";

    // Variables.
    private FotoFragment frgFoto;
    private InfoFragment frgInfo;
    private Spinner spnOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se activa el ítem de overflow en dispositivos con botón físico de
        // menú.
        overflowEnDispositivoConTeclaMenu();
        // Se indica que la ActionBar va a corresponder al widget Toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Se crea el adaptador para el Spinner a partir de un array de
            // constantes de cadena.
            ToolbarSpinnerAdapter adaptador = new ToolbarSpinnerAdapter(
                    getSupportActionBar().getThemedContext(),
                    new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.opciones))));
            // Se establece el adaptador y el listener para el spinner (que será la
            // propia actividad).
            spnOpciones = (Spinner) findViewById(R.id.spn);
            if (spnOpciones != null) {
                spnOpciones.setOnItemSelectedListener(this);
                spnOpciones.setAdapter(adaptador);
            }
            // toolbar.addView(spnOpciones);
        }
        // Si venimos de un estado anterior.
        if (savedInstanceState != null) {
            // Se coloca en la opción en la que estaba.
            spnOpciones.setSelection(savedInstanceState
                    .getInt(STATE_OPCION));
        }
    }

    // Cuando se produce un cambio de configuración.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Almacena qué pestaña tenemos seleccionada.
        outState.putInt(STATE_OPCION, spnOpciones.getSelectedItemPosition());
        super.onSaveInstanceState(outState);
    }

    // Carga el fragmento de la foto
    private void cargarFragmentoFoto() {
        // Se busca el fragmento
        frgFoto = (FotoFragment) getFragmentManager().findFragmentByTag(
                TAG_FRG_FOTO);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        // Si no se encuentra, se crea y se añade al contenedor.
        if (frgFoto == null) {
            frgFoto = FotoFragment.newInstance(R.drawable.bench);
            ft.add(R.id.flContenido, frgFoto, TAG_FRG_FOTO);
        } else {
            // Si se encuentra se enlaza con el contenedor.
            ft.attach(frgFoto);
        }
        // Se desvincula el otro fragmento del contenedor.
        if (frgInfo != null) {
            ft.detach(frgInfo);
        }
        ft.commit();
    }

    // Carga el fragmento de la info.
    private void cargarFragmentoInfo() {
        // Se busca el fragmento.
        frgInfo = (InfoFragment) getFragmentManager().findFragmentByTag(
                TAG_FRG_INFO);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        // Si no se encuentra, se crea y se añade al contenedor.
        if (frgInfo == null) {
            frgInfo = new InfoFragment();
            ft.add(R.id.flContenido, frgInfo, TAG_FRG_INFO);
        } else {
            // Si se encuentra se enlaza con el contenedor.
            ft.attach(frgInfo);
        }
        // Se desvincula el otro fragmento del contenedor.
        if (frgFoto != null) {
            ft.detach(frgFoto);
        }
        ft.commit();
    }

    // Activa el ítem de overflow en dispositivos con botón físico de menú.
    private void overflowEnDispositivoConTeclaMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignorar
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case POS_OPCION_FOTO:
                cargarFragmentoFoto();
                break;
            case POS_OPCION_INFO:
                cargarFragmentoInfo();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
