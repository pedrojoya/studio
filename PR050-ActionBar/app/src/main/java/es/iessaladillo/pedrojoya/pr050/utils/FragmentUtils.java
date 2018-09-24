package es.iessaladillo.pedrojoya.pr050.utils;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

@SuppressWarnings("SameParameterValue")
public class FragmentUtils {

    private FragmentUtils() {
    }

    public static void replaceFragmentAddToBackstack(@NonNull FragmentManager fragmentManager,
            @IdRes int parentResId, @NonNull Fragment fragment, @NonNull String tag,
            @NonNull String backstackTag, int transition) {
        fragmentManager.beginTransaction().replace(parentResId, fragment, tag).setTransition(
                transition).addToBackStack(backstackTag).commit();
    }

}
