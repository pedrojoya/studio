package es.iessaladillo.pedrojoya.pr180.main;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.pr180.R;
import es.iessaladillo.pedrojoya.pr180.utils.NetworkUtils;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
    }

    private void initViews(View view) {
        txtName = view.findViewById(R.id.txtName);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnEcho = view.findViewById(R.id.btnEcho);
        pbProgress = view.findViewById(R.id.pbProgress);

        btnSearch.setOnClickListener(v -> search());
        btnEcho.setOnClickListener(v -> echo());
    }

    private void search() {
        String name = txtName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            return;
        }
        if (NetworkUtils.isConnectionAvailable(getActivity())) {
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
        if (NetworkUtils.isConnectionAvailable(getActivity())) {
            pbProgress.setVisibility(View.VISIBLE);
            (new EchoAsyncTask(this)).execute(name);
        } else {
            showNoConnectionAvailable();
        }

    }

    private void showNoConnectionAvailable() {
        Toast.makeText(getActivity(), getString(R.string.main_fragment_no_connection),
                Toast.LENGTH_SHORT).show();
    }

    private void showResult(String result) {
        pbProgress.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
    }

    static class SearchAsyncTask extends AsyncTask<String, Void, String> {

        private final WeakReference<MainFragment> mainFragmentWeakReference;
        private OkHttpClient mOkHttpClient;
        private Call mOkHttpCall;

        public SearchAsyncTask(MainFragment mainFragment) {
            mainFragmentWeakReference = new WeakReference<>(mainFragment);
            buildOkHttpClient(mainFragment.getContext());
        }

        private void buildOkHttpClient(Context context) {
            mOkHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(
                    new StethoInterceptor()).addInterceptor(new ChuckInterceptor(context)).build();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String name = params[0];
            try {
                URL url = new URL(
                        "https://www.google.es/search?hl=es&q=\"" + URLEncoder.encode(name, "UTF-8")
                                + "\"");
                Request request = new Request.Builder().url(url).header("User-Agent",
                        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5" + ".1)").build();
                mOkHttpCall = mOkHttpClient.newCall(request);
                Response response = mOkHttpCall.execute();
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String content = responseBody.string();
                        result = extractResultFromContent(content);
                    }
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
        private OkHttpClient mOkHttpClient;
        private Call mOkHttpCall;


        public EchoAsyncTask(MainFragment mainFragment) {
            mainFragmentWeakReference = new WeakReference<>(mainFragment);
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            buildOkHttpClient(mainFragment.getContext());
        }

        private void buildOkHttpClient(Context context) {
            mOkHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(
                    new StethoInterceptor()).addInterceptor(new ChuckInterceptor(context)).build();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String name = params[0];
            try {
                URL url = new URL("http://www.informaticasaladillo.es/echo.php");
                RequestBody formBody = new FormBody.Builder().addEncoded(KEY_NAME, name).addEncoded(
                        KEY_DATE, simpleDateFormat.format(new Date())).build();
                Request request = new Request.Builder().url(url).post(formBody).build();
                mOkHttpCall = mOkHttpClient.newCall(request);
                Response response = mOkHttpCall.execute();
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        result = responseBody.string().trim();
                    }
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
