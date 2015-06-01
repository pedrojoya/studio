package es.iessaladillo.pedrojoya.pr140;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.animation.AnimationEasing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.LargeValueFormatter;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr140.data.Escrutinio_sitio;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    private APIClient.ApiInterface mApiClient;
    private PieChart mChart;
    private Toolbar toolbar;
    private String[] mCodigos = {"04", "08", "13", "21", "22", "33", "35"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        intiVistas();
        // Se obtiene la interfaz de acceso a la api.
        mApiClient = APIClient.getApiInterface();
    }

    // Obtiene e inicializa las vistas.
    private void intiVistas() {
        mChart = (PieChart) findViewById(R.id.chart);
        //mChart.setUsePercentValues(true);
        //mChart.setDescription("Nº de concejales");
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(26f);
        mChart.setTransparentCircleRadius(28f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setCenterText("Elecciones\nMunicipales\n2015");
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);

        Spinner spnToolbar = (Spinner) findViewById(R.id.spnToolbar);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,
                new String[]{"Algeciras ", "Los Barrios", "Castellar de la Frontera", "Jimena de la Frontera", "La Línea de la Concepción", "San Roque", "Tarifa"});
        adapter.setDropDownViewResource(R.layout.appbar_filter_list);
        spnToolbar.setAdapter(adapter);
        spnToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                obtenerDatos(mCodigos[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //... Acciones al no existir ningún elemento seleccionado
            }
        });
        spnToolbar.setSelection(0);
    }

    // Obtiene los datos electorales.
    private void obtenerDatos(String codigo) {
        mApiClient.getPoblacionData(codigo,
                new Callback<Escrutinio_sitio>() {
                    @Override
                    public void success(final Escrutinio_sitio escrutinio, Response response) {
                        setTitle(escrutinio.getNombre_sitio());
                        setChartData(escrutinio);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Mia", "error");
                    }
                });
    }

    private void setChartData(Escrutinio_sitio escrutinio) {
        float mult = 100;

        ArrayList<Entry> valores = new ArrayList<Entry>();
        ArrayList<String> nombres = new ArrayList<String>();


        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        int numDatos = escrutinio.getResultados().getPartidos().size();

        for (int i = 0; i < numDatos; i++) {
            int electos = Integer.parseInt(escrutinio.getResultados().getPartidos().get(i).getElectos());
            if (electos > 0) {
                valores.add(new Entry(electos, i));
                nombres.add(escrutinio.getResultados().getPartidos().get(i).getNombre());
            }
        }

        PieDataSet dataSet = new PieDataSet(valores, "Nº CONCEJALES");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(25f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        /*
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        */
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        /*
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        */
        dataSet.setColors(colors);

        PieData data = new PieData(nombres, dataSet);
        data.setValueFormatter(new LargeValueFormatter());
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
        mChart.animateY(1500, AnimationEasing.EasingOption.EaseInOutQuad);
    }

}
