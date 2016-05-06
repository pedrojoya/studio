package es.iessaladillo.pedrojoya.pr133;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final float DEFAULT_CUENTA = 0.00f;
    private static final int DEFAULT_PORCENTAJE = 2;
    private static final int DEFAULT_COMENSALES = 1;

    private TextView lblCuenta;
    private EditText txtCuenta;
    private TextView lblPorcentaje;
    private EditText txtPorcentaje;
    private EditText txtPropina;
    private EditText txtTotal;
    private Button btnRedondearTotal;
    private TextView lblComensales;
    private EditText txtComensales;
    private EditText txtEscote;

    private NumberFormat mFormateador;
    private String mSimboloDecimal;
    private String mSimboloMoneda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtiene el símbolo decimal y el símbolo de mondeda de la configuración
        // actual del dispositivo.
        DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
        mSimboloDecimal = decimalSymbols.getDecimalSeparator() + "";
        mSimboloMoneda = decimalSymbols.getCurrencySymbol();
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
        if (txtCuenta !=  null) {
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
                    // Si ha quedado vacío se pone el valor por defecto.
                    if (TextUtils.isEmpty(s.toString())) {
                        txtCuenta.setText(String.format(Locale.getDefault(), "%.2f",
                                DEFAULT_CUENTA));
                    }
                    // Se sustituye el '.' por el símbolo decimal (la ',').
                    if (s.toString().contains(".") && !(".".equals(mSimboloDecimal))) {
                        int position = txtCuenta.getSelectionStart();
                        String reemplazo = s.toString().replace(".", mSimboloDecimal);
                        txtCuenta.setText(reemplazo);
                        txtCuenta.setSelection(position);
                    }
                    // Se realizan los cálculos
                    calcular();
                }
            });
        }
        TextView lblMonedaCuenta = (TextView) findViewById(R.id.lblMonedaCuenta);
        if (lblMonedaCuenta != null) {
            lblMonedaCuenta.setText(mSimboloMoneda);
        }
        lblPorcentaje = (TextView) findViewById(R.id.lblPorcentaje);
        txtPorcentaje = (EditText) findViewById(R.id.txtPorcentaje);
        if (txtPorcentaje != null) {
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
                    // Si ha quedado vacío se pone el valor por defecto.
                    if (TextUtils.isEmpty(s.toString())) {
                        txtPorcentaje.setText(String.valueOf(DEFAULT_PORCENTAJE));
                    }
                    // Se realizan los cálculos.
                    calcular();
                }
            });
        }
        txtPropina = (EditText) findViewById(R.id.txtPropina);
        TextView lblMonedaPropina = (TextView) findViewById(R.id.lblMonedaPropina);
        if (lblMonedaPropina != null) {
            lblMonedaPropina.setText(mSimboloMoneda);
        }
        txtTotal = (EditText) findViewById(R.id.txtTotal);
        TextView lblMonedaTotal = (TextView) findViewById(R.id.lblMonedaTotal);
        if (lblMonedaTotal != null) {
            lblMonedaTotal.setText(mSimboloMoneda);
        }
        btnRedondearTotal = (Button) findViewById(R.id.btnRedondearTotal);
        if (btnRedondearTotal != null) {
            btnRedondearTotal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    redondearTotal();
                }
            });
        }
        Button btnLimpiarTotal = (Button) findViewById(R.id.btnLimpiarTotal);
        if (btnLimpiarTotal != null) {
            btnLimpiarTotal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    limpiarTotal();
                }
            });
        }
        // Vistas de las segunda tarjeta.
        lblComensales = (TextView) findViewById(R.id.lblComensales);
        txtComensales = (EditText) findViewById(R.id.txtComensales);
        if (txtComensales != null) {
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
                    // Si ha quedado vacío se pone el valor por defecto.
                    if (TextUtils.isEmpty(s.toString())) {
                        txtComensales.setText(String.valueOf(DEFAULT_COMENSALES));
                    } else {
                        try {
                            // Se formatea.
                            int comensales = mFormateador.parse(s.toString())
                                    .intValue();
                            // El número de comensales no puede ser 0.
                            if (comensales == 0) {
                                txtComensales.setText(String.valueOf(DEFAULT_COMENSALES));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    // Se realizan los cálculos.
                    calcular();
                }
            });
        }
        txtEscote = (EditText) findViewById(R.id.txtEscote);
        TextView lblMonedaEscote = (TextView) findViewById(R.id.lblMonedaEscote);
        if (lblMonedaEscote != null) {
            lblMonedaEscote.setText(mSimboloMoneda);
        }
        Button btnRedondearEscote = (Button) findViewById(R.id.btnRedondearEscote);
        if (btnRedondearEscote != null) {
            btnRedondearEscote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    redondearEscote();
                }
            });
        }
        Button btnLimpiarEscote = (Button) findViewById(R.id.btnLimpiarEscote);
        if (btnLimpiarEscote != null) {
            btnLimpiarEscote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    limpiarEscote();
                }
            });
        }
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
            lbl.setTextColor(ContextCompat.getColor(this, R.color.accent));
            lbl.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lbl.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
            lbl.setTypeface(Typeface.DEFAULT);
        }
    }

    // Redondea el total y realiza el cálculo del escote por comensal.
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

    // Redondeo el escote por comensal.
    private void redondearEscote() {
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

    // Limpia los campos de datos de la primera tarjeta.
    private void limpiarTotal() {
        txtCuenta.setText(String.format(Locale.getDefault(), "%.2f",
                DEFAULT_CUENTA));
        txtPorcentaje.setText(String.valueOf(DEFAULT_PORCENTAJE));
        txtCuenta.requestFocus();
    }

    // Limpia los campos de datos de las segunda tarjeta.
    private void limpiarEscote() {
        txtComensales.setText(String.valueOf(DEFAULT_COMENSALES));
    }

    // Comprueba si tenemos todos los datos necesarios para hacer loss
    // cálculos y los lleva a cabo.
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

    // Muestra el texto el EditText recibido en formato moneda.
    private void formatMoneda(EditText txt) {
        try {
            txt.setText(String.format(Locale.getDefault(), "%.2f",
                    (mFormateador.parse(txt.getText()
                            .toString())).floatValue()));
        } catch (NumberFormatException | ParseException e) {
            txt.setText(String.format(Locale.getDefault(), "%.2f", DEFAULT_CUENTA));
            e.printStackTrace();
        }
    }

    // Muestra el texto el EditText en formato entero.
    private void formatEntero(EditText txt) {
        try {
            txt.setText(String.format(Locale.getDefault(), "%d",
                    (mFormateador.parse(txt.getText()
                            .toString())).intValue()));
        } catch (NumberFormatException | ParseException e) {
            txt.setText(String.format(Locale.getDefault(), "%d", 0));
            e.printStackTrace();
        }
    }

}