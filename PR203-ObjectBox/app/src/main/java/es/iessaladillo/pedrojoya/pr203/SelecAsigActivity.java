package es.iessaladillo.pedrojoya.pr203;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.BoxStore;

@SuppressWarnings({"WeakerAccess", "CanBeFinal", "FieldCanBeLocal"})
public class SelecAsigActivity extends AppCompatActivity {

    public static final String EXTRA_ASIGNATURAS = "EXTRA_ASIGNATURAS";
    private static final String EXTRA_ALUMNO_ID = "EXTRA_ALUMNO_ID";

    @BindView(R.id.lstAsignaturas)
    ListView lstAsignaturas;
    @BindView(R.id.lblEmptyView)
    TextView lblEmptyView;
    private List<Asignatura> mAsignaturas;
    private ArrayAdapter<Asignatura> mAdaptador;
    private BoxStore mBoxStore;
    private long mIdAlumno;
    private List<AsignaturasAlumnos> mAsignaturasAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_asig);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            mIdAlumno = getIntent().getLongExtra(EXTRA_ALUMNO_ID, 0);
        }
        initBD();
        initVistas();
    }

    private void initBD() {
        mBoxStore = ((App) getApplication()).getBoxStore();
        mAsignaturas = mBoxStore.boxFor(Asignatura.class)
                .query()
                .order(Asignatura_.nombre)
                .build()
                .find();
        mAsignaturasAlumno = mBoxStore.boxFor(AsignaturasAlumnos.class).query().equal(
                AsignaturasAlumnos_.alumnoId, mIdAlumno).build().find();

    }

    private void initVistas() {
        lstAsignaturas.setEmptyView(lblEmptyView);
        lstAsignaturas.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        mAdaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice,
                mAsignaturas);
        lstAsignaturas.setAdapter(mAdaptador);
        Asignatura asignatura;
        for (int i = 0; i < lstAsignaturas.getCount(); i++) {
            asignatura = (Asignatura) lstAsignaturas.getItemAtPosition(i);
            // Si la asignatura está entre las del alumnos
            if (esDelAlumno(asignatura.getId())) {
                lstAsignaturas.setItemChecked(i, true);
            }
        }
    }

    private boolean esDelAlumno(long idAsignatura) {
        for (AsignaturasAlumnos asigAlum : mAsignaturasAlumno) {
            if (asigAlum.getAsignaturaId() == idAsignatura) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("SameParameterValue")
    public static void startForResult(Activity context, int requestCode, long idAlumno) {
        Intent intent = new Intent(context, SelecAsigActivity.class);
        intent.putExtra(EXTRA_ALUMNO_ID, idAlumno);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ASIGNATURAS, getElementosSeleccionados(lstAsignaturas));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private ArrayList<Asignatura> getElementosSeleccionados(ListView lst) {
        // ArrayList resultado.
        ArrayList<Asignatura> datos = new ArrayList<>();
        // Se obtienen los elementos seleccionados de la lista.
        SparseBooleanArray selec = lst.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            // Si está seleccionado.
            if (selec.valueAt(i)) {
                // Se añade al resultado.
                datos.add((Asignatura) lst.getItemAtPosition(selec.keyAt(i)));
            }
        }
        // Se retorna el resultado.
        return datos;
    }

}
