package es.iessaladillo.pedrojoya.PR004;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        OnClickListener {

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        // La actividad responderá al pulsar sobre cualquier botón.
        Button btnNavegar = (Button) findViewById(R.id.btnNavegar);
        if (btnNavegar != null) {
            btnNavegar.setOnClickListener(this);
        }
        Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
        if (btnBuscar != null) {
            btnBuscar.setOnClickListener(this);
        }
        Button btnLlamar = (Button) findViewById(R.id.btnLlamar);
        if (btnLlamar != null) {
            btnLlamar.setOnClickListener(this);
        }
        Button btnMarcar = (Button) findViewById(R.id.btnMarcar);
        if (btnMarcar != null) {
            btnMarcar.setOnClickListener(this);
        }
        Button btnMostrarMapa = (Button) findViewById(R.id.btnMostrarMapa);
        if (btnMostrarMapa != null) {
            btnMostrarMapa.setOnClickListener(this);
        }
        Button btnBuscarMapa = (Button) findViewById(R.id.btnBuscarMapa);
        if (btnBuscarMapa != null) {
            btnBuscarMapa.setOnClickListener(this);
        }
        Button btnMostrarContactos = (Button) findViewById(R.id.btnMostrarContactos);
        if (btnMostrarContactos != null) {
            btnMostrarContactos.setOnClickListener(this);
        }
    }

    // Al hacer click sobre algún botón.
    @Override
    public void onClick(View v) {
        Intent intent;
        // Dependiendo del botón pulsado.
        switch (v.getId()) {
            case R.id.btnNavegar:
                // Acción--> VER. Uri--> URL.
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.genbeta.com"));
                if (estaDisponible(this, intent)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.no_hay_navegador,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnBuscar:
                // Acción--> BUSCAR EN INTERNET. Extra -> Término de consulta.
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "IES Saladillo");
                if (estaDisponible(this, intent)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.no_hay_buscador,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnLlamar:
                // Acción--> LLAMAR. Uri--> tel:num.
                intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:(+34)123456789"));
                if (estaDisponible(this, intent)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.no_se_puede_llamar,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnMarcar:
                // Acción--> MARCAR. Uri--> tel:num.
                intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:(+34)12345789"));
                if (estaDisponible(this, intent)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.no_hay_dial, Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case R.id.btnMostrarMapa:
                // Acción--> VER. Uri--> geo:latitud,longitud?z=zoom.
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:36.1121,-5.44347?z=19"));
                if (estaDisponible(this, intent)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.no_hay_aplicaci_n_de_mapas,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnBuscarMapa:
                // Acción--> VER. Uri--> geo:latitud,longitud?q=consulta.
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=duque de rivas, Algeciras"));
                if (estaDisponible(this, intent)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.no_hay_aplicaci_n_de_mapas,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnMostrarContactos:
                // Acción--> VER. Uri--> Accederá al proveedor de contenidos de la
                // aplicación de contactos.
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("content://contacts/people/"));
                if (estaDisponible(this, intent)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.no_hay_gestor_de_contactos,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    // Retorna si hay alguna actividad que pueda recibir el intent.
    private boolean estaDisponible(Context ctx, Intent intent) {
        final PackageManager gestorPaquetes = ctx.getPackageManager();
        List<ResolveInfo> listaApps = gestorPaquetes.queryIntentActivities(
                intent, PackageManager.MATCH_DEFAULT_ONLY);
        return listaApps.size() > 0;
    }

}