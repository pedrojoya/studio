package es.iessaladillo.pedrojoya.pr178.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidx.annotation.DrawableRes;

public class PicassoUtils {

    private PicassoUtils() {
    }

    public static void loadUrl(ImageView imageView, String url, @DrawableRes int placeholderResId) {
        Picasso.with(imageView.getContext()).load(url).placeholder(placeholderResId).error(
                placeholderResId).into(imageView);
    }

}
