package es.iessaladillo.pedrojoya.pr064.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

public class FragmentUtils {

    private FragmentUtils() {
    }

    @SuppressWarnings("SameParameterValue")
    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
            @IdRes int parentResId, @NonNull Fragment fragment, @NonNull String tag) {
        fragmentManager.beginTransaction().replace(parentResId, fragment, tag).commit();
    }

}
