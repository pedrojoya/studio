package es.iessaladillo.pedrojoya.pr158;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.Random;
import java.util.UUID;

import io.realm.Realm;


public class DetalleActivity extends AppCompatActivity {

    private static final String EXTRA_ID_ALUMNO = "idAlumno";

    private Realm mRealm;
    private EditText txtNombre;
    private EditText txtDireccion;
    private String mIdAlumno;
    private Alumno mAlumno;
    private Random mAleatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        mAleatorio = new Random();
        // Se obtiene la instancia de Realm.
        mRealm = Realm.getInstance(getApplicationContext());
        // Se obtienen e inicializan las vistas.
        initVistas();
        // Se muestra el alumno.
        if (getIntent() != null && getIntent().hasExtra(EXTRA_ID_ALUMNO)) {
            mIdAlumno = getIntent().getStringExtra(EXTRA_ID_ALUMNO);
            mostrarAlumno(mIdAlumno);
        }
    }

    private void mostrarAlumno(String idAlumno) {
        mAlumno = mRealm.where(Alumno.class).equalTo("id", idAlumno).findFirst();
        txtNombre.setText(mAlumno.getNombre());
        txtDireccion.setText(mAlumno.getDireccion());
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        configToolbar();
        configFab();
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtDireccion = (EditText) findViewById(R.id.txtDireccion);
    }

    // Configura la Toolbar.
    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    // Configura el FAB.
    private void configFab() {
        FloatingActionButton fabAccion = (FloatingActionButton) findViewById(R.id.fabAccion);
        fabAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRealm.beginTransaction();
                if (TextUtils.isEmpty(mIdAlumno)) {
                    mAlumno = new Alumno();
                    mAlumno.setId(UUID.randomUUID().toString());
                    mAlumno.setNombre(txtNombre.getText().toString());
                    mAlumno.setDireccion(txtDireccion.getText().toString());
                    mAlumno.setUrlFoto("http://lorempixel.com/100/100/nature/" + (mAleatorio.nextInt(9) + 1) + "/");
                    mAlumno.setTimestamp(System.currentTimeMillis());
                    mRealm.copyToRealmOrUpdate(mAlumno);
                } else {
                    mAlumno.setNombre(txtNombre.getText().toString());
                    mAlumno.setDireccion(txtDireccion.getText().toString());
                }
                // Se añade o actualiza el alumno a la base de datos.
                mRealm.commitTransaction();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    // Método estático para llamar a la actividad.
    public static void start(Context contexto, String idAlumno) {
        Intent intent = new Intent(contexto, DetalleActivity.class);
        intent.putExtra(EXTRA_ID_ALUMNO, idAlumno);
        contexto.startActivity(intent);
    }

    // Método estático para llamar a la actividad.
    public static void start(Context contexto) {
        contexto.startActivity(new Intent(contexto, DetalleActivity.class));
    }

}
