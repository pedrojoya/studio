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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.LargeValueFormatter;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr140.data.Escrutinio_sitio;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    private APIClient.ApiInterface mApiClient;
    private final ArrayList<Poblacion> mPoblaciones = createPoblaciones();

    private PieChart mChart;
    private Toolbar mToolbar;
    private Spinner mSpnToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiVistas();
        // Se obtiene la interfaz de acceso a la api.
        mApiClient = APIClient.getApiInterface();
        // Se selecciona la primera población.
        mSpnToolbar.setSelection(0);

    }

    // Obtiene e inicializa las vistas.
    private void intiVistas() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mChart = (PieChart) findViewById(R.id.chart);
        mSpnToolbar = (Spinner) findViewById(R.id.spnToolbar);
        configToolbar();
        configChart();
        configSpinner();
    }

    // Configura la toolbar.
    private void configToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    // Configura el gráfico.
    private void configChart() {
        //mChart.setUsePercentValues(true);
        mChart.setDescription("Concejales");
        //mChart.setDescriptionPosition(0, 0);
        //mChart.setDrawSliceText(true);
        mChart.setNoDataText(getString(R.string.grafico_sin_datos));
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(26f);
        mChart.setTransparentCircleRadius(28f);
        mChart.setDrawCenterText(true);
        mChart.setCenterText("Elecciones\nMunicipales\n2015");
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }

    // Configura el spinner de selección de población.
    private void configSpinner() {
        ArrayList<String> nombres = new ArrayList<>();
        for (Poblacion poblacion : mPoblaciones) {
            nombres.add(poblacion.getNombre());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,
                nombres);
        adapter.setDropDownViewResource(R.layout.appbar_filter_list);
        mSpnToolbar.setAdapter(adapter);
        mSpnToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                obtenerDatos(mPoblaciones.get(i).getCodigo());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    // Obtiene los datos electorales.
    private void obtenerDatos(String codigo) {
        mApiClient.getPoblacionData(codigo, new Callback<Escrutinio_sitio>() {
            @Override
            public void success(final Escrutinio_sitio escrutinio,
                                Response response) {
                // Se establecen los datos del gráfico
                setChartData(escrutinio);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(getString(R.string.app_name), error.getMessage());
            }
        });
    }

    // Establece los datos del gráfico en base al escrutinio y se redibuja.
    private void setChartData(Escrutinio_sitio escrutinio) {
        ArrayList<Entry> valores = new ArrayList<>();
        ArrayList<String> nombres = new ArrayList<>();
        ArrayList<Integer> colores = new ArrayList<>();
        // Cada entrada debe tener un identificador único (en este caso i)
        for (int i = 0; i < escrutinio.getResultados().getPartidos().size(); i++) {
            int electos = Integer.parseInt(
                    escrutinio.getResultados().getPartidos().get(i).getElectos());
            // Los partidos con 0 concejales no son añadidos.
            if (electos > 0) {
                int color = getColorPartido(escrutinio.getResultados()
                        .getPartidos().get(i).getId_partido());
                colores.add(Integer.valueOf(color));
                valores.add(new Entry(electos, i));
                nombres.add(
                        escrutinio.getResultados().getPartidos().get(i).getNombre());
            }
        }
        // Se crea el DataSet a partir del ArrayList de valores y se le asigna
        // un nombre. Se le añaden colores.
        PieDataSet dataSet = new PieDataSet(valores, "Nº CONCEJALES");
        dataSet.setSliceSpace(2f); // Espacio entre porciones
        dataSet.setSelectionShift(25f); // Cuanto sobresale porción al pulsar.
        dataSet.setColors(colores);
        /* Opcionalmente se pueden añadir colecciones de colores.:
            VORDIPLOM_COLORS, JOYFUL_COLORS, LIBERTY_COLORS, PASTEL_COLORS,
            getHoloBlue()
           ArrayList<Integer> colors = new ArrayList<>();
           for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);
         */
        // Se crea y asigna el Data del gráfico a partir del DataSet y el
        // ArrayList de nombres.
        PieData data = new PieData(nombres, dataSet);
        data.setValueFormatter(new LargeValueFormatter()); // Format. valores
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);
        mChart.highlightValues(null); // Ninguno seleccionado
        // Se repinta y anima el gráfico
        mChart.invalidate();
        mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
    }

    // Retorna el color correspondiente a un determinado partido.
    private int getColorPartido(String idPartido) {
        // PP
        if (idPartido.equals("4252")) {
            return Color.argb(255, 0, 163, 223);
        }
        // PSOE
        if (idPartido.equals("4327") || idPartido.equals("4333") || idPartido
                .equals("4330")) {
            return Color.argb(255, 239, 25, 32);
        }
        // Cs
        if (idPartido.equals("1456")) {
            return Color.argb(255, 239, 122, 54);
        }
        // IU
        if (idPartido.equals("3465")) {
            return Color.argb(255, 219, 5, 37);
        }
        // Podemos
        if (idPartido.equals("131") || idPartido.equals("271") || idPartido
                .equals("3782") || idPartido.equals("4694")) {
            return Color.argb(255, 96, 44, 97);
        }
        // PA
        if (idPartido.equals("4100") || idPartido.equals("4099")) {
            return Color.argb(255, 27, 168, 56);
        }
        // PI Los Barrios
        if (idPartido.equals("2944")) {
            return Color.argb(255, 242, 180, 7);
        }
        // En otro caso.
        return Color.GRAY;
    }

    // Retorna el ArrayList de poblaciones.
    private ArrayList<Poblacion> createPoblaciones() {
        ArrayList<Poblacion> poblaciones = new ArrayList<>();
        poblaciones.add(new Poblacion("Algeciras", "04"));
        poblaciones.add(new Poblacion("Los Barrios", "08"));
        poblaciones.add(new Poblacion("Castellar", "13"));
        poblaciones.add(new Poblacion("Jimena", "21"));
        poblaciones.add(new Poblacion("La Línea", "22"));
        poblaciones.add(new Poblacion("San Roque", "33"));
        poblaciones.add(new Poblacion("Tarifa", "35"));
        return poblaciones;
    }

}