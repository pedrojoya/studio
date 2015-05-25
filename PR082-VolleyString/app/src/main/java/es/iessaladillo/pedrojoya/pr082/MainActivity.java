package es.iessaladillo.pedrojoya.pr082;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Constantes.
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_FECHA = "fecha";

    // Vistas.
    private EditText txtNombre;
    private ProgressBar pbProgreso;

    // Variables.
    private RequestQueue colaPeticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Se obtiene la cola de peticiones de Volley.
        colaPeticiones = App.getRequestQueue();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    buscar();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        Button btnEco = (Button) findViewById(R.id.btnEco);
        btnEco.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                eco();
            }
        });
        pbProgreso = (ProgressBar) findViewById(R.id.pbProgreso);
        pbProgreso.setVisibility(View.INVISIBLE);
    }

    // Retorna si hay conexión a la red o no.
    private boolean isConnectionAvailable() {
        // Se obtiene del gestor de conectividad la información de red.
        ConnectivityManager gestorConectividad = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectividad.getActiveNetworkInfo();
        // Se retorna si hay conexión.
        return (infoRed != null && infoRed.isConnected());
    }

    // Crea una tarea para buscar el término en Internet.
    private void buscar() throws UnsupportedEncodingException {
        // Si hay un nombre a buscar.
        String nombre = txtNombre.getText().toString();
        if (!TextUtils.equals(nombre, "")) {
            // Si hay conexión a Internet.
            if (isConnectionAvailable()) {
                // Se crea el listener para la respuesta.
                Listener<String> listener = new Listener<String>() {
                    // Cuando se obtiene la respuesta.
                    @Override
                    public void onResponse(String response) {
                        pbProgreso.setVisibility(View.INVISIBLE);
                        // Se busca en el contenido la palabra
                        // Aproximadamente.
                        String resultado = "";
                        int ini = response.indexOf("Aproximadamente");
                        if (ini != -1) {
                            // Se busca el siguiente espacio en blanco después
                            // de Aproximadamente.
                            int fin = response.indexOf(" ", ini + 16);
                            // El resultado corresponde a lo que sigue a
                            // Aproximadamente, hasta el siguiente
                            // espacio en blanco.
                            resultado = response.substring(ini + 16, fin);
                        }
                        // Si hay resultado.
                        if (!TextUtils.equals(resultado, "")) {
                            // Se muestra un toast con el resultado.
                            mostrarToast(resultado + " "
                                    + getString(R.string.entradas));
                        }

                    }
                };
                // Se crea el listener para el error.
                ErrorListener errorListener = new ErrorListener() {
                    // Cuando se produce un error en la petición.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pbProgreso.setVisibility(View.INVISIBLE);
                        mostrarToast(error.getMessage());
                    }
                };
                // Se crea la petición.
                GoogleRequest peticion = new GoogleRequest(URLEncoder.encode(
                        nombre, "UTF-8"), listener, errorListener);
                // Se hace visible el círculo de progreso.
                pbProgreso.setVisibility(View.VISIBLE);
                // Se añade la petición a la cola de Volley.
                colaPeticiones.add(peticion);
            } else {
                mostrarToast(getString(R.string.no_hay_conexion_a_internet));
            }
        }
    }

    // Envía el nombre a un servidor de eco.
    private void eco() {
        // Si hay un nombre a enviar.
        final String nombre = txtNombre.getText().toString();
        if (!TextUtils.equals(nombre, "")) {
            // Si hay conexión a Internet.
            if (isConnectionAvailable()) {
                // Se crea el mapa de parámetros.
                SimpleDateFormat formateador = new SimpleDateFormat(
                        "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                Map<String, String> params = new HashMap<>();
                params.put(KEY_NOMBRE, nombre);
                params.put(KEY_FECHA, formateador.format(new Date()));
                // Se crea el listener para la respuesta.
                Listener<String> listener = new Listener<String>() {
                    // Cuando se obtiene la respuesta.
                    @Override
                    public void onResponse(String response) {
                        pbProgreso.setVisibility(View.INVISIBLE);
                        // Si hay resultado.
                        if (!TextUtils.equals(response, "")) {
                            // Se muestra un toast con el resultado.
                            mostrarToast(response);
                        }
                    }
                };
                // Se crea el listener para error.
                ErrorListener errorListener = new ErrorListener() {
                    // Cuando se produce un error en la petición.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pbProgreso.setVisibility(View.INVISIBLE);
                        mostrarToast(error.getMessage());
                    }
                };
                // Se crea la petición.
                EcoRequest peticion = new EcoRequest(params, listener,
                        errorListener);
                // Se hace visible el círculo de progreso.
                pbProgreso.setVisibility(View.VISIBLE);
                // Se añade la petición a la cola de Volley.
                colaPeticiones.add(peticion);
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

}
