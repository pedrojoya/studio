package es.iessaladillo.pedrojoya.pr005.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr005.R;
import es.iessaladillo.pedrojoya.pr005.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr005.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.pr005.editalumno.EditAlumnoActivity;
import es.iessaladillo.pedrojoya.pr005.model.Alumno;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final int RC_ALUMNO = 1;

    private TextView lblDatos;
    private Button btnSolicitar;

    private MainContract.Presenter mPresenter;
    private MessageManager mMessageManager;
    private String mNombre;
    private int mEdad = Alumno.DEFAULT_EDAD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        mMessageManager = new ToastMessageManager();
        initVistas();
    }

    private void initVistas() {
        btnSolicitar = (Button) this.findViewById(R.id.btnSolicitar);
        lblDatos = (TextView) this.findViewById(R.id.lblDatos);
        btnSolicitar.setOnClickListener(v -> mPresenter.doSolicitarDatos(mNombre, mEdad));
    }

    // Cuando llega una respuesta.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si el resultado es satisfactorio y el código de petición el adecuado.
        if (requestCode == RC_ALUMNO) {
            mPresenter.onSolicitarDatosResult(resultCode, data);
        }
    }

    @Override
    public void navigateToSolicitar(String nombreActual, int edadActual) {
        EditAlumnoActivity.startForResult(this, RC_ALUMNO, nombreActual, edadActual);
    }

    @Override
    public void showDatos(String nombre, int edad) {
        mNombre = nombre;
        mEdad = edad;
        lblDatos.setText(getString(R.string.datos, mNombre, mEdad));
    }

    @Override
    public void showErrorAlSolicitarDatos() {
        mMessageManager.showMessage(btnSolicitar, getString(R.string.error_al_solicitar_datos));
    }

}
