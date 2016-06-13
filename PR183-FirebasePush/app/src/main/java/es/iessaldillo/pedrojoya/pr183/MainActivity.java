package es.iessaldillo.pedrojoya.pr183;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Nos subscribimos a un topic de FCM.
        FirebaseMessaging.getInstance().subscribeToTopic("Baldomero");
        // Se obtiene el token de identificación de esta instancia de
        // la aplicación (uno vez recibido el servicio almacena el token
        // en el servidor).
        FirebaseInstanceId.getInstance().getToken();
    }

}
