package es.iessaladillo.pedrojoya.PR004.utils;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import java.util.List;

public class IntentUtils {

    private IntentUtils() {
    }

    // Retorna si hay alguna actividad que pueda recibir el intent.
    public static boolean isActivityAvailable(Context ctx, Intent intent) {
        final PackageManager gestorPaquetes = ctx.getApplicationContext().getPackageManager();
        List<ResolveInfo> listaApps = gestorPaquetes.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return listaApps.size() > 0;
    }

    // Muestra la actividad de configuración de aplicaciones.
    public static void startInstalledAppDetailsActivity(@NonNull final Activity context) {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        // Para que deje rastro en la pila de actividades se añaden flags.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    public static Intent getViewUriIntent(Uri uri) {
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent getWebSearchIntent(String texto) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, texto);
        return intent;
    }

    public static Intent getDialIntent(String tel) {
        return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel.trim()));
    }

    public static Intent getViewInMapIntent(double longit, double lat, int zoom) {
        return new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:" + longit + "," + lat + "?z=" + zoom));
    }

    public static Intent getSearchInMapIntent(String texto) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + texto));
    }

}
