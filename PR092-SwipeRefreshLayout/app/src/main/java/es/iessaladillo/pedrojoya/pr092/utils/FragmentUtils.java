package es.iessaladillo.pedrojoya.pr092.utils;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentUtils {

    private FragmentUtils() { }

    @SuppressWarnings("SameParameterValue")
    public static void replaceFragment(FragmentManager fragmentManager, @IdRes int parentResId,
            Fragment fragment) {
        fragmentManager.beginTransaction().replace(parentResId, fragment).commit();
    }

}
