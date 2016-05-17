package es.iessaladillo.pedrojoya.pr153;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

@SuppressWarnings({"unused", "WeakerAccess"})
public class BindingAdapters {
    // El pseudoatributo app:imageUrl ejecuta este método.
    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }
}
