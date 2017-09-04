package es.iessaladillo.pedrojoya.pr123.utils;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

@SuppressWarnings({"SameParameterValue", "unused"})
public class FragmentUtils {

    private FragmentUtils() {
    }

    public static void addFragment(@NonNull FragmentManager fragmentManager, @IdRes int parentResId,
            @NonNull Fragment fragment, @NonNull String tag) {
        fragmentManager.beginTransaction().add(parentResId, fragment, tag).commit();
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
            @IdRes int parentResId, @NonNull Fragment fragment, @NonNull String tag) {
        fragmentManager.beginTransaction().replace(parentResId, fragment, tag).commit();
    }

    public static void replaceFragmentAddToBackstack(@NonNull FragmentManager fragmentManager,
            @IdRes int parentResId, @NonNull Fragment fragment, @NonNull String tag,
            @NonNull String backstackTag, int transition) {
        fragmentManager.beginTransaction().replace(parentResId, fragment, tag).setTransition(
                transition).addToBackStack(backstackTag).commit();
    }

}
