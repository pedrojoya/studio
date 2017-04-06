package es.iessaladillo.pedrojoya.pr002.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import es.iessaladillo.pedrojoya.pr002.R;
import es.iessaladillo.pedrojoya.pr002.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr002.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.pr002.utils.KeyboardUtils;

public class MainActivity extends AppCompatActivity implements OnClickListener,
        OnCheckedChangeListener, MainContract.View {

    // Vistas.
    private CheckBox chkEducado;
    private EditText txtNombre;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnSaludar;

    private MainContract.Presenter mPresenter;
    private MessageManager mMessageManager;

    // Cuando se crea la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Se llama al método onCreate de la actividad padre.
        super.onCreate(savedInstanceState);
        // Se establece el layout que se usará para la actividad, que es "inflado" obteniendo la
        // vista que presenta la IU de la actividad.
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
        btnSaludar = (Button) this.findViewById(R.id.btnSaludar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        // La actividad actuará con listener cuando se haga click sobre el botón.
        btnSaludar.setOnClickListener(this);
        // La actividad actuará como listener cuando cambie el estado del checkbox.
        chkEducado.setOnCheckedChangeListener(this);
    }

    // Cuando se hace click sobre btnSaludar.
    @Override
    public void onClick(View v) {
        // Se llama al método correspondiente del presentador.
        mPresenter.doSaludar(txtNombre.getText().toString(), chkEducado.isChecked());
    }

    // Cuando se cambia de estado del checkbox.
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mPresenter.doCambiarEstadoEducado(isChecked);
    }

    // Muestra el saludo personalizado.
    @Override
    public void saludar(String nombre) {
        // Se oculta el teclado virtual.
        KeyboardUtils.hideKeyboard(txtNombre);
        // Se muestra el mensaje.
        mMessageManager.showMessage(txtNombre, getString(R.string.buenos_dias, nombre));
    }

    // Muestra el saludo educado personalizado.
    @Override
    public void saludarEducado(String nombre) {
        // Se oculta el teclado virtual.
        KeyboardUtils.hideKeyboard(txtNombre);
        // Se muestra el mensaje.
        mMessageManager.showMessage(txtNombre, getString(R.string.tenga_usted, nombre));
    }

    // Cambia el texto al modo educado.
    @Override
    public void mostrarTextoModoEducado() {
        chkEducado.setText(getString(R.string.saludar_educadamente));
    }

    // Cambia el texto al modo no educado.
    @Override
    public void mostrarTextoModoNoEducado() {
        // Se crea el mensaje.
        chkEducado.setText(getString(R.string.saludar_normal));
    }

}
