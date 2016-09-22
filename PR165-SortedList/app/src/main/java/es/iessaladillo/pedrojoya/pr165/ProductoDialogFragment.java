package es.iessaladillo.pedrojoya.pr165;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProductoDialogFragment extends DialogFragment {

    private ProductoDialogListener mListener = null;
    private EditText txtNombre;
    private EditText txtCantidad;
    private EditText txtUnidad;
    private TextInputLayout tilNombre;
    private TextInputLayout tilCantidad;
    private TextInputLayout tilUnidad;
    private Button btnAceptar;

    // Interfaz pública para comunicación con la actividad.
    public interface ProductoDialogListener {
        void onAgregarClick(Producto producto);

        @SuppressWarnings("EmptyMethod")
        void onCancelarClick();
    }

    // Al crear el diálogo. Retorna el diálogo configurado.
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.agregar_producto);
        @SuppressLint("InflateParams") final View layout = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_producto, null);
        initVistas(layout);
        b.setView(layout);
        b.setPositiveButton(R.string.aceptar, (dialog, which) -> {
            // Se obtiene el producto.
            Producto producto = vistasToProducto();
            // Se notifica el evento al listener.
            mListener.onAgregarClick(producto);
        });
        b.setNeutralButton(R.string.cancelar, (dialog, which) -> {
            // Se notifica el evento al listener.
            mListener.onCancelarClick();
        });
        return b.create();
    }

    // Cuando se inicia el fragmento.
    @Override
    public void onStart() {
        super.onStart();
        // Se obtiene el botón positivo (para poder deshabilitarlo si el
        // formulario no es válido).
        btnAceptar = ((AlertDialog) getDialog()).getButton(Dialog.BUTTON_POSITIVE);
        btnAceptar.setEnabled(isValidForm());
    }

    // Obtiene e inicializa las vistas.
    private void initVistas(View layout) {
        tilNombre = (TextInputLayout) layout.findViewById(R.id.tilNombre);
        tilCantidad = (TextInputLayout) layout.findViewById(R.id.tilCantidad);
        tilUnidad = (TextInputLayout) layout.findViewById(R.id.tilUnidad);
        txtNombre = (EditText) layout.findViewById(R.id.txtNombre);
        txtNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Cuando se cambia el texto escrito se comprueba su validez.
                if (TextUtils.isEmpty(editable.toString())) {
                    tilNombre.setError(getString(R.string.obligatorio));
                    tilNombre.setErrorEnabled(true);
                } else {
                    tilNombre.setError("");
                    tilNombre.setErrorEnabled(false);
                }
                if (btnAceptar != null) {
                    btnAceptar.setEnabled(isValidForm());
                }
            }
        });
        txtCantidad = (EditText) layout.findViewById(R.id.txtCantidad);
        txtCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Cuando se cambia el texto escrito se comprueba su validez.
                if (TextUtils.isEmpty(txtCantidad.getText().toString())) {
                    tilCantidad.setError(getString(R.string.obligatorio));
                    tilCantidad.setErrorEnabled(true);
                } else if (Float.parseFloat(txtCantidad.getText().toString()) <= 0) {
                    tilCantidad.setError(getString(R.string.mayor_que_0));
                    tilCantidad.setErrorEnabled(true);
                } else {
                    tilCantidad.setError("");
                    tilCantidad.setErrorEnabled(false);
                }
                if (btnAceptar != null) {
                    btnAceptar.setEnabled(isValidForm());
                }
            }
        });
        txtUnidad = (EditText) layout.findViewById(R.id.txtUnidad);
        txtUnidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Cuando se cambia el texto escrito se comprueba su validez.
                if (TextUtils.isEmpty(editable.toString())) {
                    tilUnidad.setError(getString(R.string.obligatorio));
                    tilUnidad.setErrorEnabled(true);
                } else {
                    tilUnidad.setError("");
                    tilUnidad.setErrorEnabled(false);
                }
                if (btnAceptar != null) {
                    btnAceptar.setEnabled(isValidForm());
                }
            }
        });
    }

    // Retorna si el formulario es válido.
    private boolean isValidForm() {
        // Nombre.
        if (TextUtils.isEmpty(txtNombre.getText().toString())) {
            return false;
        }
        // Cantidad.
        if (TextUtils.isEmpty(txtCantidad.getText().toString())) {
            return false;
        } else if (Float.parseFloat(txtCantidad.getText().toString()) <= 0) {
            return false;
        }
        // Unidad.
        //noinspection RedundantIfStatement
        if (TextUtils.isEmpty(txtUnidad.getText().toString())) {
            return false;
        }
        return true;
    }

    // Retorna un nuevo objeto producto configurado con los datos introducidos
    // por el usuario en las vistas correspondientes.
    private Producto vistasToProducto() {
        return new Producto(
                txtNombre.getText().toString(),
                Float.parseFloat(txtCantidad.getText().toString()),
                txtUnidad.getText().toString());

    }

    // Al enlazar el fragmento con la actividad.
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del diálogo.
        try {
            mListener = (ProductoDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar ProductoDialogListener");
        }
    }

}
