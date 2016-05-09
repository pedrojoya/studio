package es.iessaladillo.pedrojoya.pr158;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Random;
import java.util.UUID;

import io.realm.Realm;


public class DetalleActivity extends AppCompatActivity {

    private static final String EXTRA_ID_ALUMNO = "idAlumno";
    private static final String STATE_URL_FOTO = "urlFoto";
    private static final String TN_FOTO = "transition_foto";
    private static final long ENTER_TRANSITION_DURATION_MILIS = 500;

    private Realm mRealm;
    private EditText txtNombre;
    private EditText txtDireccion;
    private String mIdAlumno;
    private Alumno mAlumno;
    private Random mAleatorio;
    private String mUrlFoto;
    private ImageView imgFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Se habilita el uso de transiciones entre actividades.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
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
            alumnoToVistas();
        } else {
            // Se crea un nuevo alumno con foto aleatoria.
            mAlumno = new Alumno();
            mUrlFoto = getFotoAleatoria();
        }
        // Si venimos de un estado anterior dejamos la foto tal y como estaba.
        if (savedInstanceState != null) {
            mUrlFoto = savedInstanceState.getString(STATE_URL_FOTO);
        }
        // Se muestra la foto. Cuando esté cargada, se inicia la transición
        // que había sido pospuesta.
        Glide.with(this).load(mUrlFoto).placeholder(R.drawable.placeholder)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                }).into(imgFoto);
    }

    // Obtiene una foto aleatoria.
    private String getFotoAleatoria() {
        return "http://lorempixel.com/300/300/nature/" + (mAleatorio.nextInt(8) + 1) + "/";
    }

    // Muestra los datos del alumno
    private void alumnoToVistas() {
        txtNombre.setText(mAlumno.getNombre());
        txtDireccion.setText(mAlumno.getDireccion());
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        configToolbar();
        configFab();
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtDireccion = (EditText) findViewById(R.id.txtDireccion);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        ViewCompat.setTransitionName(imgFoto, TN_FOTO);
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUrlFoto = getFotoAleatoria();
                Glide.with(view.getContext()).load(mUrlFoto)
                        .placeholder(R.drawable.placeholder).into(imgFoto);
            }
        });
    }

    // Configura la Toolbar.
    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("");
    }

    // Configura el FAB.
    private void configFab() {
        FloatingActionButton fabAccion =
                (FloatingActionButton) findViewById(R.id.fabAccion);
        if (fabAccion != null) {
            fabAccion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Se guardan los cambios y se finaliza la actividad.
                    guardar();
                }
            });
        }
    }

    // Guarda los cambios en la base de datos y finaliza la actividad.
    private void guardar() {
        mRealm.beginTransaction();
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
        // Se añade o actualiza el alumno a la base de datos.
        mRealm.copyToRealmOrUpdate(mAlumno);
        mRealm.commitTransaction();
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
    public static void start(Activity activity, String idAlumno, View foto) {
        Intent intent = new Intent(activity, DetalleActivity.class);
        intent.putExtra(EXTRA_ID_ALUMNO, idAlumno);
        Bundle opciones = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                        activity,
                        foto, TN_FOTO).toBundle();
        ActivityCompat.startActivity(activity, intent, opciones);
    }

    // Método estático para llamar a la actividad (para añadir).
    public static void start(Activity activity) {
        Intent intent = new Intent(activity, DetalleActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Se almacena la url de la foto.
        outState.putString(STATE_URL_FOTO, mUrlFoto);
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
}