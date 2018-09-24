package pedrojoya.iessaladillo.es.pr243.base;

import androidx.appcompat.view.ActionMode;

// Extiende ActionMode.Callback para añadirle el evento de que cambia la selección.
@SuppressWarnings("unused")
public interface MultiChoiceModeListener extends ActionMode.Callback {

    void onSelectionChanged(ActionMode mode, int selected);

}