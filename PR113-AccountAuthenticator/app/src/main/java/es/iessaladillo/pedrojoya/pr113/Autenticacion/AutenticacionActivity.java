package es.iessaladillo.pedrojoya.pr113.Autenticacion;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
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
import es.iessaladillo.pedrojoya.pr113.Actividades.MainActivity;
import es.iessaladillo.pedrojoya.pr113.App;
import es.iessaladillo.pedrojoya.pr113.Model.Usuario;
import es.iessaladillo.pedrojoya.pr113.Model.UsuarioNuevo;
import es.iessaladillo.pedrojoya.pr113.R;
import es.iessaladillo.pedrojoya.pr113.api.ApiService;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public class AutenticacionActivity extends AccountAuthenticatorActivity {

    public static final String PREFS_USUARIO = "usuario";
    public static final String PREF_OBJECT_ID = "objectId";
    public static final String PREF_SESSION_TOKEN = "sessionToken";

    public static final String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String ARG_AUTH_TYPE = "AUTH_TYPE";
    public static final String ARG_IS_ADDING_NEW_ACCOUNT = "ADDING_NEW_ACCOUNT";
    public static final String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";

    public final static String PARAM_USER_PASS = "USER_PASS";

    @InjectView(R.id.lblUsuario)
    TextView mLblUsuario;
    @InjectView(R.id.txtUsuario)
    EditText mTxtUsuario;
    @InjectView(R.id.lblClave)
    TextView mLblClave;
    @InjectView(R.id.txtClave)
    EditText mTxtClave;
    @InjectView(R.id.btnRegistrar)
    Button mBtnRegistrar;
    @InjectView(R.id.btnConectar)
    Button mBtnConectar;

    private String mAuthTokenType;
    private String mAccountType;
    private boolean mIsAddingNewAccount;
    private AccountManager mAccountManager;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        // Se obtiene el gestor de cuentas.
        mAccountManager = AccountManager.get(getBaseContext());
        // Se obtienen los datos con lo que ha sido llamada la actividad.
        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        if (accountName != null) {
            mTxtUsuario.setText(accountName);
        }
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = Autenticador.AUTHTOKEN_TYPE_FULL_ACCESS;
        mAccountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
        mIsAddingNewAccount = getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false);
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
        String username = mTxtUsuario.getText().toString();
        String password = mTxtClave.getText().toString();
            App.getApiService().login(username, password);
    }

    private void finalizarLogin(String username, String password, String authToken) {
        // Se crea el Intent que debe retornarse
        Intent res = new Intent();
        res.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
        res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, mAccountType);
        res.putExtra(AccountManager.KEY_AUTHTOKEN, authToken);
        res.putExtra(PARAM_USER_PASS, password);
        // Se crea el objeto Account correspondiente a la cuenta.
        final Account account = new Account(username, mAccountType);
        // Si se debe añadir la cuenta al gestor de cuentas.
        if (mIsAddingNewAccount) {
            // Se añade la cuenta explícitamente y se establece su token de autenticación.
            mAccountManager.addAccountExplicitly(account, password, null);
            mAccountManager.setAuthToken(account, mAuthTokenType, authToken);
        } else {
            // Si bo se debe añadir, se actualiza el password de la cuenta.
            mAccountManager.setPassword(account, password);
        }
        // Se retorna al autenticador el bundle con los extras del resultado.
        setAccountAuthenticatorResult(res.getExtras());
        // Se retorna como resultado de la actividad el intent de resultado.
        setResult(RESULT_OK, res);
        // Se finaliza la actividad.
        finish();
    }

    @OnClick(R.id.btnRegistrar)
    public void registrar() {
        String username = mTxtUsuario.getText().toString();
        String password = mTxtClave.getText().toString();
        try {
            App.getApiService().signUp(new UsuarioNuevo(username, password));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cuando el usuario se ha conectado.
    @Subscribe
    public void onUsuarioLoggedIn(ApiService.UsuarioLoggedInEvent event) {
        String username = mTxtUsuario.getText().toString();
        String password = mTxtClave.getText().toString();
        String authToken = event.getUsuario().getSessionToken();
        guardarSesion();
        finalizarLogin(username, password, authToken);
    }

    // Cuando el usuario se ha registrado.
    @Subscribe
    public void onUsuarioSignedUp(ApiService.UsuarioSignedUpEvent event) {
        String username = mTxtUsuario.getText().toString();
        String password = mTxtClave.getText().toString();
        String authToken = event.getUsuario().getSessionToken();
        guardarSesion();
        finalizarLogin(username, password, authToken);
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



