package pedrojoya.iessaladillo.es.pr107;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MeteoFragment extends Fragment {

    private String mLocalidad;
    private String mUnidad;

    // Interfaz de interacción con el fragmento.
    public interface CallbackListener {
        public void onItemClick(String item);
    }

    // Constantes.
    private static final String ARG_CIUDAD = "ciudad";

    // Variables a nivel de clase.
    private String mCiudad;
    private CallbackListener mListener;
    private ArrayAdapter<String> mAdaptador;

    // Vistas.
    private ListView lstMeteo;

    // Método factoría del fragmento.
    public static MeteoFragment newInstance(String ciudad) {
        MeteoFragment fragment = new MeteoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CIUDAD, ciudad);
        fragment.setArguments(args);
        return fragment;
    }

    // Constructor público vacío.
    public MeteoFragment() {
        // Es obligatorio tener un constructor público vacío.
    }

    // Cuando se crea el menú de opciones.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_meteo, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Al pulsar un ítem de menú.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuRefrescar:
                actualizarMeteo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Refresca los datos de la lista.
    private void actualizarMeteo() {
        // Se obtienen las preferencias.
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mLocalidad = preferencias.getString(getActivity().getString(R.string.prefLocalidadKey), "Madrid");
        mUnidad = preferencias.getString(getActivity().getString(R.string.prefUnidadKey), "metric");
        ObtenerDatosMeteo tarea = new ObtenerDatosMeteo();
        tarea.execute(mLocalidad);
    }

    // Al crearse el fragmento.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // El fragmento aporta ítems de menú.
        setHasOptionsMenu(true);
        // Se recuperan los argumentos.
        if (getArguments() != null) {
            mCiudad = getArguments().getString(ARG_CIUDAD);
        }
    }

    // Retorna la vista que debe pintarse para el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meteo, container, false);
    }

    // Cuando se inicia la actividad.
    @Override
    public void onStart() {
        super.onStart();
        actualizarMeteo();
    }

    // Al crearse completamente la actividad.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        // Se carga de datos el adaptador para la lista.
        lstMeteo = (ListView) getView().findViewById(R.id.lstMeteo);
        mAdaptador = new ArrayAdapter<String>(
                getActivity(),
                R.layout.fragment_meteo_item,
                R.id.lblMeteo,
                new ArrayList<String>());
        lstMeteo.setAdapter(mAdaptador);
        lstMeteo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onItemClick(mAdaptador.getItem(position));
                }
            }
        });
    }

    // Cuando el fragmento se vincula con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Si la actividad no implementa el listener se genera un error.
        try {
            mListener = (CallbackListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " debe impelementar MeteoFragment.CallbackListener");
        }
    }

    // Cuando se desvincula el fragmento de la actividad.
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Tarea asíncrona que obtiene los datos para la lista.
    public class ObtenerDatosMeteo extends AsyncTask<String, Void, String[]> {

        // Recibe el nombre de la localidad y la unidad de medida.
        @Override
        protected String[] doInBackground(String... params) {
            // Si no hay parámetros no se hace nada.
            if (params.length == 0) {
                return null;
            }
            HttpURLConnection conexion = null;
            BufferedReader lector = null;
            String resultadoJson = null;
            try {
                // Se construye la URL de consulta para la API OpenWeatherMap.
                final String URL_BASE = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String PARAM_TERMINO = "q";
                final String PARAM_FORMATO = "mode";
                final String PARAM_UNIDADES = "units";
                final String PARAM_DIAS = "cnt";
                Uri uri = Uri.parse(URL_BASE)
                        .buildUpon()
                        .appendQueryParameter(PARAM_TERMINO, params[0])
                        .appendQueryParameter(PARAM_FORMATO, "json")
                        .appendQueryParameter(PARAM_UNIDADES, "metric")
                        .appendQueryParameter(PARAM_DIAS, "7")
                        .build();
                URL url = new URL(uri.toString());
                // Se realiza la petición GET.
                conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("GET");
                conexion.connect();
                // Se obtiene el flujo de entrada de la conexión y se crea el lector.
                InputStream inputStream = conexion.getInputStream();
                if (inputStream == null) {
                    // Nothing to do.
                    resultadoJson = null;
                }
                lector = new BufferedReader(new InputStreamReader(inputStream));
                // Se lee línea a línea el resultado.
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = lector.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    resultadoJson = null;
                }
                resultadoJson = buffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                resultadoJson = null;
            } finally {
                if (conexion != null) {
                    conexion.disconnect();
                }
                if (lector != null) {
                    try {
                        lector.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            // Se parsea el resultado y retornamos el array de strings resultante.
            try {
                return getWeatherDataFromJson(resultadoJson, 7);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            if (strings != null) {
                mAdaptador.clear();
                for (String item : strings) {
                    mAdaptador.add(item);
                }
            }
        }

    }

    /* The date/time conversion code is going to be moved outside the asynctask later,
 * so for convenience we're breaking it out into its own method now.
 */
    private String getReadableDateString(long time) {
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
        return format.format(date).toString();
    }

    /**
     * Prepare the weather high/lows for presentation.
     */
    private String formatHighLows(double high, double low) {
        // Se pasa al sistema imperial si así se ha configurado.
        if (mUnidad.equals("imperial")) {
            high = (high * 1.8) + 32;
            low = (low * 1.8) + 32;
        }
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p/>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        final String OWM_DATETIME = "dt";
        final String OWM_DESCRIPTION = "main";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        String[] resultStrs = new String[numDays];
        for (int i = 0; i < weatherArray.length(); i++) {
            // For now, using the format "Day, description, hi/low"
            String day;
            String description;
            String highAndLow;

            // Get the JSON object representing the day
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            // The date/time is returned as a long.  We need to convert that
            // into something human-readable, since most people won't read "1400356800" as
            // "this saturday".
            long dateTime = dayForecast.getLong(OWM_DATETIME);
            day = getReadableDateString(dateTime);

            // description is in a child array called "weather", which is 1 element long.
            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            // Temperatures are in a child object called "temp".  Try not to name variables
            // "temp" when working with temperature.  It confuses everybody.
            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            double high = temperatureObject.getDouble(OWM_MAX);
            double low = temperatureObject.getDouble(OWM_MIN);

            highAndLow = formatHighLows(high, low);
            resultStrs[i] = day + " - " + description + " - " + highAndLow;
        }

        return resultStrs;
    }

}
