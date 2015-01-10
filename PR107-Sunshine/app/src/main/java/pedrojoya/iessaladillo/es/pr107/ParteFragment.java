package pedrojoya.iessaladillo.es.pr107;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ParteFragment extends Fragment {

    // Constantes.
    private static final String ARG_CIUDAD = "ciudad";

    // Variables a nivel de clase.
    private String mCiudad;
    private CallbackListener mListener;
    private ArrayAdapter<String> mAdaptador;

    // Vistas.
    private ListView lstParte;

    // Método factoría del fragmento.
    public static ParteFragment newInstance(String ciudad) {
        ParteFragment fragment = new ParteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CIUDAD, ciudad);
        fragment.setArguments(args);
        return fragment;
    }

    // Constructor público vacío.
    public ParteFragment() {
        // Es obligatorio tener un constructor público vacío.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_parte, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuRefrescar:
                refrescar();
        }
        return super.onOptionsItemSelected(item);
    }

    private void refrescar() {
        ObtenerDatosParte tarea = new ObtenerDatosParte();
        tarea.execute("Madrid");
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
        return inflater.inflate(R.layout.fragment_parte, container, false);
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
        lstParte = (ListView) getView().findViewById(R.id.lstParte);
        List<String> datosParte = getDatosParte();
        mAdaptador = new ArrayAdapter<String>(
                getActivity(),
                R.layout.fragment_parte_item,
                R.id.lblParte,
                datosParte);
        lstParte.setAdapter(mAdaptador);
    }

    // Retorna los datos para la lista.
    private List<String> getDatosParte() {
        String[] forecastArray = {
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 70/46",
                "Weds - Cloudy - 72/63",
                "Thurs - Rainy - 64/51",
                "Fri - Foggy - 70/46",
                "Sat - Sunny - 76/68"};
        return new ArrayList<String>(Arrays.asList(forecastArray));
    }

    public void onItemClick(int item) {
        if (mListener != null) {
            mListener.onItemClick(item);
        }
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
                    + " debe impelementar ParteFragment.CallbackListener");
        }
    }

    // Cuando se desvincula el fragmento de la actividad.
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface CallbackListener {
        public void onItemClick(int item);
    }

    public class ObtenerDatosParte extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String ciudad = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            Log.d(getString(R.string.app_name), "Ejecutando tarea");

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=" + ciudad + "&mode=json&units=metric&cnt=7");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                forecastJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return null;
        }
    }

}
