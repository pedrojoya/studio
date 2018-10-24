package es.iessaladillo.pedrojoya.pr097.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;

public class Rotation {

    private Rotation() {
    }

    public static void rotateScreen(@NonNull Activity activity) {
        int orientation = ApplicationProvider.getApplicationContext()
                .getResources()
                .getConfiguration().orientation;
        activity.setRequestedOrientation((orientation
                == Configuration.ORIENTATION_PORTRAIT) ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
