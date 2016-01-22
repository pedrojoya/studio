package es.iessaladillo.pedrojoya.pr166.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr166.R;
import es.iessaladillo.pedrojoya.pr166.bd.DAO;
import es.iessaladillo.pedrojoya.pr166.modelos.Alumno;
import es.iessaladillo.pedrojoya.pr166.utils.ClickToSelectEditText;

public class AlumnoFragment extends Fragment {

    // Constantes.
    private static final String EXTRA_ID = "id";

    private EditText txtNombre;
    private EditText txtTelefono;
    private EditText txtDireccion;
    private ClickToSelectEditText<String> spnCurso;
    private Alumno mAlumno;

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
        initViews();
    }

    // Obtiene la referencia a las vistas del layout.
    private void initViews() {
        if (getView() != null) {
            spnCurso = (ClickToSelectEditText<String>) getView().findViewById(R.id.txtCurso);
            cargarCursos();
            txtNombre = (EditText) getView().findViewById(R.id.txtNombre);
            txtTelefono = (EditText) getView().findViewById(R.id.txtTelefono);
            txtDireccion = (EditText) getView().findViewById(R.id.txtDireccion);
        }
        // Establecemos el modo de funcionamiento dependiendo de si nos han
        // pasado el id del alumno o no.
        if (getArguments() == null || getArguments().getLong(EXTRA_ID) == 0) {
            setModoAgregar();
        }
        else {
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
        // Se llena el objeto Alumno con los datos de las vistas.
        vistasToAlumno();
        // Dependiendo del modo se inserta o actualiza el mAlumno (siempre y
        // cuando se hayan introducido los datos obligatorios).
        if (mAlumno.getNombre().length() > 0
                && mAlumno.getTelefono().length() > 0) {
            if (getArguments() == null || getArguments().getLong(EXTRA_ID) == 0) {
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

}
