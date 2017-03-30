package pedrojoya.iessaladillo.es.pr204.base;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public interface ImageLoader {

    void loadImageIntoImageView(String url, ImageView v, @DrawableRes int placeholderDrawableResId,
                                @DrawableRes int errorDrawableResId);

}
