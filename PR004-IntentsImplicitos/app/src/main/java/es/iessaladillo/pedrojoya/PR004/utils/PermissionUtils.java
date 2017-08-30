package es.iessaladillo.pedrojoya.PR004.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

@SuppressWarnings({"SameParameterValue", "WeakerAccess"})
public class PermissionUtils {

    private PermissionUtils() {
    }

    public static boolean hasPermission(Context context, String permissionName) {
        return ContextCompat.checkSelfPermission(context.getApplicationContext(), permissionName)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean canCall(Context context) {
        return PermissionUtils.hasPermission(context,
                Manifest.permission.CALL_PHONE);
    }
}
