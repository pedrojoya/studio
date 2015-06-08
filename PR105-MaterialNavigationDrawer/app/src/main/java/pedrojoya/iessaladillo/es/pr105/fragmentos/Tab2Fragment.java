package pedrojoya.iessaladillo.es.pr105.fragmentos;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import pedrojoya.iessaladillo.es.pr105.R;

public class Tab2Fragment extends Fragment {

    // Retorna una nueva intancia del fragmento.
    public static Tab2Fragment newInstance() {
        return new Tab2Fragment();
    }

    public Tab2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            initVistas(getView());
        }
    }

    private void initVistas(View view) {
        final TextInputLayout tilTelefono = (TextInputLayout) view.findViewById(R.id.tilTelefono);
        final EditText txtTelefono = (EditText) view.findViewById(R.id.txtTelefono);
        txtTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(txtTelefono.getText().toString())) {
                    if (!txtTelefono.getText().toString().startsWith("6")) {
                        tilTelefono.setError("Debe comenzar por 6");
                    } else {
                        tilTelefono.setErrorEnabled(false);
                    }
                } else {
                    tilTelefono.setErrorEnabled(false);
                }
            }
        });
    }
}
