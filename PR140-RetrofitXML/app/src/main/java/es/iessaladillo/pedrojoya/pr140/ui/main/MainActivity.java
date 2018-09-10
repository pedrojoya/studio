package es.iessaladillo.pedrojoya.pr140.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr140.R;
import es.iessaladillo.pedrojoya.pr140.base.Event;
import es.iessaladillo.pedrojoya.pr140.base.RequestState;
import es.iessaladillo.pedrojoya.pr140.base.ToolbarSpinnerAdapter;
import es.iessaladillo.pedrojoya.pr140.data.model.City;
import es.iessaladillo.pedrojoya.pr140.data.remote.ApiService;
import es.iessaladillo.pedrojoya.pr140.data.remote.model.Counting;

public class MainActivity extends AppCompatActivity {

    private PieChart chart;
    private Toolbar toolbar;
    private Spinner spnToolbar;
    private ProgressBar progressBar;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(ApiService
                .getInstance(this).getApi())).get(MainActivityViewModel.class);
        initViews();
        observeCounting();
    }

    private void initViews() {
        toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        chart = ActivityCompat.requireViewById(this, R.id.chart);
        spnToolbar = ActivityCompat.requireViewById(this, R.id.spnToolbar);
        progressBar = ActivityCompat.requireViewById(this, R.id.progressBar);

        setupToolbar();
        setupChart();
        setupSpinner();
    }

    private void observeCounting() {
        viewModel.getCounting().observe(this, request -> {
            if (request != null) {
                if (request instanceof RequestState.Loading) {
                    showLoading(
                            ((RequestState.Loading) request).isLoading());
                } else if (request instanceof RequestState.Error) {
                    showErrorLoadingCounting(((RequestState.Error) request).getException());
                } else if (request instanceof RequestState.Result) {
                    //noinspection unchecked
                    showCounting(((RequestState.Result<Counting>) request).getData());
                }
            }

        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setupChart() {
        Description description = new Description();
        description.setText(getString(R.string.main_activity_concejales));
        chart.setDescription(description);
        //chart.setDescriptionPosition(0, 0);
        //chart.setDrawSliceText(true);
        chart.setNoDataText(getString(R.string.grafico_sin_datos));
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(ContextCompat.getColor(this, android.R.color.transparent));
        chart.setHoleRadius(26f);
        chart.setTransparentCircleRadius(28f);
        chart.setDrawCenterText(true);
        chart.setCenterText(getString(R.string.main_activity_elecciones));
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }

    private void setupSpinner() {
        if (getSupportActionBar() != null) {
            spnToolbar.setAdapter(new ToolbarSpinnerAdapter<>(
                    getSupportActionBar().getThemedContext(), viewModel.getCities()));
            spnToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    if (position != viewModel.getSelectedCity()) {
                        viewModel.setSelectedCity(position);
                        viewModel.loadEscrutinio(((City) adapterView.getItemAtPosition(position)).getCode());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }

    private void showCounting(Counting counting) {
        progressBar.setVisibility(View.INVISIBLE);
        ArrayList<PieEntry> values = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        // Cada entrada debe tener un identificador único (en este caso i)
        for (int i = 0; i < counting.getResults().getParties().size(); i++) {
            int electos = Integer.parseInt(
                    counting.getResults().getParties().get(i).getElected());
            // Los partidos con 0 concejales no son añadidos.
            if (electos > 0) {
                colors.add(counting.getResults().getParties().get(i).getColor());
                values.add(new PieEntry(electos,
                        counting.getResults().getParties().get(i).getName()));
            }
        }
        // Se crea el DataSet a partir del ArrayList de valores y se le asigna
        // un nombre. Se le añaden colores.
        PieDataSet dataSet = new PieDataSet(values, "Nº CONCEJALES");
        dataSet.setSliceSpace(2f); // Espacio entre porciones
        dataSet.setSelectionShift(25f); // Cuanto sobresale porción al pulsar.
        dataSet.setColors(colors);
        /* Opcionalmente se pueden añadir colecciones de colores.:
            VORDIPLOM_COLORS, JOYFUL_COLORS, LIBERTY_COLORS, PASTEL_COLORS,
            getHoloBlue()
           ArrayList<Integer> colors = new ArrayList<>();
           for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);
         */
        // Se crea y asigna el Data del gráfico a partir del DataSet
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new LargeValueFormatter()); // Format. valores
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);
        chart.highlightValues(null); // Ninguno seleccionado
        // Se repinta y anima el gráfico
        chart.invalidate();
        chart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
    }

    private void showLoading(boolean loading) {
        progressBar.setVisibility(loading?View.VISIBLE:View.INVISIBLE);
    }

    private void showErrorLoadingCounting(Event<Exception> event) {
        progressBar.setVisibility(View.INVISIBLE);
        Exception exception = event.getContentIfNotHandled();
        if (exception != null) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
