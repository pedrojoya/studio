package es.iessaladillo.pedrojoya.pr152.utils;

import android.os.Bundle;
import android.support.v7.preference.EditTextPreferenceDialogFragmentCompat;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

/**
 * Hack to set the input type of an EditTextPreference to "password".
 */
public class PasswordPreferenceDialogFragmentCompat extends EditTextPreferenceDialogFragmentCompat {
    public PasswordPreferenceDialogFragmentCompat() {
    }

    public static PasswordPreferenceDialogFragmentCompat newInstance(String key) {
        PasswordPreferenceDialogFragmentCompat fragment = new
                PasswordPreferenceDialogFragmentCompat();
        Bundle b = new Bundle(1);
        b.putString("key", key);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        EditText editText = (EditText) view.findViewById(android.R.id.edit);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
}
