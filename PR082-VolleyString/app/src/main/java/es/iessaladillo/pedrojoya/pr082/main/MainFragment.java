package es.iessaladillo.pedrojoya.pr082.main;


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

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.iessaladillo.pedrojoya.pr082.R;
import es.iessaladillo.pedrojoya.pr082.data.remote.EchoRequest;
import es.iessaladillo.pedrojoya.pr082.data.remote.GoogleRequest;
import es.iessaladillo.pedrojoya.pr082.data.remote.VolleyInstance;
import es.iessaladillo.pedrojoya.pr082.utils.NetworkUtils;

public class MainFragment extends Fragment {

    private static final String KEY_NAME = "nombre";
    private static final String KEY_DATE = "fecha";

    private EditText txtName;
    private ProgressBar pbProgress;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnSearch;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnEcho;
    private RequestQueue requestQueue;
    private SimpleDateFormat simpleDateFormat;

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
        requestQueue = VolleyInstance.getInstance(getActivity()).getRequestQueue();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
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
            sendSearchRequest(name);
        } else {
            showNoConnectionAvailable();
        }
    }

    private void sendSearchRequest(String name) {
        try {
            requestQueue.add(new GoogleRequest(URLEncoder.encode(name, "UTF-8"),
                    response -> showResult(extractResultFromContent(response)), this::showError));
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(getActivity(), R.string.main_fragment_coding_error, Toast.LENGTH_LONG)
                    .show();
        }
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

    private void echo() {
        String name = txtName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            return;
        }
        if (NetworkUtils.isConnectionAvailable(getActivity())) {
            pbProgress.setVisibility(View.VISIBLE);
            sendEchoRequest(name);
        } else {
            showNoConnectionAvailable();
        }

    }

    private void sendEchoRequest(String name) {
        Map<String, String> params = new HashMap<>();
        params.put(KEY_NAME, name);
        params.put(KEY_DATE, simpleDateFormat.format(new Date()));
        requestQueue.add(new EchoRequest(params, this::showResult, this::showError));
    }

    private void showNoConnectionAvailable() {
        Toast.makeText(getActivity(), getString(R.string.main_fragment_no_connection),
                Toast.LENGTH_SHORT).show();
    }

    private void showResult(String result) {
        pbProgress.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
    }

    private void showError(VolleyError error) {
        pbProgress.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
    }

}
