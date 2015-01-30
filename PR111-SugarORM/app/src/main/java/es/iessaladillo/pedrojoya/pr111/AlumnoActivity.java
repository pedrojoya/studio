package es.iessaladillo.pedrojoya.pr111;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AlumnoActivity extends ActionBarActivity {

    public static final String EXTRA_TAREA = "tarea";

    @InjectView(R.id.lblNombre)
    TextView mLblNombre;
    @InjectView(R.id.txtNombre)
    EditText mTxtNombre;
    @InjectView(R.id.lblEdad)
    TextView mLblEdad;
    @InjectView(R.id.txtEdad)
    EditText mTxtEdad;

    private Alumno mAlumno;
    private MenuItem mItemGuardar;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        ButterKnife.inject(this);
        // Se obtiene la tarea con la que ha sido llamado (si la hay).
        if (getIntent() != null && getIntent().hasExtra(EXTRA_TAREA)) {
            mAlumno = (Alumno) getIntent().getParcelableExtra(EXTRA_TAREA);
            setTitle(R.string.modificar_alumno);
        }
        else {
            setTitle(R.string.agregar_alumno);
        }
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        // Si tenemos tarea que mostrar, la mostramos.
        if (mAlumno != null) {
            mTxtNombre.setText(mAlumno.getNombre());
            mTxtEdad.setText(mAlumno.getEdad() + "");
        }
        // Se cambia el color del TextView dependiendo de si el EditText
        // correspondiente tiene el foco o no.
        mTxtNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(mLblNombre, hasFocus);
            }

        });
        mTxtEdad.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(mLblEdad, hasFocus);
            }

        });
        mTxtNombre.addTextChangedListener(new TextWatcher() {

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
                // Item de menú Guardar sólo visible si están todos los datos.
                checkDatos();
                // lblUsuario visible sólo si txtUsuario tiene datos.
                checkVisibility(mTxtNombre, mLblNombre);
            }

        });
        mTxtEdad.addTextChangedListener(new TextWatcher() {

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
                // Item de menú Guardar sólo visible si están todos los datos.
                checkDatos();
                // lblClave visible sólo si tiene datos.
                checkVisibility(mTxtEdad, mLblEdad);
            }

        });
        // Comprobaciones iniciales.
        setColorSegunFoco(mLblNombre, true);
        checkVisibility(mTxtEdad, mLblEdad);
        checkVisibility(mTxtNombre, mLblNombre);
    }

    public void agregar() {
        if (!TextUtils.isEmpty(mTxtNombre.getText().toString()) && !TextUtils.isEmpty(mTxtEdad.getText().toString())) {
            Alumno alumno = new Alumno(mTxtNombre.getText().toString(), Integer.parseInt(mTxtEdad.getText().toString()));
            alumno.save();
            finish();
        } else {
            Toast.makeText(this, "Debe introducir todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizar() {
        if (!TextUtils.isEmpty(mTxtNombre.getText().toString()) && !TextUtils.isEmpty(mTxtEdad.getText().toString())) {
            Alumno alumno = new Alumno(mTxtNombre.getText().toString(), Integer.parseInt(mTxtEdad.getText().toString()));
            alumno.setId(mAlumno.getId());
            alumno.save();
            finish();
        } else {
            Toast.makeText(this, "Debe introducir todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkDatos();
    }

    // Activa o desactiva el item de menú Guardar dependiendo de si hay datos.
    private void checkDatos() {
        if (mItemGuardar != null) {
            mItemGuardar.setEnabled(!TextUtils.isEmpty(mTxtNombre.getText()
                    .toString())
                    && !TextUtils.isEmpty(mTxtEdad.getText().toString()));
        }
    }

    // TextView visible sólo si EditText tiene datos.
    private void checkVisibility(EditText txt, TextView lbl) {
        if (TextUtils.isEmpty(txt.getText().toString())) {
            lbl.setVisibility(View.INVISIBLE);
        } else {
            lbl.setVisibility(View.VISIBLE);
        }
    }

    // Establece el color y estilo del TextView dependiendo de si el
    // EditText correspondiente tiene el foco o no.
    private void setColorSegunFoco(TextView lbl, boolean hasFocus) {
        if (hasFocus) {
            lbl.setTextColor(getResources().getColor(R.color.edittext_focused));
            lbl.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lbl.setTextColor(getResources()
                    .getColor(R.color.edittext_notfocused));
            lbl.setTypeface(Typeface.DEFAULT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_alumno, menu);
        mItemGuardar = menu.findItem(R.id.mnuGuardar);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuGuardar:
                // Dependiendo de si hay tarea o no.
                if (mAlumno == null) {
                    agregar();
                } else {
                    actualizar();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}



