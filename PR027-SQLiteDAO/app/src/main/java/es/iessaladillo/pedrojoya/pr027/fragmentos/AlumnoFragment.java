package es.iessaladillo.pedrojoya.pr027.fragmentos;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.bd.DAO;
import es.iessaladillo.pedrojoya.pr027.modelos.Alumno;
import es.iessaladillo.pedrojoya.pr027.utils.ClickToSelectEditText;

public class AlumnoFragment extends Fragment {

    // Constantes.
    private static final String EXTRA_ID = "id";
    private static final String STATE_TILNOMBRE = "state_tilNombre";
    private static final String STATE_TILCURSO = "state_tilCurso";
    private static final String STATE_TILTELEFONO = "state_tilTelefono";

    private EditText txtNombre;
    private EditText txtTelefono;
    private EditText txtDireccion;
    private ClickToSelectEditText<String> spnCurso;
    private Alumno mAlumno;
    private TextInputLayout tilNombre;
    private TextInputLayout tilCurso;
    private TextInputLayout tilTelefono;
    private TextInputLayout tilDireccion;
    private ImageView imgNombre;
    private ImageView imgCurso;
    private ImageView imgTelefono;
    private ImageView imgDireccion;
    private Random mAleatorio;

    // Retorna una nueva instancia del fragmento (para agregar)
    static public AlumnoFragment newInstance() {
        return new AlumnoFragment();
    }

    // Retorna una nueva instancia del fragmento. Recibe el id del mAlumno
    // (para actualizar).
    static public AlumnoFragment newInstance(long idAlumno) {
        AlumnoFragment frg = new AlumnoFragment();
        Bundle argumentos = new Bundle();
        argumentos.putLong(EXTRA_ID, idAlumno);
        frg.setArguments(argumentos);
        return frg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alumno, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAleatorio = new Random();
        initViews(savedInstanceState);
    }

    // Obtiene la referencia a las vistas del layout.
    private void initViews(Bundle savedInstanceState) {
        if (getView() != null) {
            tilNombre = (TextInputLayout) getView().findViewById(R.id.tilNombre);
            tilTelefono = (TextInputLayout) getView().findViewById(R.id.tilTelefono);
            tilCurso = (TextInputLayout) getView().findViewById(R.id.tilCurso);
            tilDireccion = (TextInputLayout) getView().findViewById(R.id.tilDireccion);
            if (savedInstanceState != null) {
                if (savedInstanceState.getBoolean(STATE_TILNOMBRE)) {
                    tilNombre.setErrorEnabled(true);
                    tilNombre.setError(getString(R.string.campo_obligatorio));
                }
                if (savedInstanceState.getBoolean(STATE_TILCURSO)) {
                    tilCurso.setErrorEnabled(true);
                    tilCurso.setError(getString(R.string.campo_obligatorio));
                }
                if (savedInstanceState.getBoolean(STATE_TILTELEFONO)) {
                    tilTelefono.setErrorEnabled(true);
                    tilTelefono.setError(getString(R.string.campo_obligatorio));
                }
                tilDireccion.setErrorEnabled(false);
            }
            imgNombre = (ImageView) getView().findViewById(R.id.imgNombre);
            txtNombre = (EditText) getView().findViewById(R.id.txtNombre);
            txtNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_face);
                    drawable = DrawableCompat.wrap(drawable);
                    if (!hasFocus) {
                        if (tilNombre.isErrorEnabled() && checkRequiredEditText(txtNombre, tilNombre)) {
                            tilNombre.setError("");
                            tilNombre.setErrorEnabled(false);
                        }
                        DrawableCompat.setTintList(drawable, null);
                    } else {
                        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
                        DrawableCompat.setTint(drawable, getResources().getColor(R.color.accent));
                    }
                    imgNombre.setImageDrawable(drawable);
                }
            });
            imgCurso = (ImageView) getView().findViewById(R.id.imgCurso);
            spnCurso = (ClickToSelectEditText<String>) getView().findViewById(R.id.txtCurso);
            cargarCursos();
            spnCurso.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_create);
                    drawable = DrawableCompat.wrap(drawable);
                    if (!hasFocus) {
                        if (tilCurso.isErrorEnabled() && checkRequiredEditText(spnCurso, tilCurso)) {
                            tilCurso.setError("");
                            tilCurso.setErrorEnabled(false);
                        }
                        DrawableCompat.setTintList(drawable, null);
                    } else {
                        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
                        DrawableCompat.setTint(drawable, getResources().getColor(R.color.accent));
                        spnCurso.showDialog(v);
                    }
                    imgCurso.setImageDrawable(drawable);
                }
            });
            imgTelefono = (ImageView) getView().findViewById(R.id.imgTelefono);
            txtTelefono = (EditText) getView().findViewById(R.id.txtTelefono);
            txtTelefono.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_phone);
                    drawable = DrawableCompat.wrap(drawable);
                    if (!hasFocus) {
                        if (tilTelefono.isErrorEnabled() && checkRequiredEditText(txtTelefono, tilTelefono)) {
                            tilTelefono.setError("");
                            tilTelefono.setErrorEnabled(false);
                        }
                        DrawableCompat.setTintList(drawable, null);
                    } else {
                        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
                        DrawableCompat.setTint(drawable, getResources().getColor(R.color.accent));
                    }
                    imgTelefono.setImageDrawable(drawable);
                }
            });
            imgDireccion = (ImageView) getView().findViewById(R.id.imgDireccion);
            txtDireccion = (EditText) getView().findViewById(R.id.txtDireccion);
            txtDireccion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!tilDireccion.isErrorEnabled()) {
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_home);
                        drawable = DrawableCompat.wrap(drawable);
                        if (!hasFocus) {
                            DrawableCompat.setTintList(drawable, null);
                        } else {
                            DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
                            DrawableCompat.setTint(drawable, getResources().getColor(R.color.accent));
                        }
                        imgDireccion.setImageDrawable(drawable);
                    }
                }
            });
            txtDireccion.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        guardarAlumno();
                        return true;
                    }
                    return false;

                }
            });
        }
        // Establecemos el modo de funcionamiento dependiendo de si nos han
        // pasado el id del alumno o no.
        if (getArguments() == null || getArguments().getLong(EXTRA_ID) == 0) {
            setModoAgregar();
        } else {
            setModoEditar();
        }

    }

    // Carga los cursos en el "spinner".
    private void cargarCursos() {
        ArrayAdapter<CharSequence> adaptadorCursos = ArrayAdapter.createFromResource(getActivity(),
                R.array.cursos, android.R.layout.simple_list_item_1);
        spnCurso.setAdapter(adaptadorCursos);
        spnCurso.setOnItemSelectedListener(new ClickToSelectEditText.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelectedListener(String item, int selectedIndex) {
                spnCurso.setText(item);
            }
        });
    }

    // Realiza las operaciones iniciales necesarias en el modo Agregar.
    private void setModoAgregar() {
        // Se crea un nuevo objeto Alumno vacío.
        mAlumno = new Alumno();
        getActivity().setTitle(R.string.agregar_alumno);
    }

    // Realiza las operaciones iniciales necesarias en el modo Editar.
    private void setModoEditar() {
        // Se cargan los datos del alumno a partir del id recibido.
        cargarAlumno(getArguments().getLong(EXTRA_ID));
        // Se escriben los datos del mAlumno en las vistas correspondientes.
        alumnoToVistas();
        getActivity().setTitle(R.string.editar_alumno);
    }

    // Carga los datos del mAlumno provenientes de la BD en el objeto Alumno.
    private void cargarAlumno(long id) {
        // Se consulta en la BD los datos del mAlumno a través del objeto DAO.
        mAlumno = (new DAO(getActivity())).queryAlumno(id);
        // Si no se ha encontrado el mAlumno, se informa y se pasa al modo
        // Agregar.
        if (mAlumno == null) {
            Toast.makeText(getActivity(), R.string.alumno_no_encontrado,
                    Toast.LENGTH_LONG).show();
            setModoAgregar();
        }
    }

    // Guarda el alumno en pantalla en la base de datos.
    public void guardarAlumno() {
        // Dependiendo del modo se inserta o actualiza el Alumno (siempre y
        // cuando se hayan introducido los datos obligatorios).
        if (isValidForm()) {
            // Se llena el objeto Alumno con los datos de las vistas.
            vistasToAlumno();
            if (getArguments() == null || getArguments().getLong(EXTRA_ID) == 0) {
                agregarAlumno();
            } else {
                actualizarAlumno();
            }
        }
    }

    private boolean isValidForm() {
        boolean valido = true;
        if (!checkRequiredEditText(txtNombre, tilNombre)) {
            valido = false;
        }
        if (!checkRequiredEditText(spnCurso, tilCurso)) {
            valido = false;
        }
        if (!checkRequiredEditText(txtTelefono, tilTelefono)) {
            valido = false;
        }
        return valido;
    }

    private boolean checkRequiredEditText(EditText txt, TextInputLayout til) {
        if (TextUtils.isEmpty(txt.getText().toString())) {
            til.setErrorEnabled(true);
            til.setError(getString(R.string.campo_obligatorio));
            return false;
        } else {
            til.setErrorEnabled(false);
            til.setError("");
            return true;
        }
    }

    // Agrega el alumno a la base de datos.
    private void agregarAlumno() {
        // Se realiza el insert a través del objeto DAO.
        long id = (new DAO(getActivity())).createAlumno(mAlumno);
        // Se informa de si ha ido bien.
        if (id >= 0) {
            mAlumno.setId(id);
            Toast.makeText(getActivity(),
                    getString(R.string.insercion_correcta), Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getActivity(),
                    getString(R.string.insercion_incorrecta),
                    Toast.LENGTH_SHORT).show();
        }
        // Se resetean las vistas para poder agregar otro alumno (seguimos en
        // modo Agregar).
        resetVistas();
    }

    // Actualiza el alumno en la base de datos.
    private void actualizarAlumno() {
        // Realiza el update en la BD a través del objeto DAO y se informa de si ha ido bien.
        if ((new DAO(getActivity())).updateAlumno(mAlumno)) {
            Toast.makeText(getActivity(),
                    getString(R.string.actualizacion_correcta),
                    Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(),
                    getString(R.string.actualizacion_incorrecta),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Hace reset sobre el contenido de las vistas.
    private void resetVistas() {
        txtNombre.setText("");
        txtTelefono.setText("");
        spnCurso.setText("");
        txtDireccion.setText("");
        txtNombre.requestFocus();
    }

    // Muestra los datos del objeto Alumno en las vistas.
    private void alumnoToVistas() {
        txtNombre.setText(mAlumno.getNombre());
        txtTelefono.setText(mAlumno.getTelefono());
        txtDireccion.setText(mAlumno.getDireccion());
        spnCurso.setText(mAlumno.getCurso());
    }

    // Llena el objeto Alumno con los datos de las vistas.
    private void vistasToAlumno() {
        mAlumno.setNombre(txtNombre.getText().toString());
        mAlumno.setTelefono(txtTelefono.getText().toString());
        mAlumno.setDireccion(txtDireccion.getText().toString());
        mAlumno.setCurso(spnCurso.getText().toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_TILNOMBRE, tilNombre.isErrorEnabled());
        outState.putBoolean(STATE_TILCURSO, tilCurso.isErrorEnabled());
        outState.putBoolean(STATE_TILTELEFONO, tilTelefono.isErrorEnabled());
    }

    // Retorna una url aleatoria correspondiente a una imagen para el avatar.
    private String getRandomAvatarUrl() {
        final String BASE_URL = "http://lorempixel.com/100/100/";
        final String[] tipos = {"abstract", "animals", "business", "cats", "city", "food",
                "night", "life", "fashion", "people", "nature", "sports", "technics", "transport"};
        return BASE_URL + tipos[mAleatorio.nextInt(tipos.length)] + "/" +
                (mAleatorio.nextInt(10) + 1) + "/";
    }

}
