package es.iessaladillo.pedrojoya.PR004.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import es.iessaladillo.pedrojoya.PR004.R;
import es.iessaladillo.pedrojoya.PR004.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.PR004.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.PR004.utils.IntentUtils;
import es.iessaladillo.pedrojoya.PR004.utils.NetworkUtils;

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

        btnNavegar.setOnClickListener(this::navegar);
        btnBuscar.setOnClickListener(this::buscar);
        btnLlamar.setOnClickListener(this::quiereLlamar);
        btnMarcar.setOnClickListener(this::marcar);
        btnMostrarMapa.setOnClickListener(this::mostrarEnMapa);
        btnBuscarMapa.setOnClickListener(this::buscarEnMapa);
        btnMostrarContactos.setOnClickListener(this::mostrarContactos);
    }

    private void navegar(View v) {
        mPresenter.doNavegar();
    }

    private void buscar(View v) {
        mPresenter.doBuscar();
    }

    private void quiereLlamar(View v) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+34)123456789"));
        if (IntentUtils.isActivityAvailable(getApplicationContext(), intent)) {
            if (!puedeLlamar()) {
                solicitarPermisoLlamar();
            } else {
                llamar();
            }
        } else {
            mMessageManager.showMessage(v, getString(R.string.no_se_puede_llamar));
        }
    }

    private void llamar() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            mMessageManager.showMessage(btnLlamar, getString(R.string.no_sin_permiso));
        } else {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+34)123456789")));
        }
    }

    private void marcar(View v) {
        mPresenter.doMarcar();
    }

    private void mostrarEnMapa(View v) {
        mPresenter.doMostrarEnMapa();
    }

    private void buscarEnMapa(View v) {
        mPresenter.doBuscarEnMapa();
    }

    @SuppressWarnings("UnusedParameters")
    private void mostrarContactos(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
        if (IntentUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            mMessageManager.showMessage(btnLlamar, getString(R.string.no_hay_gestor_de_contactos));
        }
    }


    @SuppressWarnings("SameParameterValue")
    private boolean tienePermiso(String permissionName) {
        return ContextCompat.checkSelfPermission(this, permissionName)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean puedeLlamar() {
        return tienePermiso(Manifest.permission.CALL_PHONE);
    }

    private void solicitarPermisoLlamar() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                RP_LLAMAR);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == RP_LLAMAR && puedeLlamar()) {
            llamar();
        } else {
            // Comprobamos si el usuario ha marcado No volver a preguntar.
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                informar();
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

}
