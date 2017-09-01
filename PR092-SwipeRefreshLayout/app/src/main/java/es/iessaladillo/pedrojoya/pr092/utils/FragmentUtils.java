package es.iessaladillo.pedrojoya.pr092.utils;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class FragmentUtils {

    private FragmentUtils() {
    }

    @SuppressWarnings("SameParameterValue")
    public static void replaceFragment(FragmentManager fragmentManager, @IdRes int parentResId,
            Fragment fragment) {
        fragmentManager.beginTransaction().replace(parentResId, fragment).commit();
    }

}
