package es.iessaladillo.pedrojoya.pr189;

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
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

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

        btnNavegar.setOnClickListener(this::navegar);
        btnBuscar.setOnClickListener(this::buscar);
        btnLlamar.setOnClickListener(v -> quiereLlamar());
        btnMarcar.setOnClickListener(this::marcar);
        btnMostrarMapa.setOnClickListener(this::mostrarEnMapa);
        btnBuscarMapa.setOnClickListener(this::buscarEnMapa);
        btnMostrarContactos.setOnClickListener(this::mostrarContactos);
    }

    @SuppressWarnings({"WeakerAccess", "UnusedParameters"})
    public void navegar(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.genbeta.com"));
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_navegador, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings({"WeakerAccess", "UnusedParameters"})
    public void buscar(View v) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, "IES Saladillo");
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_buscador, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void quiereLlamar() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+34)123456789"));
        if (estaDisponible(this, intent)) {
            if (!puedeLlamar()) {
                solicitarPermisoLlamar();
            } else {
                llamar();
            }
        } else {
            Toast.makeText(this, R.string.no_se_puede_llamar, Toast.LENGTH_SHORT).show();
        }
    }

    private void llamar() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+34)123456789")));
    }

    @SuppressWarnings("UnusedParameters")
    private void marcar(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+34)12345789"));
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_dial, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings({"WeakerAccess", "UnusedParameters"})
    public void mostrarEnMapa(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:36.1121,-5.44347?z=19"));
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_aplicaci_n_de_mapas, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings({"WeakerAccess", "UnusedParameters"})
    public void buscarEnMapa(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=duque de rivas, " + "Algeciras"));
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_aplicaci_n_de_mapas, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings({"WeakerAccess", "UnusedParameters"})
    public void mostrarContactos(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
        if (estaDisponible(this, intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_hay_gestor_de_contactos, Toast.LENGTH_SHORT).show();
        }
    }

    // Retorna si hay alguna actividad que pueda recibir el intent.
    private boolean estaDisponible(Context ctx, Intent intent) {
        final PackageManager gestorPaquetes = ctx.getPackageManager();
        List<ResolveInfo> listaApps = gestorPaquetes.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return listaApps.size() > 0;
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
        Snackbar.make(btnLlamar, R.string.accion_no_disponible, Snackbar.LENGTH_LONG)
                .setAction(R.string.configurar, view -> startInstalledAppDetailsActivity(this))
                .show();

    }

    private static void startInstalledAppDetailsActivity(@NonNull final Activity context) {
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
