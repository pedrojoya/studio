package es.iessaladillo.pedrojoya.pr082;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MainActivity extends AppCompatActivity {

    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_FECHA = "fecha";

    @BindView(R.id.txtNombre)
    public EditText txtNombre;
    @BindView(R.id.pbProgreso)
    public ProgressBar pbProgreso;

    private RequestQueue colaPeticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVistas();
        // Se obtiene la cola de peticiones de Volley.
        colaPeticiones = App.getRequestQueue();
    }

    private void initVistas() {
        pbProgreso.setVisibility(View.INVISIBLE);
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
    @OnClick(R.id.btnBuscar)
    public void buscar() {
        // Si hay un nombre a buscar.
        String nombre = txtNombre.getText().toString();
        if (!TextUtils.equals(nombre, "")) {
            // Si hay conexión a Internet.
            if (isConnectionAvailable()) {
                // Se crea el listener para la respuesta.
                Listener<String> listener = new Listener<String>() {
                    public static final String STR_APROXIMADAMENTE = "Aproximadamente";
                    // Cuando se obtiene la respuesta.
                    @Override
                    public void onResponse(String response) {
                        pbProgreso.setVisibility(View.INVISIBLE);
                        // Se busca en el contenido la palabra
                        // Aproximadamente.
                        String resultado = "";
                        int ini = response.indexOf(STR_APROXIMADAMENTE);
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
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.entradas, resultado), Toast.LENGTH_LONG).show();
                        }

                    }
                };
                // Se crea el listener para el error.
                ErrorListener errorListener = new ErrorListener() {
                    // Cuando se produce un error en la petición.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pbProgreso.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                };
                // Se crea la petición.
                GoogleRequest peticion;
                try {
                    peticion = new GoogleRequest(URLEncoder.encode(
                            nombre, "UTF-8"), listener, errorListener);
                    // Se hace visible el círculo de progreso.
                    pbProgreso.setVisibility(View.VISIBLE);
                    // Se añade la petición a la cola de Volley.
                    colaPeticiones.add(peticion);
                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(getApplicationContext(), R.string.error_codificacion, Toast.LENGTH_LONG)
                            .show();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.no_hay_conexion_a_internet, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    // Envía el nombre a un servidor de eco.
    @OnClick(R.id.btnEco)
    public void eco() {
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
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                };
                // Se crea el listener para error.
                ErrorListener errorListener = new ErrorListener() {
                    // Cuando se produce un error en la petición.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pbProgreso.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG)
                                .show();
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
                Toast.makeText(getApplicationContext(), R.string.no_hay_conexion_a_internet, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

}
