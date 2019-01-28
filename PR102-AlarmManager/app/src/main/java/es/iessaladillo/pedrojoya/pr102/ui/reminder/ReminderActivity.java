package es.iessaladillo.pedrojoya.pr102.ui.reminder;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr102.R;
import es.iessaladillo.pedrojoya.pr102.reminder.ReminderScheduler;

public class ReminderActivity extends AppCompatActivity {

    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        if (getIntent() != null && getIntent().hasExtra(ReminderScheduler.EXTRA_MESSAGE)) {
            message = getIntent().getStringExtra(ReminderScheduler.EXTRA_MESSAGE);
        }
        setupViews();
    }

    private void setupViews() {
        TextView lblMessage = ActivityCompat.requireViewById(this, R.id.lblMessage);
        lblMessage.setText(message);
    }

}
