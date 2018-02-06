package es.iessaladillo.pedrojoya.pr153.tools;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

@SuppressWarnings({"unused", "WeakerAccess"})
public class DataBindingTools {

    private DataBindingTools() {
    }

    // BindingAdapter to load image with Picasso
    @BindingAdapter(value = {"bind:imageUrl", "bind:placeholder", "bind:error"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String url, Drawable drawablePlaceholder,
            Drawable drawableError) {
        if (!TextUtils.isEmpty(url)) {
            RequestCreator peticion = Picasso.with(imageView.getContext()).load(url);
            if (drawablePlaceholder != null) {
                peticion.placeholder(drawablePlaceholder);
            }
            if (drawableError != null) {
                peticion.error(drawableError);
            }
            peticion.into(imageView);
        }
    }

}
