package es.iessaladillo.pedrojoya.pr109;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import es.iessaladillo.pedrojoya.pr109.Model.Usuario;
import es.iessaladillo.pedrojoya.pr109.Model.UsuarioNuevo;
import es.iessaladillo.pedrojoya.pr109.api.ApiService;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public class LoginActivity extends ActionBarActivity {

    public static final String PREFS_USUARIO = "usuario";
    public static final String PREF_OBJECT_ID = "objectId";
    public static final String PREF_SESSION_TOKEN = "sessionToken";

    @InjectView(R.id.lblConcepto)
    TextView mLblUsuario;
    @InjectView(R.id.txtConcepto)
    EditText mTxtUsuario;
    @InjectView(R.id.lblResponsable)
    TextView mLblClave;
    @InjectView(R.id.txtResponsable)
    EditText mTxtClave;
    @InjectView(R.id.btnRegistrar)
    Button mBtnRegistrar;
    @InjectView(R.id.btnConectar)
    Button mBtnConectar;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        // Se cambia el color del TextView dependiendo de si el EditText
        // correspondiente tiene el foco o no.
        mTxtUsuario.setOnFocusChangeListener(new OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(mLblUsuario, hasFocus);
            }

        });
        mTxtClave.setOnFocusChangeListener(new OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(mLblClave, hasFocus);
            }

        });
        // btnAceptar sólo accesible si hay datos en txtUsuario y txtClave.
        mTxtUsuario.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            // Después de haber cambiado el texto.
            @Override
            public void afterTextChanged(Editable s) {
                // Botones disponibles sólo si hay datos.
                checkDatos();
                // lblUsuario visible sólo si txtUsuario tiene datos.
                checkVisibility(mTxtUsuario, mLblUsuario);
            }

        });
        mTxtClave.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            // Después de haber cambiado el texto.
            @Override
            public void afterTextChanged(Editable s) {
                // Botones disponibles sólo si hay datos.
                checkDatos();
                // lblClave visible sólo si tiene datos.
                checkVisibility(mTxtClave, mLblClave);
            }

        });
        // Comprobaciones iniciales.
        setColorSegunFoco(mLblUsuario, true);
        checkDatos();
        checkVisibility(mTxtClave, mLblClave);
        checkVisibility(mTxtUsuario, mLblUsuario);
    }

    @OnClick(R.id.btnConectar)
    public void conectar() {
        App.getApiService().login(mTxtUsuario.getText().toString(), mTxtClave.getText().toString());
    }

    @OnClick(R.id.btnRegistrar)
    public void registrar() {
        App.getApiService().signUp(new UsuarioNuevo(mTxtUsuario.getText().toString(), mTxtClave.getText().toString()));
    }

    // Cuando se produce el evento de que el usuario ha hecho login.
    @Subscribe
    public void onUsuarioLoggedIn(ApiService.UsuarioLoggedInEvent event) {
        guardarSesion();
        mostrarPrincipal();
    }

    // Cuando se produce el evento de que el usuario se ha registrado correctamente (con login incluido).
    @Subscribe
    public void onUsuarioSignedUp(ApiService.UsuarioSignedUpEvent event) {
        guardarSesion();
        mostrarPrincipal();
    }

    private void guardarSesion() {
        SharedPreferences preferencias =  getSharedPreferences(PREFS_USUARIO, MODE_PRIVATE);
        Usuario usuario = App.getUsuario();
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(PREF_OBJECT_ID, usuario.getObjectId());
        editor.putString(PREF_SESSION_TOKEN, usuario.getSessionToken());
        editor.commit();
    }

    // Si se ha producido un evento de error en la petición.
    @Subscribe
    public void onApiError(ApiService.ApiErrorEvent event) {
        RetrofitError error = event.getError();
        switch (error.getKind()) {
            // Si hay un problema con la red.
            case NETWORK:
                Toast.makeText(this, "Comprueba que tienes internet", Toast.LENGTH_SHORT).show();
                break;
            case HTTP:
                int status = error.getResponse().getStatus();
                if (status == HttpStatus.SC_FORBIDDEN) {
                    Toast.makeText(this, "Hay que estar logueado", Toast.LENGTH_SHORT).show();
                } else if (status == HttpStatus.SC_BAD_REQUEST) {
                    try {
                        JSONObject jError = new JSONObject(new String(((TypedByteArray) error.getResponse().getBody()).getBytes()));
                        int code = jError.getInt("code");
                        if (code == 202) {
                            Toast.makeText(this, "Ese usuario ya existe", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (status == HttpStatus.SC_NOT_FOUND) {
                    Toast.makeText(this, "Usuario desconocido", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Log.d("Mia", error.getMessage());
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Se registra la actividad en el bus.
        App.getEventBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // La actividad se desregistra del bus.
        App.getEventBus().unregister(this);
    }

    // Activa o desactiva el botón de Aceptar dependiendo de si hay datos.
    private void checkDatos() {
        boolean permitir = !TextUtils.isEmpty(mTxtUsuario.getText().toString()) && !TextUtils.isEmpty(mTxtClave.getText().toString());
        mBtnConectar.setEnabled(permitir);
        mBtnRegistrar.setEnabled(permitir);
    }

    // TextView visible sólo si EditText tiene datos.
    private void checkVisibility(EditText txt, TextView lbl) {
        if (TextUtils.isEmpty(txt.getText().toString())) {
            lbl.setVisibility(View.INVISIBLE);
        } else {
            lbl.setVisibility(View.VISIBLE);
        }
    }

    // Establece el color y estilo del TextView dependiendo de si el
    // EditText correspondiente tiene el foco o no.
    private void setColorSegunFoco(TextView lbl, boolean hasFocus) {
        if (hasFocus) {
            lbl.setTextColor(getResources().getColor(R.color.edittext_focused));
            lbl.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lbl.setTextColor(getResources()
                    .getColor(R.color.edittext_notfocused));
            lbl.setTypeface(Typeface.DEFAULT);
        }
    }

    // Muestra la actividad principal.
    private void mostrarPrincipal() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}



