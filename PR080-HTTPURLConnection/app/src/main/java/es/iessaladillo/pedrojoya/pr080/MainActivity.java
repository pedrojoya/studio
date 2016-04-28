package es.iessaladillo.pedrojoya.pr080;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        BuscarAsyncTask.Callbacks, EcoAsyncTask.Callbacks {

    // Vistas.
    private EditText txtNombre;
    private ProgressBar pbProgreso;

    // Variables.
    private BuscarAsyncTask tareaBuscar;
    private EcoAsyncTask tareaEco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
        if (btnBuscar != null) {
            btnBuscar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    buscar();
                }
            });
        }
        Button btnEco = (Button) findViewById(R.id.btnEco);
        if (btnEco != null) {
            btnEco.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    eco();
                }
            });
        }
        pbProgreso = (ProgressBar) findViewById(R.id.pbProgreso);
        if (pbProgreso != null) {
            pbProgreso.setVisibility(View.INVISIBLE);
        }
    }

    // Retorna si hay conexión a la red o no.
    private boolean isConnectionAvailable() {
        // Se obtiene del gestor de conectividad la información de red.
        ConnectivityManager gestorConectividad =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectividad.getActiveNetworkInfo();
        // Se retorna si hay conexión.
        return (infoRed != null && infoRed.isConnected());
    }

    // Crea una tarea para buscar el término en Internet.
    private void buscar() {
        // Si hay un nombre a buscar.
        String nombre = txtNombre.getText().toString();
        if (!TextUtils.equals(nombre, "")) {
            // Si hay conexión a Internet.
            if (isConnectionAvailable()) {
                pbProgreso.setVisibility(View.VISIBLE);
                // Se lanza la tarea asíncrona de búsqueda pasándole el nombre
                // que debe buscarse.
                tareaBuscar = new BuscarAsyncTask(this);
                tareaBuscar.execute(nombre);
            } else {
                mostrarToast(getString(R.string.no_hay_conexion_a_internet));
            }
        }
    }

    // Envía el nombre a un servidor de eco.
    private void eco() {
        // Si hay un nombre a enviar.
        String nombre = txtNombre.getText().toString();
        if (!TextUtils.equals(nombre, "")) {
            // Si hay conexión a Internet.
            if (isConnectionAvailable()) {
                pbProgreso.setVisibility(View.VISIBLE);
                // Se lanza la tarea asíncrona de búsqueda pasándole el nombre
                // que debe buscarse.
                tareaEco = new EcoAsyncTask(this);
                tareaEco.execute(nombre);
            } else {
                mostrarToast(getString(R.string.no_hay_conexion_a_internet));
            }
        }
    }

    // Muestra un toast con duración larga.
    private void mostrarToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
                .show();
    }

    // Al ser pausada la actividad.
    @Override
    protected void onPause() {
        super.onPause();
        // Se cancela la tarea secundaria de buscar.
        if (tareaBuscar != null) {
            tareaBuscar.cancel(true);
            tareaBuscar = null;
        }
        // Se cancela la tarea secundaria de eco.
        if (tareaEco != null) {
            tareaEco.cancel(true);
            tareaEco = null;
        }
    }

    // Cuando se ha finalizado de realizar la tarea secundaria de buscar.
    @Override
    public void onPostExecute(BuscarAsyncTask object, String result) {
        pbProgreso.setVisibility(View.INVISIBLE);
        // Si hay resultado.
        if (!TextUtils.equals(result, "")) {
            // Se muestra un toast con el resultado.
            mostrarToast(getString(R.string.entradas, result));
        }
    }

    // Cuando se ha finalizado de realizar la tarea secundaria de eco.
    @Override
    public void onPostExecute(EcoAsyncTask objeto, String result) {
        pbProgreso.setVisibility(View.INVISIBLE);
        // Si hay resultado.
        if (!TextUtils.equals(result, "")) {
            // Se muestra un toast con el resultado.
            mostrarToast(result);
        }
    }

}
