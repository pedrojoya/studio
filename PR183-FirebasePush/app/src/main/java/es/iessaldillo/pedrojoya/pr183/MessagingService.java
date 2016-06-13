package es.iessaldillo.pedrojoya.pr183;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

// Servicio para recibir mensajes de Firebase Cloud Messaging.
public class MessagingService extends FirebaseMessagingService{

    private static final int ID_NOTIFICACION = 0;

    // Cuando se recibe un mensaje.
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Se muestra una notificación al usuario.
        mostrarNotificacion(remoteMessage.getData().get("message"));
    }

    private void mostrarNotificacion(String mensaje) {
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Prueba de FCM")
                .setContentText(mensaje)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(ID_NOTIFICACION, builder.build());
    }
}
