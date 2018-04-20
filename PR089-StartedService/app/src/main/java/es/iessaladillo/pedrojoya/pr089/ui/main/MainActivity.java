package es.iessaladillo.pedrojoya.pr089.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr089.R;
import es.iessaladillo.pedrojoya.pr089.services.SummationService;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private EditText txtInteger;
    private Button btnCalculate;

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra(SummationService.EXTRA_RESULT)) {
                    long number = intent.getLongExtra(SummationService.EXTRA_NUMBER, 0);
                    long result = intent.getLongExtra(SummationService.EXTRA_RESULT, 1);
                    Toast.makeText(MainActivity.this, "Summation of " + number + " = " + result,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void initViews() {
        txtInteger = ActivityCompat.requireViewById(this, R.id.txtInteger);
        btnCalculate = ActivityCompat.requireViewById(this, R.id.btnCalculate);

        btnCalculate.setOnClickListener(v -> calculate());
    }

    private void calculate() {
        try {
            Long number = Long.valueOf(txtInteger.getText().toString());
            SummationService.start(this, number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(SummationService.ACTION_SUMMATION);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                broadcastReceiver);
        super.onPause();
    }

}
