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
    private static final int DEFAULT_COMENSALES = 1;

    private TextView lblCuenta;
    private EditText txtCuenta;
    private TextView lblPorcentaje;
    private EditText txtPorcentaje;
    private TextView lblPropina;
    private EditText txtPropina;
    private TextView lblTotal;
    private EditText txtTotal;
    private Button btnRedondearTotal;
    private Button btnLimpiarTotal;
    private TextView lblComensales;
    private EditText txtComensales;
    private TextView lblEscote;
    private EditText txtEscote;
    private Button btnRedondearEscote;
    private Button btnLimpiarEscote;
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
                calcular();
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
                if (!hasFocus) {
                    formatEntero(txtPorcentaje);
                }
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
                calcular();
            }
        });
        lblPropina = (TextView) findViewById(R.id.lblPropina);
        txtPropina = (EditText) findViewById(R.id.txtPropina);
        lblTotal = (TextView) findViewById(R.id.lblTotal);
        txtTotal = (EditText) findViewById(R.id.txtTotal);
        btnRedondearTotal = (Button) findViewById(R.id.btnRedondearTotal);
        btnRedondearTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redondearTotal();
            }
        });
        btnLimpiarTotal = (Button) findViewById(R.id.btnLimpiarTotal);
        btnLimpiarTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarTotal();
            }
        });


        lblComensales = (TextView) findViewById(R.id.lblComensales);
        txtComensales = (EditText) findViewById(R.id.txtComensales);
        txtComensales.setSelectAllOnFocus(true);
        txtComensales.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblComensales, hasFocus);
                if (!hasFocus) {
                    formatEntero(txtComensales);
                }
            }
        });
        // Sólo se pueden hacer operaciones si tenemos todos los datos.
        txtComensales.addTextChangedListener(new TextWatcher() {
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
                    txtComensales.setText(DEFAULT_COMENSALES + "");
                } else {
                    try {
                        int comensales = mFormateador.parse(s.toString())
                                .intValue();
                        if (comensales == 0) {
                            txtComensales.setText(DEFAULT_COMENSALES + "");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                // Comprobar si hay datos para hacer operaciones.
                calcular();
            }
        });
        lblEscote = (TextView) findViewById(R.id.lblEscote);
        txtEscote = (EditText) findViewById(R.id.txtEscote);
        btnRedondearEscote = (Button) findViewById(R.id.btnRedondearEscote);
        btnRedondearEscote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redondearEscote();
            }
        });
        btnLimpiarEscote = (Button) findViewById(R.id.btnLimpiarEscote);
        btnLimpiarEscote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarEscote();
            }
        });

        // Comprobaciones iniciales.
        limpiarTotal();
        limpiarEscote();
        setColorSegunFoco(lblCuenta, true);
        calcular();
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
    private void redondearTotal() {
        try {
            float total = (mFormateador.parse(txtTotal.getText()
                    .toString())).floatValue();
            float nuevoTotal = (float) Math.floor(total);
            if (nuevoTotal != total) {
                nuevoTotal += 1;
            }
            float comensales = (mFormateador.parse(txtComensales.getText()
                    .toString())).floatValue();
            float nuevoEscote = nuevoTotal / comensales;
            txtTotal.setText(String.format(Locale.getDefault(), "%.2f",
                    nuevoTotal));
            txtEscote.setText(String.format(Locale.getDefault(), "%.2f",
                    nuevoEscote));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void redondearEscote() {
        float total = 0;
        try {
            float escote = (mFormateador.parse(txtEscote.getText()
                    .toString())).floatValue();
            float nuevoEscote = (float) Math.floor(escote);
            if (nuevoEscote != escote) {
                nuevoEscote += 1;
            }
            txtEscote.setText(String.format(Locale.getDefault(), "%.2f",
                    nuevoEscote));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Limpia los campos de datos.
    private void limpiarTotal() {
        txtCuenta.setText(String.format(Locale.getDefault(), "%.2f",
                DEFAULT_CUENTA));
        txtPorcentaje.setText(DEFAULT_PORCENTAJE + "");
        txtCuenta.requestFocus();
    }

    private void limpiarEscote() {
        txtComensales.setText(DEFAULT_COMENSALES + "");
    }

    // Comprueba si tenemos todos los datos necesarios para hacer las
    // operaciones y las lleva a cabo.
    private void calcular() {
            if (!TextUtils.isEmpty(txtCuenta.getText().toString()) &&
                    !TextUtils.isEmpty(txtPorcentaje.getText().toString()) &&
                    !TextUtils.isEmpty(txtComensales.getText().toString())) {
                float cuenta = DEFAULT_CUENTA;
                try {
                    cuenta = (mFormateador.parse(txtCuenta.getText()
                            .toString())).floatValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                float porcentaje = DEFAULT_PORCENTAJE;
                try {
                    porcentaje = (mFormateador.parse(txtPorcentaje.getText()
                            .toString())).floatValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                float comensales = DEFAULT_COMENSALES;
                try {
                    comensales = (mFormateador.parse(txtComensales.getText()
                            .toString())).floatValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                float propina = (cuenta * porcentaje) / 100;
                float total = cuenta + propina;
                float escote = total / comensales;
                txtPropina.setText(String.format(Locale.getDefault(), "%.2f",
                        propina));
                txtTotal.setText(String.format(Locale.getDefault(), "%.2f",
                        total));
                txtEscote.setText(String.format(Locale.getDefault(), "%.2f",
                        escote));
                btnRedondearTotal.setEnabled(true);
            } else {
                btnRedondearTotal.setEnabled(false);
            }
    }

    // Muestra el texto el edittext en formato moneda.
    private void formatMoneda(EditText txt) {
        try {
            txt.setText(String.format(Locale.getDefault(), "%.2f",
                    (mFormateador.parse(txt.getText()
                            .toString())).floatValue()));
        } catch (NumberFormatException e) {
            txt.setText(String.format(Locale.getDefault(), "%.2f", DEFAULT_CUENTA));
            e.printStackTrace();
        } catch (ParseException e) {
            txt.setText(String.format(Locale.getDefault(), "%.2f", DEFAULT_CUENTA));
            e.printStackTrace();
        }
    }

    // Muestra el texto el edittext en formato entero.
    private void formatEntero(EditText txt) {
        try {
            txt.setText(String.format(Locale.getDefault(), "%d",
                    (mFormateador.parse(txt.getText()
                            .toString())).intValue()));
        } catch (NumberFormatException e) {
            txt.setText(String.format(Locale.getDefault(), "%d", 0));
            e.printStackTrace();
        } catch (ParseException e) {
            txt.setText(String.format(Locale.getDefault(), "%d", 0));
            e.printStackTrace();
        }
    }

}
