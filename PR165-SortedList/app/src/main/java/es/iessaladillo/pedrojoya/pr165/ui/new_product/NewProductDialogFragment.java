package es.iessaladillo.pedrojoya.pr165.ui.new_product;

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

import es.iessaladillo.pedrojoya.pr165.R;
import es.iessaladillo.pedrojoya.pr165.data.model.Product;

public class NewProductDialogFragment extends DialogFragment {

    private EditText txtName;
    private EditText txtQuantity;
    private EditText txtUnit;
    private TextInputLayout tilName;
    private TextInputLayout tilQuantity;
    private TextInputLayout tilUnit;
    private Button btnAccept;

    private Callback mListener;

    // Communication interface.
    public interface Callback {
        void onAddClick(Product product);

        @SuppressWarnings("EmptyMethod")
        void onCancelClick();
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.newproduct_fragment_add_product);
        @SuppressLint("InflateParams")
        final View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_dialog_new_product, null);
        initViews(view);
        b.setView(view);
        b.setPositiveButton(R.string.newproduct_fragment_accept,
                (dialog, which) -> mListener.onAddClick(viewsToProduct()));
        b.setNegativeButton(R.string.newproduct_fragment_cancel,
                (dialog, which) -> mListener.onCancelClick());
        return b.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Get possitive button to enable/disable it.
        btnAccept = ((AlertDialog) getDialog()).getButton(Dialog.BUTTON_POSITIVE);
        btnAccept.setEnabled(isValidForm());
    }

    private void initViews(View view) {
        tilName = view.findViewById(R.id.tilName);
        txtName = view.findViewById(R.id.txtName);
        tilQuantity = view.findViewById(R.id.tilQuantity);
        txtQuantity = view.findViewById(R.id.txtQuantity);
        tilUnit = view.findViewById(R.id.tilUnit);
        txtUnit = view.findViewById(R.id.txtUnit);

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateName(editable);
            }
        });
        txtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateQuantity();
            }
        });
        txtUnit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateUnit(editable);
            }
        });
    }

    private void validateName(Editable editable) {
        if (TextUtils.isEmpty(editable.toString())) {
            tilName.setError(getString(R.string.newproduct_fragment_required));
            tilName.setErrorEnabled(true);
        } else {
            tilName.setError("");
            tilName.setErrorEnabled(false);
        }
        if (btnAccept != null) {
            btnAccept.setEnabled(isValidForm());
        }
    }

    private void validateQuantity() {
        if (TextUtils.isEmpty(txtQuantity.getText().toString())) {
            tilQuantity.setError(getString(R.string.newproduct_fragment_required));
            tilQuantity.setErrorEnabled(true);
        } else if (Float.parseFloat(txtQuantity.getText().toString()) <= 0) {
            tilQuantity.setError(getString(R.string.newproduct_fragment_greater_than_0));
            tilQuantity.setErrorEnabled(true);
        } else {
            tilQuantity.setError("");
            tilQuantity.setErrorEnabled(false);
        }
        if (btnAccept != null) {
            btnAccept.setEnabled(isValidForm());
        }
    }

    private void validateUnit(Editable editable) {
        if (TextUtils.isEmpty(editable.toString())) {
            tilUnit.setError(getString(R.string.newproduct_fragment_required));
            tilUnit.setErrorEnabled(true);
        } else {
            tilUnit.setError("");
            tilUnit.setErrorEnabled(false);
        }
        if (btnAccept != null) {
            btnAccept.setEnabled(isValidForm());
        }
    }

    private boolean isValidForm() {
        return !(TextUtils.isEmpty(txtName.getText().toString()) ||
                TextUtils.isEmpty(txtQuantity.getText().toString()) ||
                TextUtils.isEmpty(txtQuantity.getText().toString()) ||
                Float.parseFloat(txtQuantity.getText().toString()) <= 0 ||
                TextUtils.isEmpty(txtUnit.getText().toString()));
    }

    private Product viewsToProduct() {
        return new Product(0, txtName.getText().toString(),
                Float.parseFloat(txtQuantity.getText().toString()), txtUnit.getText().toString());
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Callback");
        }
    }

}
