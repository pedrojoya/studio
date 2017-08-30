package es.iessaladillo.pedrojoya.pr011.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

public class ImageViewUtils {

    private ImageViewUtils() { }

    @SuppressWarnings("SameParameterValue")
    public static void tintImageView(@NonNull ImageView imageView, @ColorRes int colorRes) {
        ColorStateList colors = ContextCompat.getColorStateList(imageView.getContext(), colorRes);
        Drawable d = DrawableCompat.wrap(imageView.getDrawable());
        DrawableCompat.setTintList(d, colors);
        imageView.setImageDrawable(d);
    }

}
