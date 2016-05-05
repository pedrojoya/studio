package es.iessaladillo.pedrojoya.pr170.tools;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;

import es.iessaladillo.pedrojoya.pr170.R;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Converters {

    // Retorna la cadena correspondiente a la conversión desde un
    // bindable a una cadena.
    @BindingConversion
    public static String convertBindableToString(
            BindableString bindableString) {
        // Retorna la cadena contenida dentro del bindable.
        return bindableString.get();
    }

    // Retorna el booleano correspondiente a la conversión desde un bindable
    // a un booleano.
    @BindingConversion
    public static boolean convertBindableToBoolean(BindableBoolean bindableBoolean) {
        return bindableBoolean.get();
    }

    // Cuando se utilice la atributo app:binding en el layout sobre un
    // EditText y el valor sea un BindableString.
    @BindingAdapter({"bind:binding"})
    public static void bindEditText(final EditText view,
                                    final BindableString bindableString) {
        // Se añade el listener una sola vez (para eso usamos el tag).
        if (view.getTag(R.id.binded) == null) {
            view.setTag(R.id.binded, true);
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // Se actualiza el bindable cuando se cambia el valor en el
                    // EditText. Al pasar false como segundo parámetro, el cambio NO se notificará
                    // y el texto del EditText NO será notificado, por lo que NO se volverá a colocar
                    // el puntero del cursor al principio.
                    bindableString.set(editable.toString(), false);
                }
            });
        }
        // Si el valor del bindable es diferente al que había en el EditText,
        // se cambia.
        String newValue = bindableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }

    // Al indicar como vista CompoundButton, podemos usar este adaptador con CheckBox, RadioButton,
    // Switch, SwitchCompat, ToggleButton.
    @BindingAdapter({"bind:binding"})
    public static void bindCheckBox(CompoundButton view, final BindableBoolean bindableBoolean) {
        if (view.getTag(R.id.binded) == null) {
            view.setTag(R.id.binded, true);
            view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    // Se actualiza el bindable cuando se cambia el valor en el
                    // CheckBox.
                    bindableBoolean.set(b);
                }
            });
        }
        // Si el valor del bindable es diferente al que había en el CheckBox,
        // se cambia.
        Boolean newValue = bindableBoolean.get();
        view.setChecked(newValue);
    }

}
