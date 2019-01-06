package es.iessaladillo.pedrojoya.pr080.data.remote.echo;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.pr080.base.AsyncLiveTask;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.Resource;
import es.iessaladillo.pedrojoya.pr080.utils.NetworkUtils;

public class EchoRequest extends AsyncLiveTask<Resource<Event<String>>> {

    private static final String KEY_NAME = "nombre";
    private static final String KEY_DATE = "fecha";

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
        "dd/MM/yyyy " + "HH:mm:ss", Locale.getDefault());
    private final String text;

    EchoRequest(String text) {
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
            URL url = new URL("http://www.informaticasaladillo.es/echo.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            PrintWriter writer = new PrintWriter(connection.getOutputStream());
            writer.write(KEY_NAME + "=" + URLEncoder.encode(text, "UTF-8"));
            writer.write(
                "&" + KEY_DATE + "=" + URLEncoder.encode(simpleDateFormat.format(new Date()),
                    "UTF-8"));
            writer.flush();
            writer.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK && !isCancelled()) {
                postValue(Resource.success(
                    new Event<>(NetworkUtils.readContent(connection.getInputStream()))));
            } else {
                postValue(Resource.error(new Exception(connection.getResponseMessage())));
            }
        } catch (Exception e) {
            postValue(Resource.error(e));
        }

    }

    @Override
    protected void doOnCancelled() {
    }

}
