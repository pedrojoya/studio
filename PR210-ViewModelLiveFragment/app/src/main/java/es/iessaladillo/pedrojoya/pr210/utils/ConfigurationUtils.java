package es.iessaladillo.pedrojoya.pr210.utils;

import android.content.Context;
import android.content.res.Configuration;

public class ConfigurationUtils {

    private ConfigurationUtils() {
    }

    public static boolean hasLandscapeOrientation(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

}
