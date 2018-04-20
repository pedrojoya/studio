package es.iessaldillo.pedrojoya.pr183;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(BuildConfig.TITULO);
        TextView lblMensaje = ActivityCompat.requireViewById(this, R.id.lblMensaje);
        lblMensaje.setText(BuildConfig.NUEVOTEXTO);
        lblMensaje.setText(BuildConfig.SECRETO1);
        lblMensaje.setText(BuildConfig.SECRETO2);
        // Nos subscribimos a un topic de FCM.
        FirebaseMessaging.getInstance().subscribeToTopic(BuildConfig.TITULO);
        // Se obtiene el token de identificación de esta instancia de
        // la aplicación (uno vez recibido el servicio almacena el token
        // en el servidor).
        FirebaseInstanceId.getInstance().getToken();
    }

}
