package es.iessaladillo.pedrojoya.pr170.old;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

@SuppressWarnings({"unused", "WeakerAccess"})
public class OldDataBindingTools {

    private OldDataBindingTools() {
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

    @BindingAdapter({"bind:data"})
    public static void setData(final ClickToSelectEditText textView, CharSequence[] data) {
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter<>(textView.getContext(),
                android.R.layout.simple_list_item_1, data);
        textView.setAdapter(adaptador);
        //noinspection unchecked
        textView.setOnItemSelectedListener((item, selectedIndex) -> textView.setText(item.toString()));
        textView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                textView.showDialog(v);
            }
        });
    }

    // Para establecer un drawable vectorial como drawableLeft de un textview.
    @BindingAdapter("bind:vectorDrawableLeft")
    public static void setDrawableLeft(TextView textView, int resourceId) {
        Drawable drawable = VectorDrawableCompat.create(textView.getResources(), resourceId,
                textView.getContext().getTheme());
        Drawable[] drawables = textView.getCompoundDrawables();
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawables[1], drawables[2],
                drawables[3]);
    }



}
