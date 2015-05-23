package es.iessaladillo.pedrojoya.pr101;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class ExportarReceiver extends BroadcastReceiver {

    // Constantes.
    private static final int NC_EXPORTADO = 5;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Se obtiene el gestor de notificaciones del sistema.
        NotificationManager mGestor = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Se configura la notificación.
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        b.setSmallIcon(R.drawable.ic_not_download);
        BitmapDrawable largeIcon = (BitmapDrawable) context.getResources().getDrawable(
                R.mipmap.ic_launcher);
        if (largeIcon != null) {
            b.setLargeIcon(largeIcon.getBitmap());
        }
        b.setContentTitle(context.getString(R.string.listado_de_alumnos));
        b.setContentText(context.getString(R.string.fichero_generado_con_exito));
        b.setTicker(context.getString(R.string.lista_de_alumnos_exportada));
        b.setAutoCancel(true);
        // Se crea el pending intent.
        String filename = intent.getStringExtra(ExportarService.EXTRA_FILENAME);
        Intent mostrarIntent = new Intent(Intent.ACTION_VIEW);
        mostrarIntent.setDataAndType(Uri.parse(filename), "text/plain");
        PendingIntent pi = PendingIntent.getActivity(context, 0, mostrarIntent,
                0);
        b.setContentIntent(pi);
        // Se construye y muestra la notificación, asignándole un código de
        // notificación entero.
        mGestor.notify(NC_EXPORTADO, b.build());
    }

}
