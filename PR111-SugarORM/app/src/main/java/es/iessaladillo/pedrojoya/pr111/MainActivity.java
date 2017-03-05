package es.iessaladillo.pedrojoya.pr111;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings({"WeakerAccess", "unused", "CanBeFinal"})
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lstAlumnos)
    ListView mLstAlumnos;
    @BindView(R.id.lblNoHayAlumnos)
    TextView mLblNoHayAlumnos;
    @BindView(R.id.rlListaVacia)
    RelativeLayout rlListaVacia;

    private AlumnosAdapter mAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Se inicializan las vistas.
        initVistas();
    }

    // Inicializa las vistas.
    private void initVistas() {
        mAdaptador = new AlumnosAdapter(this, new ArrayList<Alumno>());
        mLstAlumnos.setEmptyView(rlListaVacia);
        mLstAlumnos.setAdapter(mAdaptador);
        mLstAlumnos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // Se establece el listener para los eventos del modo de acción
        // contextual.
        mLstAlumnos
                .setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                    // Al preparse el modo.
                    @Override
                    public boolean onPrepareActionMode(ActionMode mode,
                                                       Menu menu) {
                        // No se hace nada.
                        return false;
                    }

                    // Al destruirse el modo.
                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        // No se hace nada.
                    }

                    // Al crear el modo.
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        // Se infla la especificación del menú contextual en el
                        // menú.
                        mode.getMenuInflater().inflate(R.menu.contextual_activity_main,
                                menu);
                        // Se retorna que ya se ha gestionado el evento.
                        return true;
                    }

                    // Al hacer click sobre un ítem de acción del modo
                    // contextual.
                    @Override
                    public boolean onActionItemClicked(ActionMode mode,
                                                       MenuItem item) {
                        // Dependiendo del elemento pulsado.
                        switch (item.getItemId()) {
                            case R.id.mnuEliminar:
                                // Se obtienen los elementos seleccionados (y se
                                // quita la selección).
                                ArrayList<Alumno> alumnos = getElementosSeleccionados(
                                        mLstAlumnos, true);
                                // Se eliminan del mAdaptador.
                                for (Alumno alumno : alumnos) {
                                    alumno.delete();
                                    mAdaptador.remove(alumno);
                                }
                                // Se notifica al mAdaptador que ha habido cambios.
                                // mAdaptador.notifyDataSetChanged();
                                break;
                        }
                        // Se retorna que se ha procesado el evento.
                        return true;
                    }

                    // Al cambiar el estado de selección de un elemento.
                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode,
                                                          int position, long id, boolean checked) {
                        // Se actualiza el título de la action bar contextual.
                        mode.setTitle(getString(R.string.de, mLstAlumnos.getCheckedItemCount(),
                                mLstAlumnos.getCount()));
                    }
                });
        // Cuando se hace click en un elemento de la lista.
        mLstAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adaptador, View v,
                                    int position, long id) {
                // Se muestra la actividad de alumno, pasándole el alumno pulsado.
                Intent intent = new Intent(getApplicationContext(), AlumnoActivity.class);
                intent.putExtra(AlumnoActivity.EXTRA_ALUMNO, (Alumno) mLstAlumnos.getItemAtPosition(position));
                startActivity(intent);
            }
        });

    }

    // Cuando se pulsa el botón de Agregar o el icono de No hay alumnos.
    @OnClick({R.id.rlListaVacia, R.id.btnAgregar})
    public void mostrarAgregar() {
        startActivity(new Intent(this, AlumnoActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        // Se cargan los datos.
        cargarAlumnos();
    }

    // Carga en la lista los alumnos de la base de datos.
    private void cargarAlumnos() {
        // Si la tabla aún no está creada lanza una excepción.
        List<Alumno> alumnos;
        try {
            alumnos = Alumno.listAll(Alumno.class);
            mAdaptador.clear();
            mAdaptador.addAll(alumnos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Retorna un ArrayList con los elementos seleccionados. Recibe la lista y
    // si debe quitarse la selección una vez obtenidos los elementos.
    @SuppressWarnings("SameParameterValue")
    private ArrayList<Alumno> getElementosSeleccionados(ListView lst,
                                                        boolean uncheck) {
        // ArrayList resultado.
        ArrayList<Alumno> datos = new ArrayList<>();
        // Se obtienen los elementos seleccionados de la lista.
        SparseBooleanArray selec = lst.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            // Si está seleccionado.
            if (selec.valueAt(i)) {
                int position = selec.keyAt(i);
                // Se quita de la selección (si hay que hacerlo).
                if (uncheck) {
                    lst.setItemChecked(position, false);
                }
                // Se añade al resultado.
                datos.add((Alumno) lst.getItemAtPosition(selec.keyAt(i)));
            }
        }
        // Se retorna el resultado.
        return datos;
    }

}
