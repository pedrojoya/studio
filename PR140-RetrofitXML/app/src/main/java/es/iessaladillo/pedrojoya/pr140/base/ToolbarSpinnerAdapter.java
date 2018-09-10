package es.iessaladillo.pedrojoya.pr140.base;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.List;

// Adaptador para spinner en toolbar. Para que se muestre siguiendo el tema
// adecuado debe implementar ThemedSpinnerAdapter y recibir el contexto de
// la toolbar.
@SuppressWarnings("WeakerAccess")
public class ToolbarSpinnerAdapter<T> extends ArrayAdapter<T> implements ThemedSpinnerAdapter {

    // Helper in order to be drawn with the right theme
    private final Helper helper;
    private final List<T> data;

    public ToolbarSpinnerAdapter(Context context, List<T> data) {
        super(context, android.R.layout.simple_spinner_dropdown_item, data);
        helper = new Helper(context);
        this.data = data;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // Use helper's inflater.
            convertView = helper.getDropDownViewInflater().inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        // android.R.layout.simple_spinner_dropdown_item layout has only a CheckedTextView.
        ((CheckedTextView) convertView).setText(data.get(position).toString());
        return convertView;
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        // Set helper's theme
        helper.setDropDownViewTheme(theme);
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        // Get helper's theme
        return helper.getDropDownViewTheme();
    }

}
