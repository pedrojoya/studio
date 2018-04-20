package es.iessaladillo.pedrojoya.pr080.ui.main;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.pr080.R;
import es.iessaladillo.pedrojoya.pr080.utils.NetworkUtils;

public class MainFragment extends Fragment {

    private EditText txtName;
    private ProgressBar pbProgress;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnSearch;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnEcho;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
    }

    private void initViews(View view) {
        txtName = ViewCompat.requireViewById(view, R.id.txtName);
        btnSearch = ViewCompat.requireViewById(view, R.id.btnSearch);
        btnEcho = ViewCompat.requireViewById(view, R.id.btnEcho);
        pbProgress = ViewCompat.requireViewById(view, R.id.pbProgress);

        btnSearch.setOnClickListener(v -> search());
        btnEcho.setOnClickListener(v -> echo());
    }

    private void search() {
        String name = txtName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            return;
        }
        if (NetworkUtils.isConnectionAvailable(requireActivity())) {
            pbProgress.setVisibility(View.VISIBLE);
            (new SearchAsyncTask(this)).execute(name);
        } else {
            showNoConnectionAvailable();
        }
    }

    private void echo() {
        String name = txtName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            return;
        }
        if (NetworkUtils.isConnectionAvailable(requireActivity())) {
            pbProgress.setVisibility(View.VISIBLE);
            (new EchoAsyncTask(this)).execute(name);
        } else {
            showNoConnectionAvailable();
        }

    }

    private void showNoConnectionAvailable() {
        Toast.makeText(requireActivity(), getString(R.string.main_fragment_no_connection),
                Toast.LENGTH_SHORT).show();
    }

    private void showResult(String result) {
        pbProgress.setVisibility(View.INVISIBLE);
        Toast.makeText(requireActivity(), result, Toast.LENGTH_SHORT).show();
    }

    static class SearchAsyncTask extends AsyncTask<String, Void, String> {

        private final WeakReference<MainFragment> mainFragmentWeakReference;

        SearchAsyncTask(MainFragment mainFragment) {
            mainFragmentWeakReference = new WeakReference<>(mainFragment);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String name = params[0];
            try {
                URL url = new URL(
                        "https://www.google.es/search?hl=es&q=\"" + URLEncoder.encode(name, "UTF-8")
                                + "\"");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                // Needed for Google search.
                httpURLConnection.setRequestProperty("User-Agent",
                        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    result = extractResultFromContent(
                            NetworkUtils.readContent(httpURLConnection.getInputStream()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        private String extractResultFromContent(String contenido) {
            String resultado = "";
            int ini = contenido.indexOf("Aproximadamente");
            if (ini != -1) {
                // Se busca el siguiente espacio en blanco después de
                // Aproximadamente.
                int fin = contenido.indexOf(" ", ini + 16);
                // El resultado corresponde a lo que sigue a
                // Aproximadamente, hasta el siguiente espacio en blanco.
                resultado = contenido.substring(ini + 16, fin);
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(String result) {
            MainFragment mainFragment = mainFragmentWeakReference.get();
            if (mainFragment != null) {
                mainFragment.showResult(result);
            }
        }

    }

    static class EchoAsyncTask extends AsyncTask<String, Void, String> {

        private static final String KEY_NAME = "nombre";
        private static final String KEY_DATE = "fecha";

        private final WeakReference<MainFragment> mainFragmentWeakReference;
        private final SimpleDateFormat simpleDateFormat;

        EchoAsyncTask(MainFragment mainFragment) {
            mainFragmentWeakReference = new WeakReference<>(mainFragment);
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String name = params[0];
            try {
                URL url = new URL("http://www.informaticasaladillo.es/echo.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                PrintWriter writer = new PrintWriter(httpURLConnection.getOutputStream());
                writer.write(KEY_NAME + "=" + URLEncoder.encode(name, "UTF-8"));
                writer.write("&" + KEY_DATE + "=" + URLEncoder.encode(
                        simpleDateFormat.format(new Date()), "UTF-8"));
                writer.flush();
                writer.close();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    result = NetworkUtils.readContent(httpURLConnection.getInputStream());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            MainFragment mainFragment = mainFragmentWeakReference.get();
            if (mainFragment != null) {
                mainFragment.showResult(result);
            }
        }

    }

}
