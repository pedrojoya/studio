package pedrojoya.iessaladillo.es.pr201.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class PicassoUtils {

    private PicassoUtils() {
    }

    public static void loadUrl(@NonNull ImageView imageView, @NonNull String url, @DrawableRes int placeholderResId) {
        Picasso.with(imageView.getContext()).load(url).placeholder(placeholderResId).error(
                placeholderResId).into(imageView);
    }

}
