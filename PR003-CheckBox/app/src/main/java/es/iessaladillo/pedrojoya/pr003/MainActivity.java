package es.iessaladillo.pedrojoya.pr003;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Vistas.
    private CheckBox chkEducado;
    private EditText txtNombre;

    // Cuando se crea la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene la referencia e inicializa las vistas.
    private void initVistas() {
        // Se obtiene la referencia a las vistas.
        chkEducado = (CheckBox) findViewById(R.id.chkEducado);
        Button btnSaludar = (Button) findViewById(R.id.btnSaludar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        // Se inicializan las vistas.
        chkEducado.setChecked(true);
        btnSaludar.setOnClickListener(new OnClickListener() {

            // Cuando se hace click sobre btnSaludar.
            public void onClick(View b) {
                // Se crea el mensaje a mostrar.
                String mensaje = getString(R.string.buenos_dias);
                if (chkEducado.isChecked()) {
                    mensaje = mensaje + " " + getString(R.string.tenga_usted);
                }
                mensaje += " " + txtNombre.getText();
                // Se muestra el mensaje en un Toast.
                Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_LONG)
                        .show();
            }

        });
        chkEducado.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            // Cuando se cambia de estado del checkbox.
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // Se crea el mensaje.
                String mensaje = getString(R.string.modo_educado) + " ";
                if (isChecked) {
                    mensaje += getString(R.string.on);
                } else {
                    mensaje += getString(R.string.off);
                }
                // Se muestra en un Toast.
                Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_LONG)
                        .show();
            }

        });

    }

}