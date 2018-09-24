package pedrojoya.iessaladillo.es.pr229.utils;

import androidx.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoUtils {

    private PicassoUtils() {
    }

    public static void loadUrl(ImageView imageView, String url, @DrawableRes int placeholderResId) {
        Picasso.with(imageView.getContext()).load(url).placeholder(placeholderResId).error(
                placeholderResId).into(imageView);
    }

}
