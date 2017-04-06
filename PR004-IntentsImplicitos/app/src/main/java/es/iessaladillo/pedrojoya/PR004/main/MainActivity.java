package es.iessaladillo.pedrojoya.PR004.main;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import es.iessaladillo.pedrojoya.PR004.R;
import es.iessaladillo.pedrojoya.PR004.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.PR004.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.PR004.utils.IntentUtils;
import es.iessaladillo.pedrojoya.PR004.utils.NetworkUtils;
import es.iessaladillo.pedrojoya.PR004.utils.PermissionUtils;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final int RP_LLAMAR = 1;

    private Button btnNavegar;
    private Button btnBuscar;
    private Button btnLlamar;
    private Button btnMarcar;
    private Button btnMostrarMapa;
    private Button btnBuscarMapa;
    private Button btnMostrarContactos;

    private MainContract.Presenter mPresenter;
    private MessageManager mMessageManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        mMessageManager = new ToastMessageManager();
        initView();
    }

    private void initView() {
        btnNavegar = (Button) findViewById(R.id.btnNavegar);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnLlamar = (Button) findViewById(R.id.btnLlamar);
        btnMarcar = (Button) findViewById(R.id.btnMarcar);
        btnMostrarMapa = (Button) findViewById(R.id.btnMostrarMapa);
        btnBuscarMapa = (Button) findViewById(R.id.btnBuscarMapa);
        btnMostrarContactos = (Button) findViewById(R.id.btnMostrarContactos);

        btnNavegar.setOnClickListener(v -> mPresenter.doNavegar());
        btnBuscar.setOnClickListener(v -> mPresenter.doBuscar());
        btnLlamar.setOnClickListener(v -> quiereLlamar());
        btnMarcar.setOnClickListener(v -> mPresenter.doMarcar());
        btnMostrarMapa.setOnClickListener(v -> mPresenter.doMostrarEnMapa());
        btnBuscarMapa.setOnClickListener(v -> mPresenter.doBuscarEnMapa());
        btnMostrarContactos.setOnClickListener(v -> mPresenter.doMostrarContactos());
    }

    private void quiereLlamar() {
        if (!puedeLlamar()) {
            solicitarPermisoLlamar();
        } else {
            mPresenter.doLlamar();
        }
    }

    private boolean puedeLlamar() {
        return PermissionUtils.hasPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE);
    }

    private void solicitarPermisoLlamar() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                RP_LLAMAR);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == RP_LLAMAR && puedeLlamar()) {
            mPresenter.doLlamar();
        } else {
            // Comprobamos si el usuario ha marcado No volver a preguntar.
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                informar();
            } else {
                mMessageManager.showMessage(btnLlamar, getString(R.string.no_sin_permiso));
            }
        }
    }

    private void informar() {
        Snackbar.make(btnLlamar, R.string.accion_no_disponible, Snackbar.LENGTH_LONG).setAction(
                R.string.configurar,
                view -> IntentUtils.startInstalledAppDetailsActivity(MainActivity
                        .this)).show();
    }

    @Override
    public void showWeb(Uri uri) {
        if (NetworkUtils.isConnectionAvailable(this)) {
            Intent intent = IntentUtils.getViewUriIntent(uri);
            if (IntentUtils.isActivityAvailable(getApplicationContext(), intent)) {
                startActivity(intent);
            } else {
                mMessageManager.showMessage(btnNavegar, getString(R.string.no_hay_navegador));
            }
        } else {
            mMessageManager.showMessage(btnNavegar, getString(R.string.sin_conexion));
        }
    }

    @Override
    public void showBuscar(String texto) {
        if (NetworkUtils.isConnectionAvailable(this)) {
            Intent intent = IntentUtils.getWebSearchIntent(texto);
            if (IntentUtils.isActivityAvailable(getApplicationContext(), intent)) {
                startActivity(intent);
            } else {
                mMessageManager.showMessage(btnNavegar, getString(R.string.no_hay_buscador));
            }
        } else {
            mMessageManager.showMessage(btnNavegar, getString(R.string.sin_conexion));
        }
    }

    @Override
    public void showMarcar(String tel) {
        Intent intent = IntentUtils.getDialIntent(tel);
        if (IntentUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            mMessageManager.showMessage(btnNavegar, getString(R.string.no_hay_dial));
        }
    }

    @Override
    public void showPosicionEnMapa(double longitud, double latitud, int zoom) {
        Intent intent = IntentUtils.getViewInMapIntent(longitud, latitud, zoom);
        if (IntentUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            mMessageManager.showMessage(btnLlamar, getString(R.string.no_hay_aplicaci_n_de_mapas));
        }
    }

    @Override
    public void showBusquedaEnMapa(String texto) {
        Intent intent = IntentUtils.getSearchInMapIntent(texto);
        if (IntentUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            mMessageManager.showMessage(btnLlamar, getString(R.string.no_hay_aplicaci_n_de_mapas));
        }
    }

    @Override
    public void showContactos() {
        Intent intent = IntentUtils.getContactsIntent();
        if (IntentUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            mMessageManager.showMessage(btnMostrarContactos,
                    getString(R.string.no_hay_gestor_de_contactos));
        }
    }

    @Override
    public void showLlamar(String tel) {
        Intent intent = IntentUtils.getCallIntent(tel);
        if (IntentUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            mMessageManager.showMessage(btnMostrarContactos,
                    getString(R.string.no_hay_aplicacion_de_telefono));
        }
    }

}
