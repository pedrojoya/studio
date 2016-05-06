package es.iessaladillo.pedrojoya.pr177;

import android.databinding.DataBindingUtil;
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

import es.iessaladillo.pedrojoya.pr177.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private static final String STATE_SALUDO = "saludo";

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

    private ActivityMainVM mViewModel;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
            mViewModel = new ActivityMainVM(DEFAULT_CUENTA, DEFAULT_PORCENTAJE, DEFAULT_COMENSALES);
        } else {
            mViewModel = savedInstanceState.getParcelable(STATE_SALUDO);
        }
        mBinding.setViewModel(mViewModel);
        // Se obtiene el símbolo decimal y el símbolo de mondeda de la configuración
        // actual del dispositivo.
/*
        DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
        mSimboloDecimal = decimalSymbols.getDecimalSeparator() + "";
        mSimboloMoneda = decimalSymbols.getCurrencySymbol();
        mFormateador = NumberFormat.getInstance(Locale.getDefault());
*/
        initVistas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.txtCuenta.requestFocus();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        //mBinding.txtCuenta.setSelectAllOnFocus(true);
/*
            txtCuenta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                // Al cambiar el foco.
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    setColorSegunFoco(lblCuenta, hasFocus);
                    if (!hasFocus) {
                        formatMoneda(txtCuenta);
                    }
                }
*/
/*
        mBinding.txtCuenta.addTextChangedListener(new TextWatcher() {
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
*/
/*
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
*//*

                    // Se realizan los cálculos
                    mViewModel.calcular();
                }
            });
*/
/*
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
*/
/*
            mBinding.txtPorcentaje.addTextChangedListener(new TextWatcher() {
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
                        mBinding.txtPorcentaje.setText(String.valueOf(DEFAULT_PORCENTAJE));
                    }
                    // Se realizan los cálculos.
                    mViewModel.calcular();
                }
            });
*/


            mBinding.btnRedondearTotal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    redondearTotal();
                }
            });
            mBinding.btnLimpiarTotal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    limpiarTotal();
                }
            });
        // Vistas de las segunda tarjeta.
            //mBinding.txtComensales.setSelectAllOnFocus(true);
/*
            mBinding.txtComensales.addTextChangedListener(new TextWatcher() {
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
                    }
                    // Se realizan los cálculos.
                    mViewModel.calcular();
                }
            });
*/
            mBinding.btnLimpiarEscote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    limpiarEscote();
                }
            });
        // Comprobaciones iniciales.
        limpiarTotal();
        limpiarEscote();
//        setColorSegunFoco(mBinding.lblCuenta, true);
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

    // Limpia los campos de datos de la primera tarjeta.
    private void limpiarTotal() {

    }

    // Limpia los campos de datos de las segunda tarjeta.
    private void limpiarEscote() {
    }

}