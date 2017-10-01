package es.iessaladillo.pedrojoya.pr216.utils;

import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

@SuppressWarnings("unused")
public class FragmentUtils {

    private FragmentUtils() {
    }

    @SuppressWarnings("SameParameterValue")
    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
            @IdRes int parentResId, @NonNull Fragment fragment, @NonNull String tag) {
        fragmentManager.beginTransaction().replace(parentResId, fragment, tag).commit();
    }

    @SuppressWarnings("SameParameterValue")
    public static void replaceFragmentAddToBackstack(@NonNull FragmentManager fragmentManager,
            @IdRes int parentResId, @NonNull Fragment fragment, @NonNull String tag,
            @NonNull String backstackTag, int transition) {
        fragmentManager.beginTransaction().replace(parentResId, fragment, tag).setTransition(
                transition).addToBackStack(backstackTag).commit();
    }

    public static void replaceFragmentAddToBackstackWithCustomAnimations(
            @NonNull FragmentManager fragmentManager, @IdRes int parentResId,
            @NonNull Fragment fragment, @NonNull String tag, @NonNull String backstackTag,
            @AnimRes int enterAnimation, @AnimRes int exitAnimation, @AnimRes int popEnterAnimation,
            @AnimRes int popExitAnimation) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation,
                        popExitAnimation)
                .replace(parentResId, fragment, tag)
                .addToBackStack(backstackTag)
                .commit();
    }

}
