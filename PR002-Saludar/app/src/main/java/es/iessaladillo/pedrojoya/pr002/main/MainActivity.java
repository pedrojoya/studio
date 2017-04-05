package es.iessaladillo.pedrojoya.pr002.main;

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

import es.iessaladillo.pedrojoya.pr002.R;
import es.iessaladillo.pedrojoya.pr002.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr002.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.pr002.utils.KeyboardUtils;

public class MainActivity extends AppCompatActivity implements OnClickListener,
        OnCheckedChangeListener, MainContract.View {

    // Vistas.
    private CheckBox chkEducado;
    private EditText txtNombre;

    MainContract.Presenter mPresenter;
    MessageManager mMessageManager;

    // Cuando se crea la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Se llama al método onCreate de la actividad padre.
        super.onCreate(savedInstanceState);
        // Se establece el layout que se usará para la actividad.
        this.setContentView(R.layout.activity_main);
        // Se crea el presentador.
        mPresenter = new MainPresenter(this);
        // Se crea el componente para mensajes.
        mMessageManager = new ToastMessageManager();
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
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
        if (v.getId() == R.id.btnSaludar) {
            btnSaludarOnClick();

        }
    }

    // Cuando se hace click sobre btnSaludar.
    private void btnSaludarOnClick() {
        // Se llama al presentador
        mPresenter.doSaludar(txtNombre.getText().toString(), chkEducado.isChecked());
    }

    // Cuando se cambia de estado del checkbox.
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Se crea el mensaje.
        chkEducado.setText(isChecked ? getString(R.string.saludar_educadamente) : getString(
                R.string.saludar_normal));
    }

    @Override
    public void saludar(String nombre) {
        // Se oculta el teclado virtual.
        KeyboardUtils.hideKeyboard(txtNombre);
        // Se muestra el mensaje.
        mMessageManager.showMessage(txtNombre, getString(R.string.buenos_dias, nombre));
    }

    @Override
    public void saludarEducado(String nombre) {
        // Se oculta el teclado virtual.
        KeyboardUtils.hideKeyboard(txtNombre);
        // Se muestra el mensaje.
        mMessageManager.showMessage(txtNombre, getString(R.string.tenga_usted, nombre));
    }

}
