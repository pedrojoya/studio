package es.iessaladillo.pedrojoya.pr222.utils;

import android.content.Context;
import android.content.res.Configuration;

public class ConfigurationUtils {

    private ConfigurationUtils() {
    }

    public static boolean inLandscape(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

}
