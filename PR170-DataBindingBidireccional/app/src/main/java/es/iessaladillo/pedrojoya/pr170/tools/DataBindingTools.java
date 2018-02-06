package es.iessaladillo.pedrojoya.pr170.tools;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    @BindingAdapter("app:labelForView")
    public static void setLabelForView(TextView textView, View forView) {
        forView.setOnFocusChangeListener((v, hasFocus) -> textView.setTypeface(null,
                hasFocus ? Typeface.BOLD : Typeface.NORMAL));
        textView.setOnClickListener(v -> {
            forView.requestFocus();
            forView.performClick();
        });
    }

    @BindingAdapter("app:boldOnFocus")
    public static void setBoldOnFocus(TextView textView, boolean bold) {
        if (bold) {
            textView.setOnFocusChangeListener((v, hasFocus) -> textView.setTypeface(null,
                    hasFocus ? Typeface.BOLD : Typeface.NORMAL));
        }
    }

}
