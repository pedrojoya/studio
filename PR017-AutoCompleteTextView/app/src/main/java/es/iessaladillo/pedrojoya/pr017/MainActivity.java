package es.iessaladillo.pedrojoya.pr017;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    // Constantes.
    private static final String URL_BASE = "http://www.wordreference.com/es/translation.asp?tranword=";

    // Vistas.
    private AutoCompleteTextView txtConcepto;
    private Button btnTraducir;
    private TextView lblConcepto;
    private WebView wvWeb;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas de la actividad.
    private void initVistas() {
        wvWeb = (WebView) findViewById(R.id.wvWeb);
        wvWeb.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                wvWeb.setVisibility(View.VISIBLE);
            }

        });
        lblConcepto = (TextView) findViewById(R.id.lblConcepto);
        txtConcepto = (AutoCompleteTextView) findViewById(R.id.txtConcepto);
        txtConcepto.setAdapter(new ConceptosAdapter(this, getDatos()));
        btnTraducir = (Button) findViewById(R.id.btnTraducir);
        btnTraducir.setOnClickListener(this);
        // Se cambia el color del TextView dependiendo de si el EditText
        // correspondiente tiene el foco o no.
        txtConcepto.setOnFocusChangeListener(new OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblConcepto, hasFocus);
            }

        });
        // btnTraducir s�lo accesible si hay datos en txtConcepto.
        txtConcepto.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            // Despu�s de haber cambiado el texto.
            @Override
            public void afterTextChanged(Editable s) {
                // btnAceptar disponible s�lo si hay datos.
                checkDatos();
                // lblUsuario visible s�lo si txtUsuario tiene datos.
                checkVisibility(txtConcepto, lblConcepto);
                if (TextUtils.isEmpty(s)) {
                    wvWeb.setVisibility(View.INVISIBLE);
                }
            }

        });
        // Comprobaciones iniciales.
        setColorSegunFoco(lblConcepto, true);
        checkDatos();
        checkVisibility(txtConcepto, lblConcepto);
        checkVisibility(txtConcepto, wvWeb);

    }

    // Activa o desactiva el bot�n de Aceptar dependiendo de si hay datos.
    private void checkDatos() {
        btnTraducir.setEnabled(!TextUtils.isEmpty(txtConcepto.getText()
                .toString()));
    }

    // TextView visible s�lo si EditText tiene datos.
    private void checkVisibility(EditText txt, View v) {
        if (TextUtils.isEmpty(txt.getText().toString())) {
            v.setVisibility(View.INVISIBLE);
        } else {
            v.setVisibility(View.VISIBLE);
        }
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

    // Construye y retorna el ArrayList de conceptos.
    private ArrayList<Concepto> getDatos() {
        ArrayList<Concepto> conceptos = new ArrayList<Concepto>();
        conceptos.add(new Concepto(R.drawable.animal, "Animal", "Animal"));
        conceptos.add(new Concepto(R.drawable.bridge, "Bridge", "Puente"));
        conceptos.add(new Concepto(R.drawable.flag, "Flag", "Bandera"));
        conceptos.add(new Concepto(R.drawable.food, "Food", "Comida"));
        conceptos.add(new Concepto(R.drawable.fruit, "Fruit", "Fruta"));
        conceptos.add(new Concepto(R.drawable.glass, "Glass", "Vaso"));
        conceptos.add(new Concepto(R.drawable.plant, "Plant", "Planta"));
        conceptos.add(new Concepto(R.drawable.science, "Science", "Ciencia"));
        conceptos.add(new Concepto(R.drawable.sea, "Sea", "Mar"));
        return conceptos;
    }

    // Al hacer click sobre btnTraducir.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnTraducir:
            wvWeb.loadUrl(URL_BASE + txtConcepto.getText().toString());
            break;
        }
    }

}
