package es.iessaladillo.pedrojoya.pr140;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;

import es.iessaladillo.pedrojoya.pr140.data.Escrutinio_sitio;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    private APIClient.ApiInterface mApiClient;
    private PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        // Se obtiene la interfaz de acceso a la api.
        mApiClient = APIClient.getApiInterface();
        obtenerDatos();
        chart = (PieChart) findViewById(R.id.chart);
    }

    // Obtiene los datos electorales.
    private void obtenerDatos() {
        mApiClient.getPoblacionData("22",
                new Callback<Escrutinio_sitio>() {
                    @Override
                    public void success(Escrutinio_sitio escrutinio, Response response) {
                        showChart(escrutinio);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Mia", "error");
                    }
                });
    }

    private void showChart(Escrutinio_sitio escrutinio) {
    }

}
