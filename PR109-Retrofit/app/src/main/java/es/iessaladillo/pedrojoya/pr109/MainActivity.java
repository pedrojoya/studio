package es.iessaladillo.pedrojoya.pr109;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


public class MainActivity extends ActionBarActivity {

    EjemploRestInterface api;
    @InjectView(R.id.lblTexto)
    TextView mLblTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        configAPI();
        getTareas();
    }

    private void configAPI() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("X-Parse-Application-Id", "xgYR15iQQ1kyFoNt2ZrW6qgxF5sXtgXRDgHbzy0f");
                request.addHeader("X-Parse-REST-API-Key", "wlfZg4Wz4GSKdpny577Xq9hLoHqEIByy6BiPBmpd");
                request.addHeader("Content-Type", "application/json");
            }
        };
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.parse.com/1")
                .setRequestInterceptor(requestInterceptor)
                .build();
        api = restAdapter.create(EjemploRestInterface.class);
    }

    private void getTareas() {
        ListarTareas operacion = new ListarTareas();
        operacion.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ListarTareas extends AsyncTask<Void, Void, List<Tarea>> {

        @Override
        protected List<Tarea> doInBackground(Void... params) {
            Resultado<Tarea> resultado = api.listarTareas();
            return resultado.getResults();
        }

        @Override
        protected void onPostExecute(List<Tarea> tareas) {
            super.onPostExecute(tareas);
            StringBuilder sb = new StringBuilder();
            for (Tarea tarea : tareas) {
                sb.append(tarea.getConcepto());
                sb.append("\n");
            }
            mLblTexto.setText(sb.toString());
        }
    }
}
