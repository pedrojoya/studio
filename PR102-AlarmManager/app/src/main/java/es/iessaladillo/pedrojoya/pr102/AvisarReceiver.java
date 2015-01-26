package es.iessaladillo.pedrojoya.pr102;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

public class AvisarReceiver extends BroadcastReceiver {

    // Constantes.
    public static final int DEFAULT_INTERVAL = 5000;
    private static final int NC_AVISAR = 1;
    private static final int RC_AVISO = 2;
    private static final int RC_ENTENDIDO = 1;
    private static final String EXTRA_MENSAJE = "extra_mensaje";
    private static final String PREF_ESTADO = "pref_estado";
    public static final String PREF_MENSAJE = "pref_mensaje";
    public static final String PREF_INTERVALO = "pref_intervalo";
    private static final String PREF_FILENAME = "alarmas";

    // Cuando se recibe el broadcast.
    @Override
    public void onReceive(Context context, Intent intent) {
        // Se obtiene el gestor de notificaciones del sistema.
        NotificationManager mGestor = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Se configura la notificación.
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        b.setSmallIcon(android.R.drawable.ic_menu_info_details);
        b.setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(
                android.R.drawable.ic_menu_info_details)).getBitmap());
        b.setContentTitle(context.getString(R.string.aviso_importante));
        b.setContentText(intent.getStringExtra(EXTRA_MENSAJE));
        b.setDefaults(Notification.DEFAULT_ALL);
        b.setTicker(context.getString(R.string.aviso_importante));
        b.setAutoCancel(true);
        // Al pulsarse la notificación se mostrará la actividad principal.
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, RC_ENTENDIDO, i,
                0);
        b.setContentIntent(pi);
        // Se construye y muestra la notificación.
        mGestor.notify(NC_AVISAR, b.build());
    }

    // Programa la alarma de aviso.
    static void programarAlarma(Context context, String mensaje, int intervalo) {
        // Se obtiene el gestor de alarmas.
        AlarmManager gestorAlarmas = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        // Cuando se dispare la alarma se llamar� al Broadcast Receiver
        // correspondiente.
        Intent intent = new Intent(context, AvisarReceiver.class);
        intent.putExtra(EXTRA_MENSAJE, mensaje);
        PendingIntent pi = PendingIntent.getBroadcast(context, RC_AVISO,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        // Se a�ade la alarma de tipo repetitivo y despertador.
        gestorAlarmas.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + intervalo, intervalo, pi);
        // Se guardan los datos de la alarma en las preferencias.
        guardarEstadoAlarma(context, true, mensaje, intervalo);
    }

    // Reprograma la alarma en base a los valores previos almacenados en
    // preferencias.
    static void reprogramarAlarma(Context context) {
        // Se obtienen los datos almacenados en el archivo de preferencias.
        SharedPreferences preferencias = context.getSharedPreferences(
                "alarmas", Context.MODE_PRIVATE);
        String mensaje = preferencias.getString(PREF_MENSAJE,
                context.getString(R.string.quillo_ponte_ya_a_currar));
        int intervalo = preferencias.getInt(PREF_INTERVALO, DEFAULT_INTERVAL);
        // Se programa la alarma.
        programarAlarma(context, mensaje, intervalo);
    }

    // Cancela la alarma.
    static void cancelarAlarma(Context context) {
        // Se obtiene el gestor de alarmas.
        AlarmManager gestorAlarmas = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        // Se crea un pendingIntent equivalente al de la alarma programada.
        Intent intent = new Intent(context, AvisarReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, RC_AVISO,
                intent, 0);
        // Se cancela la alarma.
        gestorAlarmas.cancel(pi);
        // Se almacena el estado de la alarma.
        guardarEstadoAlarma(context, false, "", 0);
    }

    // Guarda en las preferencia esl estado de la alarma.
    private static void guardarEstadoAlarma(Context context, boolean on,
            String mensaje, int intervalo) {
        SharedPreferences preferencias = context.getSharedPreferences(
                PREF_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean(PREF_ESTADO, on);
        // Si la alarma queda activada se guarda el mensaje y el intervalo.
        if (on) {
            editor.putString(PREF_MENSAJE, mensaje);
            editor.putInt(PREF_INTERVALO, intervalo);
        }
        editor.apply();
    }

    // Retorna si la alarma está activada o no.
    static boolean isAlarmaOn(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences(
                PREF_FILENAME, Context.MODE_PRIVATE);
        return preferencias.getBoolean(PREF_ESTADO, false);
    }

}
