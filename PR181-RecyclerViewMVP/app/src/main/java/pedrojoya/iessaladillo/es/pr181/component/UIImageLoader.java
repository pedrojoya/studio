package pedrojoya.iessaladillo.es.pr181.component;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class UIImageLoader {

    public static void loadImageIntoImageView(String url, ImageView v,
                                              @DrawableRes int placeholderDrawableResId,
                                              @DrawableRes int errorDrawableResId) {
        Picasso.with(v.getContext())
                .load(url)
                .placeholder(placeholderDrawableResId)
                .error(errorDrawableResId)
                .into(v);
    }

}
