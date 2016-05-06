package es.iessaladillo.pedrojoya.pr177;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.ParseException;

@SuppressWarnings({"unused", "WeakerAccess"})
public class DataBindingTools {

    // Adaptador para crear atributo app:font en el que establecer el tipo de
    // letra para un TextView.
    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName){
        textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + fontName));
    }

}
