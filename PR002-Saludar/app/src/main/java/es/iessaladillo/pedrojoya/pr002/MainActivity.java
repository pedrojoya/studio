package es.iessaladillo.pedrojoya.pr002;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener,
        OnCheckedChangeListener {

    // Vistas.
    private CheckBox chkEducado;
    private EditText txtNombre;

    // Cuando se crea la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Se llama al método onCreate de la actividad padre.
        super.onCreate(savedInstanceState);
        // Se establece el layout que se usará para la actividad.
        this.setContentView(R.layout.activity_main);
        // Se obtiene la referencia y se inicializan las vistas.
        getVistas();
    }

    // Obtiene la referencia e inicializa las vistas.
    private void getVistas() {
        // Se obtiene la referencia a las vistas.
        chkEducado = (CheckBox) this.findViewById(R.id.chkEducado);
        Button btnSaludar = (Button) this.findViewById(R.id.btnSaludar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        // El checkbox apracerá inicialmente chequeado.
        chkEducado.setChecked(true);
        // La actividad actuará con listener cuando se haga click sobre el
        // botón.
        if (btnSaludar != null) {
            btnSaludar.setOnClickListener(this);
        }
        // La actividad actuará como listener cuando cambie el estado del
        // checkbox.
        chkEducado.setOnCheckedChangeListener(this);
    }

    // Cuando se hace click sobre algún botón.
    @Override
    public void onClick(View v) {
        // Dependiendo del botón pulsado.
        int id = v.getId();
        if (id == R.id.btnSaludar) {
            btnSaludarOnClick();

        }
    }

    // Cuando se hace click sobre btnSaludar.
    private void btnSaludarOnClick() {
        // Se crea el mensaje a mostrar.
        String mensaje = getString(R.string.buenos_dias);
        if (chkEducado.isChecked()) {
            mensaje = mensaje + " " + getString(R.string.tenga_usted);
        }
        mensaje += " " + txtNombre.getText();
        // Se oculta el teclado virtual.
        hideKeyboard(chkEducado);
        // Se muestra el mensaje en un Toast.
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    // Oculta el teclado virtual. Recibe una vista de referencia
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // Cuando se cambia de estado del checkbox.
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Se crea el mensaje.
        chkEducado.setText(isChecked ? getString(R.string.saludar_educadamente) : getString(
                R.string.saludar_normal));
    }

}
