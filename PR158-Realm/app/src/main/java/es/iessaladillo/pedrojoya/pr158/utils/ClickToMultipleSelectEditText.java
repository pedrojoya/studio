package es.iessaladillo.pedrojoya.pr158.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr158.R;

@SuppressWarnings("unused")
public class ClickToMultipleSelectEditText<T> extends TextInputEditText implements
        DialogInterface.OnMultiChoiceClickListener {

    private static final String STATE_ITEMS = "state_items";
    private static final String STATE_SELECTION = "state_selection";
    private static final String STATE_SUPER = "superState";
    private static final String STATE_SELECTION_AT_START = "state_selection_at_start";
    private static final String STATE_ITEMS_AT_START = "state_items_at_start";

    // Interfaz de comunicación para cuando se haga la selección.
    @SuppressWarnings({"EmptyMethod", "UnusedParameters"})
    public interface OnMultipleItemsSelectedListener{
        void selectedIndices(List<Integer> indices);
        void selectedStrings(List<String> strings);
    }

    // Listener que será notificado cuando se realice la selección.
    private OnMultipleItemsSelectedListener listener;
    // Cadenas que se mostrarán en el diálogo de multiselección.
    private String[] _items = null;
    // Elementos seleccionados
    private boolean[] mSelection = null;
    // Elementos seleccionados inicialmente (para dar marcha atrás si se cancela).
    private boolean[] mSelectionAtStart = null;
    // Elementos seleccionados inicialmente (en forma de cadena).
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private String _itemsAtStart = null;
    private String mDialogTitle;

    // Constructores.
    public ClickToMultipleSelectEditText(Context context) {
        super(context);
        mDialogTitle = context.getString(R.string.seleccionar);
    }

    public ClickToMultipleSelectEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDialogTitle = context.getString(R.string.seleccionar);
    }

    public ClickToMultipleSelectEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDialogTitle = context.getString(R.string.seleccionar);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setFocusable(true);
        setClickable(true);
        setInputType(InputType.TYPE_NULL);
        setKeyListener(null);
    }

    // Establece el listener para cuando haya una selección.
    public void setListener(OnMultipleItemsSelectedListener listener){
        this.listener = listener;
    }

    public void setDialogTitle(String dialogTitle) {
        mDialogTitle = dialogTitle;
    }

    // Cada vez que se selecciona o quita la selección a un elemento del diálogo.
    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        // Se actaliza el estado de selección de dicho elemento en el array.
        if (mSelection != null && which < mSelection.length) {
            mSelection[which] = isChecked;
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }

    // Configura la vista para que se muestre el diálogo al hacer click o al obtener el foco.
    private void configureOnClickListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showDialog(view);
                }
            }
        });
    }

    // Muestra el diálogo de selección.
    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(mDialogTitle);
        builder.setMultiChoiceItems(_items, mSelection, this);
        _itemsAtStart = getSelectedItemsAsString();
        // Cuando se pulsa ok.
        builder.setPositiveButton(getContext().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se copia la selección actual al array de selección inicial.
                System.arraycopy(mSelection, 0, mSelectionAtStart, 0, mSelection.length);
                // Se realizan las llamadas callback al listener.
                listener.selectedIndices(getSelectedIndices());
                listener.selectedStrings(getSelectedStrings());
            }
        });
        // Cuando se pulsa cancelar.
        builder.setNegativeButton(getContext().getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se resetea al estado de selección al existente antes de mostrar el diálogo.
                System.arraycopy(mSelectionAtStart, 0, mSelection, 0, mSelectionAtStart.length);
            }
        });
        builder.show();
    }

    // Establece los elementos a mostrar en el diálogo (cadenas)
    public void setItems(String[] items) {
        _items = items;
        // Se crean los array de selección del tamaño adecuado. Por defecto ningún
        // elemento estará seleccionado.
        mSelection = new boolean[_items.length];
        mSelectionAtStart = new boolean[_items.length];
        Arrays.fill(mSelection, false);
        // Se configura la vista para que al hacer click u obtener el foco se muestre el diálogo.
        configureOnClickListener();
    }

    // Similar al anterior pero recibe una lista en vez de un array.
    public void setItems(List<String> items) {
        _items = items.toArray(new String[items.size()]);
        mSelection = new boolean[_items.length];
        mSelectionAtStart  = new boolean[_items.length];
        Arrays.fill(mSelection, false);
        mSelection[0] = true;
        configureOnClickListener();
    }

    // Establece la selección a partir de un array de elementos (cadenas).
    public void setSelection(String[] selection) {
        // Se resetean los arrays de selección.
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        // Se busca cada cadena recibida en el array de elementos y si se encuentra se selecciona
        // en el array de selección.
        for (String cell : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(cell)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
    }

    // Establece la selección a partir de una lista de elementos (cadenas). Similar al anterior.
    public void setSelection(List<String> selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (String sel : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(sel)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
    }

    // Establece la selección a partir de un ínidce único.
    public void setSelection(int index) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
            mSelectionAtStart[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
    }

    // Establece la selección a partir de un array de índices.
    public void setSelection(int[] selectedIndices) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (int index : selectedIndices) {
            if (index >= 0 && index < mSelection.length) {
                mSelection[index] = true;
                mSelectionAtStart[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index
                        + " is out of bounds.");
            }
        }
    }

    // Retorna la lista de elementos seleccionados (cadenas).
    private List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(_items[i]);
            }
        }
        return selection;
    }

    // Retorna la lista de índices de elementos seleccionados.
    public List<Integer> getSelectedIndices() {
        List<Integer> selection = new LinkedList<>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;

                sb.append(_items[i]);
            }
        }
        return sb.toString();
    }

    // Retorna una cadena única con los elementos seleccionados.
    private String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(_items[i]);
            }
        }
        return sb.toString();
    }

    @Override
    public Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        bundle.putStringArray(STATE_ITEMS, _items);
        bundle.putBooleanArray(STATE_SELECTION, mSelection);
        bundle.putBooleanArray(STATE_SELECTION_AT_START, mSelectionAtStart);
        bundle.putString(STATE_ITEMS_AT_START, _itemsAtStart);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state)
    {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            _items = bundle.getStringArray(STATE_ITEMS);
            mSelection = bundle.getBooleanArray(STATE_SELECTION);
            mSelectionAtStart = bundle.getBooleanArray(STATE_SELECTION_AT_START);
            _itemsAtStart = bundle.getString(STATE_ITEMS_AT_START);
            state = bundle.getParcelable(STATE_SUPER);
        }
        super.onRestoreInstanceState(state);
    }

}