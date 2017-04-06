package es.iessaladillo.pedrojoya.PR004.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

@SuppressWarnings("SameParameterValue")
public class PermissionUtils {

    private PermissionUtils() {
    }

    public static boolean hasPermission(Context context, String permissionName) {
        return ContextCompat.checkSelfPermission(context.getApplicationContext(), permissionName)
                == PackageManager.PERMISSION_GRANTED;
    }

}
