package es.iessaladillo.pedrojoya.pr064.utils;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.iessaladillo.pedrojoya.pr064.R;

class ToolbarPreference extends Preference {

    public ToolbarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        parent.setPadding(0, 0, 0, 0);

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .settings_toolbar, parent, false);

        Toolbar toolbar = layout.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setTitle(getTitle());
        toolbar.setNavigationOnClickListener(v -> {
            PreferenceScreen prefScreen = (PreferenceScreen) getPreferenceManager().findPreference(getKey());
            if (prefScreen != null) {
                prefScreen.getDialog().dismiss();
            }
        });
        return layout;
    }

}
