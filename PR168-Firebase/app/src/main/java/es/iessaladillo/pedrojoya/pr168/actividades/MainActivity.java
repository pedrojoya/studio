package es.iessaladillo.pedrojoya.pr168.actividades;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import es.iessaladillo.pedrojoya.pr168.App;
import es.iessaladillo.pedrojoya.pr168.R;
import es.iessaladillo.pedrojoya.pr168.fragmentos.ListaAlumnosFragment;
import es.iessaladillo.pedrojoya.pr168.fragmentos.ListaAlumnosFragment.OnListaAlumnosFragmentListener;
import es.iessaladillo.pedrojoya.pr168.fragmentos.LoginFragment;
import es.iessaladillo.pedrojoya.pr168.fragmentos.SiNoDialogFragment;
import es.iessaladillo.pedrojoya.pr168.fragmentos.SiNoDialogFragment.SiNoDialogListener;

public class MainActivity extends AppCompatActivity implements
        OnListaAlumnosFragmentListener, SiNoDialogListener, LoginFragment.OnLoginFragmentListener {

    private static final String TAG_LISTA_FRAGMENT = "tag_lista_fragment";
    private static final String TAG_LOGIN_FRAGMENT = "tag_login_fragment";
    private static final String TAG_FRG_DIALOGO = "tag_frg_dialogo";
    private static final int RC_AGREGAR = 1;
    private static final int RC_EDITAR = 2;

    private FloatingActionButton btnAgregar;
    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        initVistas();
        // Se comprueba si el usuario está conectado.
        firebase = new Firebase(App.FIREBASE_URL);
        if (firebase.getAuth() == null || haExpirado(firebase.getAuth())) {
            // Se muestra el fragmento de login.
            cargarFragmentoLogin();
        }
        else {
            App.setUid(firebase.getAuth().getUid());
            cargarFragmentoLista();
        }
    }

    // Retorna si el login ha expirado.
    private boolean haExpirado(AuthData auth) {
        return (System.currentTimeMillis() / 1000) >= auth.getExpires();
    }

    @Override
    public void onLogin(String email, String password) {
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                App.setUid(firebase.getAuth().getUid());
                invalidateOptionsMenu();
                cargarFragmentoLista();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                String mensaje;
                switch (firebaseError.getCode()) {
                    case FirebaseError.USER_DOES_NOT_EXIST:
                        mensaje = "El usuario especificado no existe";
                        break;
                    case FirebaseError.INVALID_EMAIL:
                        mensaje = "El email especificado no es válido";
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        mensaje = "La contraseña especificada no es válida";
                        break;
                    default:
                        mensaje = "Se ha producido un error al conectarse";
                }
                Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show() ;
            }
        });
    }

    @Override
    public void onSignup(final String email, final String password) {
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                onLogin(email, password);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                String mensaje;
                switch (firebaseError.getCode()) {
                    case FirebaseError.EMAIL_TAKEN:
                        mensaje = "Error: ya existe un usuario con ese email";
                        break;
                    default:
                        mensaje = "Se ha producido un error al crear el usuario";
                }
                Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show() ;
            }
        });
    }

    private void onLogout() {
        firebase.unauth();
        invalidateOptionsMenu();
        cargarFragmentoLogin();
    }

    // Configura la toolbar.
    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        findViewById(R.id.btnAgregar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAgregarAlumno();
            }
        });
        btnAgregar = (FloatingActionButton) findViewById(R.id.btnAgregar);
    }

    // Carga el fragmento de login.
    private void cargarFragmentoLogin() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_LOGIN_FRAGMENT) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContenido, new LoginFragment(), TAG_LOGIN_FRAGMENT)
                    .commit();
        }
    }

    // Carga el fragmento de la lista.
    private void cargarFragmentoLista() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_LISTA_FRAGMENT) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContenido, new ListaAlumnosFragment(), TAG_LISTA_FRAGMENT)
                    .commit();
        }
    }

    // Muestra la actividad de alumno para agregar.
    @Override
    public void onAgregarAlumno() {
        AlumnoActivity.startForResult(this, RC_AGREGAR);
    }

    // Muestra la actividad de alumno para editar. Recibe la key del alumno.
    @Override
    public void onEditarAlumno(String key) {
        AlumnoActivity.startForResult(this, key, RC_EDITAR);
    }

    // Muestra el fragmento de diálogo de confirmación de eliminación.
    @Override
    public void onConfirmarEliminarAlumnos() {
        SiNoDialogFragment frgDialogo = new SiNoDialogFragment();
        frgDialogo.show(getSupportFragmentManager(), TAG_FRG_DIALOGO);
    }

    // Se confirma la eliminación de los alumnos seleccionados.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog) {
        // Se llama al método del fragmento para eliminar los alumnos
        // seleccionados.
        ListaAlumnosFragment frg = (ListaAlumnosFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_LISTA_FRAGMENT);
        if (frg != null) {
            frg.eliminarAlumnosSeleccionados();
        }
    }

    // No se confirma la eliminación de los alumnos seleccionados.
    @Override
    public void onNegativeButtonClick(DialogFragment dialog) {
        // Método requerido por la interfaz SiNoDialogListener.
    }

    @Override
    public void onShowFAB() {
        btnAgregar.show();
    }

    @Override
    public void onHideFAB() {
        btnAgregar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuLogout) {
            onLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.mnuLogout).setVisible(!(firebase.getAuth() == null || haExpirado(firebase.getAuth())));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }
}
