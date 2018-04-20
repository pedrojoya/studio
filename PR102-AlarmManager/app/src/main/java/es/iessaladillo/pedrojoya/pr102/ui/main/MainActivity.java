package es.iessaladillo.pedrojoya.pr102.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr102.R;
import es.iessaladillo.pedrojoya.pr102.data.model.Alarm;

public class MainActivity extends AppCompatActivity {

    private TextView txtMessage;
    private TextView txtInterval;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        txtMessage = ActivityCompat.requireViewById(this, R.id.txtMessage);
        txtInterval = ActivityCompat.requireViewById(this, R.id.txtInterval);
        SwitchCompat swActivar = ActivityCompat.requireViewById(this, R.id.swTurnOn);

        alarm = Alarm.getInstance(this);
        txtMessage.setText(alarm.getMessage());
        txtInterval.setText(String.valueOf(alarm.getInterval()));
        swActivar.setChecked(alarm.isOn());
        swActivar.setOnCheckedChangeListener((compoundButton, isChecked) -> toggleAlarmState(isChecked));
    }

    private void toggleAlarmState(boolean isChecked) {
        if (isChecked) {
            turnAlarmOn();
        } else {
            turnAlarmOff();
        }
    }

    private void turnAlarmOn() {
        alarm.setMessage(TextUtils.isEmpty(txtMessage.getText().toString()) ? getString(
                R.string.activity_main_txtMessage) : txtMessage.getText().toString());
        alarm.setInterval(Integer.parseInt(txtInterval.getText().toString()));
        alarm.turnOn(this);
    }

    private void turnAlarmOff() {
        alarm.turnOff(this);
    }

}
