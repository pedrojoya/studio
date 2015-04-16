package es.iessaladillo.pedrojoya.pr133;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private static final float DEFAULT_CUENTA = 0.00f;
    private static final int DEFAULT_PORCENTAJE = 10;

    private TextView lblCuenta;
    private EditText txtCuenta;
    private TextView lblPorcentaje;
    private EditText txtPorcentaje;
    private TextView lblPropina;
    private EditText txtPropina;
    private TextView lblTotal;
    private EditText txtTotal;
    private Button btnRedondear;
    private Button btnLimpiar;

    private NumberFormat mFormateador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFormateador = NumberFormat.getInstance(Locale.getDefault());
        initVistas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtCuenta.requestFocus();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lblCuenta = (TextView) findViewById(R.id.lblCuenta);
        txtCuenta = (EditText) findViewById(R.id.txtCuenta);
        txtCuenta.setSelectAllOnFocus(true);
        txtCuenta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblCuenta, hasFocus);
                if (!hasFocus) {
                    formatMoneda(txtCuenta);
                }
            }
        });
        // Sólo se pueden hacer operaciones si tenemos todos los datos.
        txtCuenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            // Después de haber cambiado el texto.
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    txtCuenta.setText(String.format(Locale.getDefault(), "%.2f",
                            DEFAULT_CUENTA));
                }
                // Comprobar si hay datos para hacer operaciones.
                checkDatos();
            }
        });
        lblPorcentaje = (TextView) findViewById(R.id.lblPorcentaje);
        txtPorcentaje = (EditText) findViewById(R.id.txtPorcentaje);
        txtPorcentaje.setSelectAllOnFocus(true);
        txtPorcentaje.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblPorcentaje, hasFocus);
            }
        });
        // Sólo se pueden hacer operaciones si tenemos todos los datos.
        txtPorcentaje.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            // Después de haber cambiado el texto.
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    txtPorcentaje.setText("0");
                }
                // Comprobar si hay datos para hacer operaciones.
                checkDatos();
            }
        });
        lblPropina = (TextView) findViewById(R.id.lblPropina);
        txtPropina = (EditText) findViewById(R.id.txtPropina);
        lblTotal = (TextView) findViewById(R.id.lblTotal);
        txtTotal = (EditText) findViewById(R.id.txtTotal);
        btnRedondear = (Button) findViewById(R.id.btnRedondear);
        btnRedondear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redondear();
            }
        });
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
            }
        });
        // Comprobaciones iniciales.
        limpiar();
        setColorSegunFoco(lblCuenta, true);
        checkDatos();
    }

    // Establece el color y estilo del TextView dependiendo de si el
    // EditText correspondiente tiene el foco o no.
    private void setColorSegunFoco(TextView lbl, boolean hasFocus) {
        if (hasFocus) {
            lbl.setTextColor(getResources().getColor(R.color.accent));
            lbl.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lbl.setTextColor(getResources()
                    .getColor(R.color.primary_text));
            lbl.setTypeface(Typeface.DEFAULT);
        }
    }

    // Redondea el total.
    private void redondear() {

    }

    // Limpia los campos de datos.
    private void limpiar() {
        txtCuenta.setText(String.format(Locale.getDefault(), "%.2f",
                DEFAULT_CUENTA));
        txtPorcentaje.setText(DEFAULT_PORCENTAJE + "");
        txtCuenta.requestFocus();
    }

    // Comprueba si tenemos todos los datos necesarios para hacer las
    // operaciones y las lleva a cabo.
    private void checkDatos() {
        try {
            if (!TextUtils.isEmpty(txtCuenta.getText()
                    .toString())
                    && !TextUtils.isEmpty(txtPorcentaje.getText().toString())) {
                float cuenta = (mFormateador.parse(txtCuenta.getText()
                        .toString())).floatValue();
                float porcentaje = Float.parseFloat(txtPorcentaje.getText()
                        .toString());
                float propina = (cuenta * porcentaje) / 100;
                float total = cuenta + propina;
                txtPropina.setText(String.format(Locale.getDefault(), "%.2f",
                        propina));
                txtTotal.setText(String.format(Locale.getDefault(), "%.2f",
                        total));
                btnRedondear.setEnabled(true);
            } else {
                btnRedondear.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Muestra el texto el edittext en formato moneda.
    private void formatMoneda(EditText txt) {
        try {
            txt.setText(String.format(Locale.getDefault(), "%.2f",
                    (mFormateador.parse(txtCuenta.getText()
                            .toString())).floatValue()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
