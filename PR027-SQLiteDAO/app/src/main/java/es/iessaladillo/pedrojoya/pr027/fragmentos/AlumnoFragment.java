package es.iessaladillo.pedrojoya.pr027.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.bd.DAO;
import es.iessaladillo.pedrojoya.pr027.modelos.Alumno;

public class AlumnoFragment extends Fragment {

    public static final String EXTRA_MODO = "mModo";
    public static final String EXTRA_ID = "id";
    public static final String MODO_AGREGAR = "AGREGAR";
    public static final String MODO_EDITAR = "EDITAR";

    private EditText txtNombre;
    private EditText txtTelefono;
    private EditText txtDireccion;
    private Spinner spnCurso;

    private String mModo;
    private Alumno mAlumno;
    private ArrayAdapter<CharSequence> mAdaptadorCursos;
    private Random mAleatorio;

    static public AlumnoFragment newInstance(String modo, long id) {
        AlumnoFragment frg = new AlumnoFragment();
        Bundle argumentos = new Bundle();
        argumentos.putString(EXTRA_MODO, modo);
        argumentos.putLong(EXTRA_ID, id);
        frg.setArguments(argumentos);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alumno, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Se obtienen las referencias a las vistas.
        initVistas(getView());
        // Se carga el spinner de cursos.
        cargarCursos();
        // Se establece el mModo en el que debe comportarse la actividad
        // dependiendo del argumento recibido.
        // Dependiendo de la acción.
        String modo = getArguments().getString(EXTRA_MODO);
        if (modo.equals(MODO_EDITAR)) {
            setModoEditar();
        } else {
            setModoAgregar();
        }
        mAleatorio = new Random();
    }

    // Carga los cursos en el spinner.
    private void cargarCursos() {
        // Se crea un ArrayAdapter para el spinner, que use layouts estándar
        // tanto para cuando no está desplegado como para cuando sí lo esté. La
        // fuente de datos para el adaptador es un array de constantes de
        // cadena.
        mAdaptadorCursos = ArrayAdapter.createFromResource(getActivity(),
                R.array.cursos, android.R.layout.simple_spinner_item);
        mAdaptadorCursos
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCurso.setAdapter(mAdaptadorCursos);
    }

    // Realiza las operaciones iniciales necesarias en el mModo Editar.
    private void setModoEditar() {
        // Se establece el mModo Editar.
        mModo = MODO_EDITAR;
        // Se cargan los datos del mAlumno a partir del id recibido.
        cargarAlumno(getArguments().getLong(EXTRA_ID));
        // Se escriben los datos del mAlumno en las vistas correspondientes.
        alumnoToVistas();
        // Se actuliza el título de la actividad en relación al mModo.
        getActivity().setTitle(R.string.editar_alumno);
    }

    // Realiza las operaciones iniciales necesarias en el mModo Agregar.
    private void setModoAgregar() {
        // Se establece el mModo Agregar.
        mModo = MODO_AGREGAR;
        // Se crea un nuevo objeto Alumno vacío.
        mAlumno = new Alumno();
        // Se actualiza el título de la actividad en relación al mModo.
        getActivity().setTitle(R.string.agregar_alumno);
    }

    // Carga los datos del mAlumno provenientes de la BD en el objeto Alumno.
    private void cargarAlumno(long id) {
        // Se consulta en la BD los datos del mAlumno a través del objeto DAO.
        mAlumno = (new DAO(getActivity())).queryAlumno(id);
        // Si no se ha encontrado el mAlumno, se informa y se pasa al mModo
        // Agregar.
        if (mAlumno == null) {
            Toast.makeText(getActivity(), R.string.alumno_no_encontrado,
                    Toast.LENGTH_LONG).show();
            setModoAgregar();
        }
    }

    // Guarda el mAlumno en pantalla en la base de datos.
    void guardarAlumno() {
        // Se llena el objeto Alumno con los datos de las vistas.
        vistasToAlumno();
        // Dependiendo del mModo se inserta o actualiza el mAlumno (siempre y
        // cuando se hayan introducido los datos obligatorios).
        if (mAlumno.getNombre().length() > 0
                && mAlumno.getTelefono().length() > 0) {
            if (mModo.equals(MODO_AGREGAR)) {
                agregarAlumno();
            } else {
                actualizarAlumno();
            }
        } else {
            Toast.makeText(getActivity(),
                    getString(R.string.datos_obligatorios),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Agrega un mAlumno a la base de datos.
    private void agregarAlumno() {
        mAlumno.setAvatar(getRandomAvatarUrl());
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
        // Se resetean las vistas para poder agregar otro mAlumno (seguimos en
        // mModo Agregar).
        resetVistas();
    }

    // Actualiza un mAlumno en la base de datos.
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

    // Obtiene la referencia a las vistas del layout.
    private void initVistas(View v) {
        spnCurso = (Spinner) v.findViewById(R.id.spnCurso);
        txtNombre = (EditText) v.findViewById(R.id.txtNombre);
        txtTelefono = (EditText) v.findViewById(R.id.txtTelefono);
        txtDireccion = (EditText) v.findViewById(R.id.txtDireccion);
        ActionButton btnGuardar = (ActionButton) v.findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarAlumno();
            }
        });
    }

    // Hace reset sobre el contenido de las vistas.
    private void resetVistas() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
    }

    // Muestra los datos del objeto Alumno en las vistas.
    private void alumnoToVistas() {
        txtNombre.setText(mAlumno.getNombre());
        txtTelefono.setText(mAlumno.getTelefono());
        txtDireccion.setText(mAlumno.getDireccion());
        spnCurso.setSelection(mAdaptadorCursos.getPosition(mAlumno.getCurso()),
                true);
    }

    // Llena el objeto Alumno con los datos de las vistas.
    private void vistasToAlumno() {
        mAlumno.setNombre(txtNombre.getText().toString());
        mAlumno.setTelefono(txtTelefono.getText().toString());
        mAlumno.setDireccion(txtDireccion.getText().toString());
        mAlumno.setCurso((String) spnCurso.getSelectedItem());
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