package es.iessaladillo.pedrojoya.pr151.tools;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class DataBindingTools {

    private DataBindingTools() {
    }

    // Adaptador para crear atributo app:font en el que establecer el tipo de
    // letra para un TextView.
    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(
                Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + fontName));
    }

    // Adaptador para crear atributo app:imageUrl en el que establecer la URL
    // de la imagen que debe cargarse en un ImageView a través de Picasso.
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
