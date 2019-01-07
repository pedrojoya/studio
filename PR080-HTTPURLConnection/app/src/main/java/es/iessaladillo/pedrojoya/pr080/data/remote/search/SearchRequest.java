package es.iessaladillo.pedrojoya.pr080.data.remote.search;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import es.iessaladillo.pedrojoya.pr080.base.Call;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.Resource;
import es.iessaladillo.pedrojoya.pr080.utils.NetworkUtils;

class SearchRequest extends Call<Resource<Event<String>>> {

    private final String text;

    SearchRequest(String text) {
        this.text = text;
    }

    @Override
    protected void doAsync() {
        postValue(Resource.loading());
        // Simulate latency
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            return;
        }
        try {
            URL url = new URL(
                "https://www.google.es/search?hl=es&q=\"" + URLEncoder.encode(text,
                    "UTF-8") + "\"");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Needed for Google search.
            connection.setRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
            connection.setDoInput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                postValue(Resource.success(new Event<>(extractResultFromContent(
                    NetworkUtils.readContent(connection.getInputStream())))));
            } else {
                postValue(Resource.error(new Exception(connection.getResponseMessage())));
            }
        } catch (Exception e) {
            postValue(Resource.error(e));
        }

    }

    @Override
    protected void doOnCancelled() { }

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