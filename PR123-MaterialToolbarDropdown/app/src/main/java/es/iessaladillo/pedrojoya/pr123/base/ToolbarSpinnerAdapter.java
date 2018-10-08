package es.iessaladillo.pedrojoya.pr123.base;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ThemedSpinnerAdapter;

// Must implement ThemedSpinnerAdapter and receive toolbar's context.
public class ToolbarSpinnerAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {

    private final Helper dropDownHelper;
    private final List<String> data;

    public ToolbarSpinnerAdapter(Context context, List<String> data) {
        super(context, android.R.layout.simple_spinner_dropdown_item, data);
        // Helper to apply the theme properly.
        dropDownHelper = new Helper(context);
        this.data = data;
    }

    // When spinner is expanded.
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // Get inflater from helper (in order to use the right theme).
            LayoutInflater inflater = dropDownHelper.getDropDownViewInflater();
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        // android.R.layout.simple_spinner_dropdown_item has a simple CheckedTextView.
        ((CheckedTextView) convertView).setText(data.get(position));
        return convertView;
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        // Set helper's theme as dropdownview theme.
        dropDownHelper.setDropDownViewTheme(theme);
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        // Return helper's theme.
        return dropDownHelper.getDropDownViewTheme();
    }

}