package pedrojoya.iessaladillo.es.pr204.component;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pedrojoya.iessaladillo.es.pr204.base.ImageLoader;

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void loadImageIntoImageView(String url, ImageView v,
            @DrawableRes int placeholderDrawableResId, @DrawableRes int errorDrawableResId) {
        Picasso.with(v.getContext()).load(url).placeholder(placeholderDrawableResId).error(
                errorDrawableResId).into(v);
    }

}
