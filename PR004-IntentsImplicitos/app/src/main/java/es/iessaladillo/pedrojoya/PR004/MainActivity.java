package es.iessaladillo.pedrojoya.PR004;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements
        OnClickListener {

    private static final int RP_LLAMAR = 1;

    private Button btnNavegar;
    private Button btnBuscar;
    private Button btnLlamar;
    private Button btnMarcar;
    private Button btnMostrarMapa;
    private Button btnBuscarMapa;
    private Button btnMostrarContactos;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        btnNavegar.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
        btnLlamar.setOnClickListener(this);
        btnMarcar.setOnClickListener(this);
        btnMostrarMapa.setOnClickListener(this);
        btnBuscarMapa.setOnClickListener(this);
        btnMostrarContactos.setOnClickListener(this);
    }

    // Al hacer click sobre algún botón.
    @Override
    public void onClick(View v) {
        // Dependiendo del botón pulsado.
        switch (v.getId()) {
            case R.id.btnNavegar:
                navegar();
                break;
            case R.id.btnBuscar:
                buscar();
                break;
            case R.id.btnLlamar:
                quiereLlamar();
                break;
            case R.id.btnMarcar:
                marcar();
                break;
            case R.id.btnMostrarMapa:
                mostrarEnMapa();
                break;
            case R.id.btnBuscarMapa:
                buscarEnMapa();
                break;
            case R.id.btnMostrarContactos:
                mostrarContactos();
                break;
            default:
        }
    }

    private void navegar() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.genbeta.com"));
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_navegador,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void buscar() {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, "IES Saladillo");
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_buscador,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void quiereLlamar() {
        Intent intent = new Intent(Intent.ACTION_CALL,
                Uri.parse("tel:(+34)123456789"));
        if (estaDisponible(this, intent)) {
            if (!puedeLlamar()) {
                solicitarPermisoLlamar();
            } else {
                llamar();
            }
        } else {
            Toast.makeText(this, R.string.no_se_puede_llamar,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void llamar() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL,
                Uri.parse("tel:(+34)123456789")));
    }

    private void marcar() {
        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:(+34)12345789"));
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_dial, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void mostrarEnMapa() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:36.1121,-5.44347?z=19"));
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_aplicaci_n_de_mapas,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarEnMapa() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=duque de rivas, Algeciras"));
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_aplicaci_n_de_mapas,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarContactos() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("content://contacts/people/"));
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_gestor_de_contactos,
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Retorna si hay alguna actividad que pueda recibir el intent.
    private boolean estaDisponible(Context ctx, Intent intent) {
        final PackageManager gestorPaquetes = ctx.getPackageManager();
        List<ResolveInfo> listaApps = gestorPaquetes.queryIntentActivities(
                intent, PackageManager.MATCH_DEFAULT_ONLY);
        return listaApps.size() > 0;
    }

    @SuppressWarnings("SameParameterValue")
    private boolean tienePermiso(String permissionName) {
        return ContextCompat.checkSelfPermission(this, permissionName) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private boolean puedeLlamar() {
        return tienePermiso(Manifest.permission.CALL_PHONE);
    }

    private void solicitarPermisoLlamar() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE},
                RP_LLAMAR);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
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
        Snackbar.make(btnLlamar,
                R.string.accion_no_disponible, Snackbar.LENGTH_LONG)
                .setAction(R.string.configurar,
                        view -> startInstalledAppDetailsActivity(MainActivity
                                .this))
                .show();

    }

    private static void startInstalledAppDetailsActivity(
            @NonNull final Activity context) {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        // Para que deje rastro en la pila de actividades se añaden flags.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

}