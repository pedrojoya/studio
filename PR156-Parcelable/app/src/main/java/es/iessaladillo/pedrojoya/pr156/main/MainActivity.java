package es.iessaladillo.pedrojoya.pr156.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr156.R;
import es.iessaladillo.pedrojoya.pr156.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr156.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.pr156.editalumno.EditAlumnoActivity;
import es.iessaladillo.pedrojoya.pr156.model.Alumno;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final int RC_ALUMNO = 1;
    private static final String STATE_ALUMNO = "STATE_ALUMNO";

    private TextView lblDatos;
    private Button btnSolicitar;

    private MainContract.Presenter mPresenter;
    private MessageManager mMessageManager;
    private Alumno mAlumno;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        mMessageManager = new ToastMessageManager();
        restoreInstance(savedInstanceState);
        initVistas();
    }

    private void restoreInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mAlumno = savedInstanceState.getParcelable(STATE_ALUMNO);
        }
        if (mAlumno == null) {
            mAlumno = new Alumno("", Alumno.DEFAULT_EDAD);
        }
    }

    private void initVistas() {
        btnSolicitar = (Button) this.findViewById(R.id.btnSolicitar);
        lblDatos = (TextView) this.findViewById(R.id.lblDatos);
        if (TextUtils.isEmpty(mAlumno.getNombre())) {
            lblDatos.setText(R.string.no_disponibles);
        }
        btnSolicitar.setOnClickListener(v -> mPresenter.doSolicitarDatos(mAlumno));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_ALUMNO, mAlumno);
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
    public void navigateToSolicitar(@NonNull Alumno alumnoActual) {
        EditAlumnoActivity.startForResult(this, RC_ALUMNO, alumnoActual);
    }

    @Override
    public void showDatos(@NonNull Alumno alumno) {
        mAlumno = alumno;
        lblDatos.setText(getString(R.string.datos, alumno.getNombre(), alumno.getEdad()));
    }

    @Override
    public void showMensajeExito() {
        mMessageManager.showMessage(btnSolicitar, getString(R.string.exito_al_solicitar_datos));
    }

}
