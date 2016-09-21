package es.iessaladillo.pedrojoya.pr177;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

@SuppressWarnings({"unused", "WeakerAccess"})
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

    @BindingAdapter({"bind:emptyValue"})
    public static void setEmptyValue(final EditText editText, float value) {
        final float valor = value;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    editText.setText(String.format(Locale.getDefault(), "%.2f", valor));
                    editText.selectAll();
                }
                // Se sustituye el '.' por el símbolo decimal (la ',').
                DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols(Locale.getDefault());
                String simboloDecimal = String.valueOf(decimalSymbols.getDecimalSeparator());
                if (editable.toString().contains(".") && !(".".equals(simboloDecimal))) {
                    int position = editText.getSelectionStart();
                    String reemplazo = editable.toString().replace(".", simboloDecimal);
                    editText.setText(reemplazo);
                    editText.setSelection(position);
                }
            }
        });
    }

    @BindingAdapter({"bind:emptyValue"})
    public static void setEmptyValue(final EditText editText, int value) {
        final int valor = value;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    editText.setText(String.format(Locale.getDefault(), "%d", valor));
                    editText.selectAll();
                }
            }
        });
    }

}
