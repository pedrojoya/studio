package es.iessaladillo.pedrojoya.pr096.ui.result;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import es.iessaladillo.pedrojoya.pr096.R;


public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_NOTIFICATION_CODE = "nc";
    public static final String ACTION_VIEW =
            "es.iessaladillo.pedrojoya.pr096.action.VIEW";
    public static final String ACTION_SEND =
            "es.iessaladillo.pedrojoya.pr096.action.SEND";
    public static final String ACTION_DELETE =
            "es.iessaladillo.pedrojoya.pr096.action.DELETE";
    public static final String ACTION_ANSWER =
            "es.iessaladillo.pedrojoya.pr096.action.ANSWER";

    private TextView lblMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initViews();
        processIntent(getIntent());
    }

    private void initViews() {
        lblMensaje = ActivityCompat.requireViewById(this, R.id.lblMessage);
    }

    private void processIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_CODE, 0);
            processAction(action, notificationId);
        }
    }

    private void processAction(String action, int notificationId) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (action != null) {
            switch (action) {
                case ACTION_SEND:
                    lblMensaje.setText(getString(R.string.main_activity_send));
                    notificationManager.cancel(notificationId);
                    break;
                case ACTION_DELETE:
                    lblMensaje.setText(getString(R.string.main_activity_delete));
                    notificationManager.cancel(notificationId);
                    break;
                case ACTION_ANSWER:
                    lblMensaje.setText(getString(R.string.main_activity_answer));
                    notificationManager.cancel(notificationId);
                    break;
                default:
                    lblMensaje.setText(getString(R.string.ver));
                    break;
            }
        }
    }

}
