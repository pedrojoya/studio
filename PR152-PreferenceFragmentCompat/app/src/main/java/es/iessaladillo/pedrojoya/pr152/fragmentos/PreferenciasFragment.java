package es.iessaladillo.pedrojoya.pr152.fragmentos;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v14.preference.MultiSelectListPreference;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.SeekBarPreference;

import java.util.Set;

import es.iessaladillo.pedrojoya.pr152.R;
import es.iessaladillo.pedrojoya.pr152.utils.MultiSelectListPreferenceDialogFragmentCompat;
import es.iessaladillo.pedrojoya.pr152.utils.PasswordPreference;
import es.iessaladillo.pedrojoya.pr152.utils.PasswordPreferenceDialogFragmentCompat;

public class PreferenciasFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String FRAGMENT_DIALOG_TAG = "android.support.v7.preference.PreferenceFragment.DIALOG";

    private String mPreferenceScreenKey;

    public static PreferenciasFragment newInstance(String preferenceScreenKey) {
        PreferenciasFragment frg = new PreferenciasFragment();
        Bundle argumentos = new Bundle();
        argumentos.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, preferenceScreenKey);
        frg.setArguments(argumentos);
        return frg;
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String key) {
        mPreferenceScreenKey = key;
        // Se construye el fragmento de preferencias a partir de la
        // especificación XML de preferencias.
        //this.addPreferencesFromResource(R.xml.preferencias);
        setPreferencesFromResource(R.xml.preferencias, key);
        // Se inicializan los summary con el valor de la preferencia.
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            inicializarSummary(getPreferenceScreen().getPreference(i));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle((mPreferenceScreenKey == null) ?
                    getString(R.string.title_activity_preferencias) :
                    findPreference(mPreferenceScreenKey).getTitle());
        }
        super.onActivityCreated(savedInstanceState);
    }

    // Inicializa el summary de una preferencia. Llama recursivamente si se
    // trata de una pantalla de preferencias o una categoría de preferencias.
    private void inicializarSummary(Preference preferencia) {
        // Si la preferencia corresponde a otra pantalla de preferencias, se
        // llama recursivamente.
        if (preferencia instanceof PreferenceScreen) {
            PreferenceScreen pantalla = (PreferenceScreen) preferencia;
            for (int i = 0; i < pantalla.getPreferenceCount(); i++) {
                inicializarSummary(pantalla.getPreference(i));
            }
        }
        // Si la preferencia corresponde a una categoría de preferencias, se
        // llama recursivamente.
        else if (preferencia instanceof PreferenceCategory) {
            PreferenceCategory categoria = (PreferenceCategory) preferencia;
            for (int i = 0; i < categoria.getPreferenceCount(); i++) {
                inicializarSummary(categoria.getPreference(i));
            }
        } else {
            // Se actualiza el valor del summary a partir del valor de la
            // preferencia.
            actualizarSummary(preferencia);
        }

    }

    @Override
    public void onPause() {
        // Se elimina la actividad como listener de los cambios en las
        // preferencias.
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        // Se registra la actividad como listener de los cambios en las
        // preferencias.
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    // Cuando se cambia el valor de una preferencia.
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        // Se busca esa preferencia en el árbol de preferencias y se actualiza
        // el summary a partir del valor que contenga.
        actualizarSummary(findPreference(key));
    }

    // Actualiza el summary de una preferencia dependiendo del tipo que sea.
    private void actualizarSummary(Preference preferencia) {
        // Si es un PasswordPreference.
        if (preferencia instanceof PasswordPreference) {
            PasswordPreference pref = (PasswordPreference) preferencia;
            // Se establece como summary el valor textual de la preferencia
            // convertida a asteriscos.
            String texto = pref.getText();
            if (texto != null) {
                pref.setSummary(new String(new char[pref.getText().length()]).replace("\0", "*"));
            }
        }
        // Si es un EditTextPreference.
        else if (preferencia instanceof EditTextPreference) {
            EditTextPreference pref = (EditTextPreference) preferencia;
            // Se establece como summary el valor (textual) de la preferencia.
            pref.setSummary(pref.getText());
        }
        // Si es un ListPreference.
        else if (preferencia instanceof ListPreference) {
            ListPreference pref = (ListPreference) preferencia;
            pref.setSummary(pref.getEntry());
        }
        // Si es un MultiSelectListPreference.
        else if (preferencia instanceof MultiSelectListPreference) {
            MultiSelectListPreference pref = (MultiSelectListPreference) preferencia;
            Set<String> seleccionados = pref.getValues();
            pref.setSummary(seleccionados.toString());
        }
        // Si es un SeekBarPreference.
        else if (preferencia instanceof SeekBarPreference) {
            SeekBarPreference pref = (SeekBarPreference) preferencia;
            pref.setSummary(String.valueOf(pref.getValue()));
        }
    }

    // Hack para que funcione MultiSelectListPreferenceDialog.
    // Referencia: https://github.com/caarmen/network-monitor/tree/master/networkmonitor/src/main/java/ca/rmen/android/networkmonitor/app/prefs/hack
    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH
                && ((preference instanceof MultiSelectListPreference)
                || (preference instanceof PasswordPreference))) {
            // Diálogo personalizado.
            onDisplayPreferenceDialog(this, preference);
        }
        else {
            // Diálogo estándar.
            super.onDisplayPreferenceDialog(preference);
        }
    }

    /**
     * Displays preference dialogs which aren't supported by default in PreferenceFragmentCompat.
     *
     * @return true if we managed a preference which isn't supported by default, false otherwise.
     */

    @SuppressWarnings("UnusedReturnValue")
    private static boolean onDisplayPreferenceDialog(PreferenceFragmentCompat preferenceFragmentCompat, Preference preference) {
        DialogFragment dialogFragment = (DialogFragment) preferenceFragmentCompat.getFragmentManager().findFragmentByTag(FRAGMENT_DIALOG_TAG);
        if (dialogFragment != null) return false;

        // Hack to allow a MultiSelectListPreference
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH
                && preference instanceof MultiSelectListPreference) {
            dialogFragment = MultiSelectListPreferenceDialogFragmentCompat.newInstance(preference.getKey());
        }
        // Hack to allow a PasswordPreference
        else if (preference instanceof PasswordPreference) {
            dialogFragment = PasswordPreferenceDialogFragmentCompat.newInstance(preference.getKey());
        }

        // We've created our own fragment:
        if (dialogFragment != null) {
            dialogFragment.setTargetFragment(preferenceFragmentCompat, 0);
            dialogFragment.show(preferenceFragmentCompat.getFragmentManager(), FRAGMENT_DIALOG_TAG);
            return true;
        }
        return false;
    }
}
