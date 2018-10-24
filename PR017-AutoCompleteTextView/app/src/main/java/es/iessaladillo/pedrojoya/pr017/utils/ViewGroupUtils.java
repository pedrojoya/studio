package es.iessaladillo.pedrojoya.pr017.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

public class ViewGroupUtils {

    private ViewGroupUtils() {
    }

    public static View inflate(ViewGroup parent, @LayoutRes int layoutResId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
    }

}
