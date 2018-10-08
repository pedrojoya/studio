package es.iessaladillo.pedrojoya.pr080.data.remote.echo;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.RequestState;
import es.iessaladillo.pedrojoya.pr080.utils.NetworkUtils;

public class EchoLiveData extends MutableLiveData<RequestState> {

    private EchoAsyncTask task;

    public void requestEcho(String text) {
        task = new EchoAsyncTask();
        task.execute(text);
    }

    public void cancel() {
        if (task != null) task.cancel(true);
    }

    @SuppressLint("StaticFieldLeak")
    class EchoAsyncTask extends AsyncTask<String, Void, Void> {

        private static final String KEY_NAME = "nombre";
        private static final String KEY_DATE = "fecha";

        private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "dd/MM/yyyy " + "HH:mm:ss", Locale.getDefault());

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
                URL url = new URL("http://www.informaticasaladillo.es/echo.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                PrintWriter writer = new PrintWriter(connection.getOutputStream());
                writer.write(KEY_NAME + "=" + URLEncoder.encode(params[0], "UTF-8"));
                writer.write("&" + KEY_DATE + "=" + URLEncoder.encode(
                        simpleDateFormat.format(new Date()), "UTF-8"));
                writer.flush();
                writer.close();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //noinspection unchecked
                    postValue(new RequestState.Result<Event<String>>(
                            new Event(NetworkUtils.readContent(connection.getInputStream()))));
                } else {
                    postValue(new RequestState.Error(
                            new Event<>(new Exception(connection.getResponseMessage()))));
                }
            } catch (Exception e) {
                postValue(new RequestState.Error(new Event<>(e)));
            }
            return null;
        }

    }

}