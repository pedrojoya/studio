package es.iessaladillo.pedrojoya.pr239.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr239.R;

public class MainActivity extends AppCompatActivity {

    private TextView lblStockPrice;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnStop;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnStart;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
        viewModel.getStockLiveData().observe(this, this::showPrice);
    }

    private void showPrice(Integer price) {
        lblStockPrice.setText(String.valueOf(price));
    }

    private void initViews() {
        lblStockPrice = ActivityCompat.requireViewById(this, R.id.lblStockPrice);
        btnStart = ActivityCompat.requireViewById(this, R.id.btnStart);
        btnStop = ActivityCompat.requireViewById(this, R.id.btnStop);

        btnStart.setOnClickListener(v -> viewModel.start());
        btnStop.setOnClickListener(v -> viewModel.stop());
    }

}
