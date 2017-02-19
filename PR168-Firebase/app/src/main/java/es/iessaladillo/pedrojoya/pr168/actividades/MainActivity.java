package es.iessaladillo.pedrojoya.pr168.actividades;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.iessaladillo.pedrojoya.pr168.App;
import es.iessaladillo.pedrojoya.pr168.R;
import es.iessaladillo.pedrojoya.pr168.fragmentos.ListaAlumnosFragment;
import es.iessaladillo.pedrojoya.pr168.fragmentos.ListaAlumnosFragment
        .OnListaAlumnosFragmentListener;
import es.iessaladillo.pedrojoya.pr168.fragmentos.LoginFragment;
import es.iessaladillo.pedrojoya.pr168.fragmentos.SiNoDialogFragment;
import es.iessaladillo.pedrojoya.pr168.fragmentos.SiNoDialogFragment.SiNoDialogListener;

public class MainActivity extends AppCompatActivity implements OnListaAlumnosFragmentListener,
        SiNoDialogListener, LoginFragment.OnLoginFragmentListener {

    private static final String TAG_LISTA_FRAGMENT = "tag_lista_fragment";
    private static final String TAG_LOGIN_FRAGMENT = "tag_login_fragment";
    private static final String TAG_FRG_DIALOGO = "tag_frg_dialogo";
    private static final int RC_AGREGAR = 1;
    private static final int RC_EDITAR = 2;

    private FloatingActionButton btnAgregar;
    private DatabaseReference mFirebase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        initVistas();
        // Se obtiene la referencia a la base de datos.
        mFirebase = FirebaseDatabase.getInstance().getReference();
        // Se obtiene el objeto de autenticación y se crea el listener
        // de autenticación.
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // El usuario NO está logueado o su conexión ha expirado.
                    // Se muestra el fragmento de login.
                    cargarFragmentoLogin();
                } else {
                    // El usuario está logueado correctamente.
                    App.setUid(user.getUid());
                    cargarFragmentoLista();
                }
            }
        };
    }

    @Override
    public void onLogin(String email, String password) {
        // Se hace el login por email y password, y se añade un listener.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // El usuario ha hecho login correctamente.
                            App.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            invalidateOptionsMenu();
                            cargarFragmentoLista();
                        } else {
                            // El usuario NO ha hecho login correctamente. Se informa
                            // La excepción puede ser:
                            // FirebaseAuthInvalidUserException: No existe o está deshabilitada.
                            // FirebaseAuthInvalidCredentialsException: Password incorrecto.
                            String mensaje = "";
                            if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                mensaje = " El usuario especificado no exite o su cuenta ha sido "
                                        + "deshabilitada.";
                            } else if (task.getException()
                                    instanceof FirebaseAuthInvalidCredentialsException) {
                                mensaje = " El usuario especificado no exite o su cuenta ha sido "
                                        + "deshabilitada.";
                            }
                            Toast.makeText(MainActivity.this, "No se pudo conectar." + mensaje,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onSignup(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Se ha creado el usuario correctamente.
                            // Además se hace el login automáticamente, por lo que se ejecutará
                            // el método onAuthStateChanged del AuthStateListener.
                        } else {
                            // NO se pudo crear el usuario.
                            // La excepción puede ser:
                            //  FirebaseAuthWeakPasswordException: Password débil
                            //  FirebaseAuthInvalidCredentialsException: email mal formado
                            //  FirebaseAuthUserCollisionException: Ya existe un usuario con ese
                            // email.
                            String mensaje = "";
                            if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                mensaje = " El password especificado es demasiado débil.";
                            } else if (task.getException()
                                    instanceof FirebaseAuthInvalidCredentialsException) {
                                mensaje = " El email especificado no es correcto.";
                            } else if (task.getException()
                                instanceof FirebaseAuthUserCollisionException) {
                                mensaje = " Ya existe un usuario con el email especificado.";
                            }
                            Toast.makeText(MainActivity.this,
                                    "Fallo en la creación del usuario." + mensaje,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onLogout() {
        // El usuario se desconecta.
        FirebaseAuth.getInstance().signOut();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.flContenido,
                    new LoginFragment(), TAG_LOGIN_FRAGMENT).commit();
        }
    }

    // Carga el fragmento de la lista.
    private void cargarFragmentoLista() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_LISTA_FRAGMENT) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContenido,
                    new ListaAlumnosFragment(), TAG_LISTA_FRAGMENT).commit();
        }
    }

    // Muestra la actividad de alumno para agregar.
    @Override
    public void onAgregarAlumno() {
        AlumnoActivity.startForResult(this, RC_AGREGAR);
    }

    // Muestra la actividad de notas del alumno. Recibe la key del alumno.
    @Override
    public void onVerNotasAlumno(String key) {
        Toast.makeText(MainActivity.this, "Ver notas de " + key, Toast.LENGTH_SHORT).show();
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
                .findFragmentByTag(
                TAG_LISTA_FRAGMENT);
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
        menu.findItem(R.id.mnuLogout).setVisible(
                FirebaseAuth.getInstance().getCurrentUser() != null);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Se enlaza el listener de autenticación.
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Se desvincula el listener de autenticación.
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
