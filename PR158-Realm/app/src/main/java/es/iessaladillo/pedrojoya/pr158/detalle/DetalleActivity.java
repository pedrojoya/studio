package es.iessaladillo.pedrojoya.pr158.detalle;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Fade;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import es.iessaladillo.pedrojoya.pr158.R;
import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr158.db.entities.Asignatura;
import es.iessaladillo.pedrojoya.pr158.utils.ClickToMultipleSelectEditText;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


@SuppressWarnings({"WeakerAccess", "unused", "CanBeFinal"})
public class DetalleActivity extends AppCompatActivity implements ClickToMultipleSelectEditText
        .OnMultipleItemsSelectedListener {

    private static final String EXTRA_ID_ALUMNO = "idAlumno";
    private static final String STATE_URL_FOTO = "urlFoto";
    private static final String TN_FOTO = "transition_foto";
    private static final long ENTER_TRANSITION_DURATION_MILIS = 500;
    private static final String STATE_INDICES_ASIGNATURAS_SELECCIONADAS =
            "indicesAsignaturasSeleccionadas";

    @BindView(R.id.imgFoto)
    ImageView imgFoto;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.txtNombre)
    TextInputEditText txtNombre;
    @BindView(R.id.txtDireccion)
    TextInputEditText txtDireccion;
    @BindView(R.id.txtAsignaturas)
    ClickToMultipleSelectEditText txtAsignaturas;
    @BindView(R.id.fabAccion)
    FloatingActionButton fabAccion;

    private Realm mRealm;
    private String mIdAlumno;
    private Alumno mAlumno;
    private Random mAleatorio;
    private String mUrlFoto;
    RealmResults<Asignatura> mAsignaturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Se habilita el uso de transiciones entre actividades.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        ButterKnife.bind(this);
        // Se configuran las transiciones.
        configTransitions();
        mAleatorio = new Random();
        // Se obtiene la instancia de Realm.
        mRealm = Realm.getDefaultInstance();
        // Se obtienen e inicializan las vistas.
        initVistas();
        // Si nos han llamado con un id de alumno, se obtiene el alumno y se muestra.
        if (getIntent() != null && getIntent().hasExtra(EXTRA_ID_ALUMNO)) {
            mIdAlumno = getIntent().getStringExtra(EXTRA_ID_ALUMNO);
            mAlumno = mRealm.where(Alumno.class).equalTo("id", mIdAlumno).findFirst();
            mUrlFoto = mAlumno.getUrlFoto();
            alumnoToVistas(savedInstanceState);
            setTitle(R.string.actualizar_alumno);
        } else {
            // Se crea un nuevo alumno con foto aleatoria.
            mAlumno = new Alumno();
            mUrlFoto = getFotoAleatoria();
            setTitle(R.string.agregar_alumno);
            txtAsignaturas.setSelection(integerListToArray(new ArrayList<Integer>()));
            txtAsignaturas.setText(getCadenaAsignaturas(new ArrayList<String>()));

        }
        // Si venimos de un estado anterior dejamos la foto tal y como estaba.
        if (savedInstanceState != null) {
            mUrlFoto = savedInstanceState.getString(STATE_URL_FOTO);
        }
        // Se muestra la foto. Cuando esté cargada, se inicia la transición
        // que había sido pospuesta.
        Log.d("Mia", mUrlFoto);
        Picasso.with(this).load(mUrlFoto).placeholder(R.drawable.placeholder).error(
                R.drawable.placeholder).into(imgFoto, new Callback() {
            @Override
            public void onSuccess() {
                supportStartPostponedEnterTransition();
            }

            @Override
            public void onError() {
                supportStartPostponedEnterTransition();
            }
        });
    }

    private void cargarAsignaturas() {
        // Se le añaden las asignaturas
        mAsignaturas = mRealm.where(Asignatura.class).findAll();
        ArrayList<String> nombresAsignaturas = new ArrayList<>();
        for (Asignatura asignatura : mAsignaturas) {
            nombresAsignaturas.add(asignatura.getId());
        }
        txtAsignaturas.setListener(this);
        txtAsignaturas.setDialogTitle(getString(R.string.asignaturas));
        txtAsignaturas.setItems(nombresAsignaturas);
    }

    @OnFocusChange(R.id.txtAsignaturas)
    public void mostrarAsignaturas(View v) {
        txtAsignaturas.showDialog(v);
    }

    // Obtiene una foto aleatoria.
    private String getFotoAleatoria() {
        return "http://lorempixel.com/200/200/abstract/" + (mAleatorio.nextInt(7) + 1) + "/";
    }

    // Muestra los datos del alumno
    private void alumnoToVistas(Bundle saveInstanceState) {
        txtNombre.setText(mAlumno.getNombre());
        txtDireccion.setText(mAlumno.getDireccion());
        if (saveInstanceState == null) {
            RealmList<Asignatura> asignaturasAlumno = mAlumno.getAsignaturas();
            ArrayList<Integer> indicesAsignaturasAlumno = new ArrayList<>();
            for (int i = 0; i < mAsignaturas.size(); i++) {
                // Se busca esa asignatura entre las del alumno.
                for (int j = 0; j < asignaturasAlumno.size(); j++) {
                    if (mAsignaturas.get(i).getId().equals(asignaturasAlumno.get(j).getId())) {
                        indicesAsignaturasAlumno.add(i);
                        break;
                    }
                }
            }
            int[] indices = integerListToArray(indicesAsignaturasAlumno);
            txtAsignaturas.setSelection(indices);
            ArrayList<String> nombresAsignaturasAlumno = new ArrayList<>();
            for (Asignatura asignaturaAlumno : asignaturasAlumno) {
                nombresAsignaturasAlumno.add(asignaturaAlumno.getId());
            }
            txtAsignaturas.setText(getCadenaAsignaturas(nombresAsignaturasAlumno));
        } else {
            int[] indices = integerListToArray(
                    saveInstanceState.getIntegerArrayList(STATE_INDICES_ASIGNATURAS_SELECCIONADAS));
            txtAsignaturas.setSelection(indices);
        }
    }

    private int[] integerListToArray(ArrayList<Integer> indicesAsignaturasAlumno) {
        int[] indices = new int[indicesAsignaturasAlumno.size()];
        for (int i = 0; i < indicesAsignaturasAlumno.size(); i++) {
            indices[i] = indicesAsignaturasAlumno.get(i);
        }
        return indices;
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        configToolbar();
        ViewCompat.setTransitionName(imgFoto, TN_FOTO);
        cargarAsignaturas();
    }

    @OnClick(R.id.imgFoto)
    public void cambiarFoto(View view) {
        mUrlFoto = getFotoAleatoria();
        Picasso.with(view.getContext()).load(mUrlFoto).placeholder(R.drawable.placeholder).into(
                imgFoto);
    }

    // Configura la Toolbar.
    private void configToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, android.R.color.white));
    }

    @OnEditorAction(R.id.txtDireccion)
    public boolean onDatosIntroducidos(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            guardar();
            return true;
        }
        return false;
    }

    @OnClick(R.id.fabAccion)
    public void guardar() {
        if (TextUtils.isEmpty(txtNombre.getText().toString()) || TextUtils.isEmpty(
                txtDireccion.getText().toString())) {
            return;
        }
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (TextUtils.isEmpty(mIdAlumno)) {
                    // Si es un alumno nuevo.
                    mAlumno.setId(UUID.randomUUID().toString());
                    mAlumno.setNombre(txtNombre.getText().toString());
                    mAlumno.setDireccion(txtDireccion.getText().toString());
                    mAlumno.setUrlFoto(mUrlFoto);
                    mAlumno.setTimestamp(System.currentTimeMillis());
                } else {
                    // Si se está actualizando.
                    mAlumno.setNombre(txtNombre.getText().toString());
                    mAlumno.setDireccion(txtDireccion.getText().toString());
                    mAlumno.setUrlFoto(mUrlFoto);
                }
                // Se añade o actualiza el alumno a la base de datos (en el hilo secundario).
                Alumno realmAlumno = realm.copyToRealmOrUpdate(mAlumno);
                // Se le añaden las asignaturas
                realmAlumno.getAsignaturas().clear();
                @SuppressWarnings("unchecked") List<Integer> indicesAsignaturasAlumno =
                        txtAsignaturas
                        .getSelectedIndices();
                if (indicesAsignaturasAlumno != null) {
                    for (int i = 0; i < indicesAsignaturasAlumno.size(); i++) {
                        realmAlumno.getAsignaturas().add(
                                mAsignaturas.get(indicesAsignaturasAlumno.get(i)));
                    }
                }
                //realm.copyToRealmOrUpdate(realmAlumno);
            }
        });
        setResult(RESULT_OK);
        // Se finaliza la actividad.
        ActivityCompat.finishAfterTransition(DetalleActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Se cierra la base de datos.
        mRealm.close();
    }

    // Método estático para llamar a la actividad (para actualizar).
    @SuppressWarnings("SameParameterValue")
    public static void startForResult(Activity activity, int requestCode, String idAlumno,
            View foto) {
        Intent intent = new Intent(activity, DetalleActivity.class);
        intent.putExtra(EXTRA_ID_ALUMNO, idAlumno);
        Bundle opciones = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, foto,
                TN_FOTO).toBundle();
        ActivityCompat.startActivityForResult(activity, intent, requestCode, opciones);
    }

    // Método estático para llamar a la actividad (para añadir).
    @SuppressWarnings("SameParameterValue")
    public static void startForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, DetalleActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Se almacena la url de la foto.
        outState.putString(STATE_URL_FOTO, mUrlFoto);
        //noinspection unchecked,Convert2Diamond
        outState.putIntegerArrayList(STATE_INDICES_ASIGNATURAS_SELECCIONADAS,
                new ArrayList<Integer>(txtAsignaturas.getSelectedIndices()));
        super.onSaveInstanceState(outState);
    }

    // Se configuran las transiciones de la actividad.
    private void configTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Transición de entrada.
            Fade enterTransition = new Fade(Fade.IN);
            enterTransition.excludeTarget(android.R.id.statusBarBackground, true);
            enterTransition.excludeTarget(android.R.id.navigationBarBackground, true);
            enterTransition.excludeTarget(R.id.appbar, true);
            enterTransition.setDuration(ENTER_TRANSITION_DURATION_MILIS);
            getWindow().setEnterTransition(enterTransition);
            // Se pospone la animación de entrada hasta que la imagen
            // esté disponible.
            supportPostponeEnterTransition();
            // Transición de retorno.
            getWindow().setReturnTransition(new Fade(Fade.OUT));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Se gestiona el Up para que se haga la animación.
                // return gestionarUp();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public void selectedIndices(List<Integer> indices) {
    }

    @Override
    public void selectedStrings(List<String> strings) {
        txtAsignaturas.setText(getCadenaAsignaturas(strings));
    }

    private String getCadenaAsignaturas(List<String> nombresAsignaturas) {
        if (nombresAsignaturas.size() > 0) {
            return TextUtils.join(", ", nombresAsignaturas);
        } else {
            return getString(R.string.ninguna);
        }
    }

}
