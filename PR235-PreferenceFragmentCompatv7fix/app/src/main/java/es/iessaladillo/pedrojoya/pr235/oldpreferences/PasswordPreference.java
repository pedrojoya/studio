package es.iessaladillo.pedrojoya.pr235.oldpreferences;

import android.content.Context;
import android.support.v7.preference.EditTextPreference;
import android.util.AttributeSet;


@SuppressWarnings({"WeakerAccess", "unused"})
public class PasswordPreference extends EditTextPreference {
    public PasswordPreference(Context context) {
        super(context);
    }

    public PasswordPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PasswordPreference(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
