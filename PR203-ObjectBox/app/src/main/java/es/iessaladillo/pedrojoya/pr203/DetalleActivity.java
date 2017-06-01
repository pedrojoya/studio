package es.iessaladillo.pedrojoya.pr203;

import android.app.Activity;
import android.content.Context;
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
import android.view.KeyEvent;
import android.view.Menu;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import io.objectbox.Box;
import io.objectbox.BoxStore;

@SuppressWarnings({"WeakerAccess", "CanBeFinal", "FieldCanBeLocal"})
public class DetalleActivity extends AppCompatActivity {

    private static final String EXTRA_ID_ALUMNO = "idAlumno";
    private static final String STATE_URL_FOTO = "urlFoto";
    private static final String TN_FOTO = "transition_foto";
    private static final long ENTER_TRANSITION_DURATION_MILIS = 500;
    private static final int RC_ASIGNATURAS = 0;

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
    TextInputEditText txtAsignaturas;
    @BindView(R.id.fabAccion)
    FloatingActionButton fabAccion;

    private long mIdAlumno;
    private Alumno mAlumno;
    private ArrayList<Asignatura> mAsigSelec;
    private Random mAleatorio = new Random();
    private String mUrlFoto;
    private BoxStore mBoxStore;
    private Box<Alumno> mAlumnoBox;
    private Box<AsignaturasAlumnos> mAsignaturasAlumnosBox;
    private List<AsignaturasAlumnos> mAsignaturasAlumno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        habilitarTransiciones();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        ButterKnife.bind(this);
        configTransiciones();
        initBD();
        initVistas();
        // Si nos han llamado con un id de alumno, se obtiene el alumno y se muestra.
        if (getIntent() != null && getIntent().hasExtra(EXTRA_ID_ALUMNO)) {
            mostrarAlumno();
        } else {
            nuevoAlumno();
        }
        restaurarEstado(savedInstanceState);
        mostrarFoto();
    }

    private void habilitarTransiciones() {
        // Se habilita el uso de transiciones entre actividades.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
    }

    private void initBD() {
        mBoxStore = ((App) getApplication()).getBoxStore();
        mAlumnoBox = mBoxStore.boxFor(Alumno.class);
        mAsignaturasAlumnosBox = mBoxStore.boxFor(AsignaturasAlumnos.class);
    }

    private void initVistas() {
        configToolbar();
        ViewCompat.setTransitionName(imgFoto, TN_FOTO);
    }

    private void mostrarAlumno() {
        mIdAlumno = getIntent().getLongExtra(EXTRA_ID_ALUMNO, 0);
        mAlumno = mAlumnoBox.get(mIdAlumno);
        mUrlFoto = mAlumno.getUrlFoto();
        mAsignaturasAlumno = mAsignaturasAlumnosBox.query().equal(AsignaturasAlumnos_.alumnoId,
                mAlumno.getId()).build().find();
        alumnoToVistas();
        setTitle(R.string.actualizar_alumno);
    }

    private void nuevoAlumno() {
        // Se crea un nuevo alumno con foto aleatoria.
        mAlumno = new Alumno();
        mUrlFoto = getFotoAleatoria();
        setTitle(R.string.agregar_alumno);
    }

    private void restaurarEstado(Bundle savedInstanceState) {
        // Si venimos de un estado anterior dejamos la foto tal y como estaba.
        if (savedInstanceState != null) {
            mUrlFoto = savedInstanceState.getString(STATE_URL_FOTO);
        }
    }

    private void mostrarFoto() {
        // Se muestra la foto. Cuando esté cargada, se inicia la transición
        // que había sido pospuesta.
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

    private String getFotoAleatoria() {
        return "http://lorempixel.com/200/200/abstract/" + (mAleatorio.nextInt(7) + 1) + "/";
    }

    private void alumnoToVistas() {
        txtNombre.setText(mAlumno.getNombre());
        txtDireccion.setText(mAlumno.getDireccion());
        StringBuilder sb = new StringBuilder();
        if (mAsignaturasAlumno == null || mAsignaturasAlumno.size() == 0) {
            sb.append(getString(R.string.ninguna));
        } else {
            boolean primero = true;
            if (mAsignaturasAlumno != null) {
                for (AsignaturasAlumnos as : mAsignaturasAlumno) {
                    if (!primero) {
                        sb.append(", ");
                    } else {
                        primero = false;
                    }
                    sb.append(as.getAsignatura());
                }
            }
            txtAsignaturas.setText(sb.toString());
        }
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

    @SuppressWarnings("UnusedParameters")
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
        mAlumno.setNombre(txtNombre.getText().toString());
        mAlumno.setDireccion(txtDireccion.getText().toString());
        mAlumno.setUrlFoto(mUrlFoto);
        mAlumnoBox.put(mAlumno);
        mIdAlumno = mAlumno.getId();
        mAsignaturasAlumnosBox.remove(mAsignaturasAlumno);
        if (mAsigSelec != null) {
            for (Asignatura asig : mAsigSelec) {
                mAsignaturasAlumnosBox.put(new AsignaturasAlumnos(0, mIdAlumno, asig.getId()));
            }
        }
        // Se finaliza la actividad.
        setResult(RESULT_OK);
        ActivityCompat.finishAfterTransition(DetalleActivity.this);
    }

    public static void start(Activity context, long idAlumno, View foto) {
        Intent intent = new Intent(context, DetalleActivity.class);
        intent.putExtra(EXTRA_ID_ALUMNO, idAlumno);
        Bundle opciones = ActivityOptionsCompat.makeSceneTransitionAnimation(context, foto, TN_FOTO)
                .toBundle();
        ActivityCompat.startActivity(context, intent, opciones);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, DetalleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Se almacena la url de la foto.
        outState.putString(STATE_URL_FOTO, mUrlFoto);
        super.onSaveInstanceState(outState);
    }

    // Se configuran las transiciones de la actividad.
    private void configTransiciones() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detalle, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Se gestiona el Up para que se haga la animación.
                // return gestionarUp();
                onBackPressed();
                return true;
            case R.id.mnuAsignaturas:
                //Toast.makeText(this, getListaAsignaturas(), Toast.LENGTH_SHORT).show();
                seleccionarAsignaturas();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.txtAsignaturas)
    void seleccionarAsignaturas() {
        SelecAsigActivity.startForResult(this, RC_ASIGNATURAS, mIdAlumno);
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_ASIGNATURAS) {
            if (data != null && data.hasExtra(SelecAsigActivity.EXTRA_ASIGNATURAS)) {
                mAsigSelec = data.getParcelableArrayListExtra(SelecAsigActivity.EXTRA_ASIGNATURAS);
                if (mAsigSelec == null || mAsigSelec.size() == 0) {
                    txtAsignaturas.setText(getString(R.string.ninguna));
                } else {
                    txtAsignaturas.setText(TextUtils.join(", ", mAsigSelec));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
