package es.iessaladillo.pedrojoya.pr080.data.remote.search;

import android.annotation.SuppressLint;
import androidx.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import es.iessaladillo.pedrojoya.pr080.base.RequestState;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.utils.NetworkUtils;

public class SearchLiveData extends MutableLiveData<RequestState> {

    private SearchAsyncTask task;

    public void search(String text) {
        task = new SearchAsyncTask();
        task.execute(text);
    }

    public void cancel() {
        if (task != null) task.cancel(true);
    }

    @SuppressLint("StaticFieldLeak")
    class SearchAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            postValue(new RequestState.Loading(true));
            // Simulate latency
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                URL url = new URL(
                        "https://www.google.es/search?hl=es&q=\"" + URLEncoder.encode(params[0],
                                "UTF-8") + "\"");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                // Needed for Google search.
                connection.setRequestProperty("User-Agent",
                        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
                connection.setDoInput(true);
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    postValue(new RequestState.Result<>(new Event<>(extractResultFromContent(
                            NetworkUtils.readContent(connection.getInputStream())))));
                } else {
                    postValue(new RequestState.Error(
                            new Event<>(new Exception(connection.getResponseMessage()))));
                }
            } catch (Exception e) {
                postValue(new RequestState.Error(new Event<>(e)));
            }
            return null;
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

    }

}