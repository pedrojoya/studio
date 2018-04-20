package es.iessaladillo.pedrojoya.pr028.fragmentos;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr028.R;
import es.iessaladillo.pedrojoya.pr028.bd.DbContract;
import es.iessaladillo.pedrojoya.pr028.modelos.Alumno;
import es.iessaladillo.pedrojoya.pr028.proveedores.DbAsyncQueryHandler;
import es.iessaladillo.pedrojoya.pr028.proveedores.DbContentProvider;

public class AlumnoFragment extends Fragment implements DbAsyncQueryHandler.Callbacks {

    // Constantes.
    public static final String EXTRA_MODO = "modo";
    public static final String EXTRA_ID = "id";
    public static final String MODO_AGREGAR = "AGREGAR";
    public static final String MODO_EDITAR = "EDITAR";
    private static final String BASE_URL = "http://lorempixel.com/100/100/";
    private static final int TOKEN_INSERT = 0;
    private static final int TOKEN_UPDATE = 1;

    // Variables a nivel de clase.
    private EditText txtNombre;
    private EditText txtTelefono;
    private EditText txtDireccion;
    private Spinner spnCurso;

    private String modo;
    private Alumno alumno;
    private ArrayAdapter<CharSequence> adaptadorCursos;
    private Random mAleatorio;
    private DbAsyncQueryHandler mAlumnoAsyncQueryHandler;

    public static AlumnoFragment newInstance(String modo, long id) {
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
        if (modo != null && modo.equals(MODO_EDITAR)) {
            setModoEditar();
        } else {
            setModoAgregar();
        }
        mAleatorio = new Random();
        // Se crea el objeto para realizar las operaciones sobre el content provider en segundo
        // plano.
        mAlumnoAsyncQueryHandler = new DbAsyncQueryHandler(requireActivity().getContentResolver(),
                this);
    }

    // Carga los cursos en el spinner.
    private void cargarCursos() {
        // Se crea un ArrayAdapter para el spinner, que use layouts estándar
        // tanto para cuando no está desplegado como para cuando sí lo está. La
        // fuente de datos para el adaptador es un array de constantes de
        // cadena.
        adaptadorCursos = ArrayAdapter.createFromResource(requireActivity(), R.array.cursos,
                android.R.layout.simple_spinner_item);
        adaptadorCursos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCurso.setAdapter(adaptadorCursos);
    }

    // Realiza las operaciones iniciales necesarias en el modo Editar.
    private void setModoEditar() {
        // Se establece el modo Editar.
        modo = MODO_EDITAR;
        // Se cargan los datos del alumno a partir del id recibido.
        cargarAlumno(getArguments().getLong(EXTRA_ID));
        // Se escriben los datos del alumno en las vistas correspondientes.
        alumnoToVistas();
        // Se actuliza el título de la actividad en relación al modo.
        requireActivity().setTitle(R.string.editar_alumno);
    }

    // Realiza las operaciones iniciales necesarias en el modo Agregar.
    private void setModoAgregar() {
        // Se establece el modo Agregar.
        modo = MODO_AGREGAR;
        // Se crea un nuevo objeto Alumno vacío.
        alumno = new Alumno();
        // Se actualiza el título de la actividad en relación al modo.
        requireActivity().setTitle(R.string.agregar_alumno);
    }

    // Carga los datos del alumno provenientes de la BD en el objeto Alumno.
    private void cargarAlumno(long id) {
        // Se consulta en la BD los datos del alumno a través del content
        // provider en un hilo diferente al hilo principal.
        Uri uri = Uri.parse(DbContentProvider.CONTENT_URI_ALUMNOS + "/" + id);
        CursorLoader cLoader = new CursorLoader(requireActivity(), uri, DbContract.Alumno.TODOS,
                null, null, null);
        Cursor cursor = cLoader.loadInBackground();
        // Si no se ha encontrado el alumno, se informa y se pasa al modo
        // Agregar.
        if (cursor.getCount() == 1) {
            // Se coloca en el primer registro.
            cursor.moveToFirst();
            // Se carga en el objeto Alumno.
            alumno = Alumno.fromCursor(cursor);
        } else {
            Toast.makeText(requireActivity(), R.string.alumno_no_encontrado, Toast.LENGTH_LONG)
                    .show();
            setModoAgregar();
        }
        // Se cierra el cursor.
        cursor.close();
    }

    // Guarda el alumno en pantalla en la base de datos.
    private void guardarAlumno() {
        // Se llena el objeto Alumno con los datos de las vistas.
        vistasToAlumno();
        // Dependiendo del modo se inserta o actualiza el alumno (siempre y
        // cuando se hayan introducido los datos obligatorios).
        if (alumno.getNombre().length() > 0 && alumno.getTelefono().length() > 0) {
            if (modo.equals(MODO_AGREGAR)) {
                agregarAlumno();
            } else {
                actualizarAlumno();
            }
        } else {
            Toast.makeText(requireActivity(), getString(R.string.datos_obligatorios),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Agrega un alumno a la base de datos.
    private void agregarAlumno() {
        alumno.setAvatar(getRandomAvatarUrl());
        // Se realiza la inserción a través del AsyncQueryHandler.
        mAlumnoAsyncQueryHandler.startInsert(TOKEN_INSERT, null,
                DbContentProvider.CONTENT_URI_ALUMNOS, Alumno.toContentValues(alumno));
    }

    // Actualiza un alumno en la base de datos.
    private void actualizarAlumno() {
        // Se realiza la actualización a través del AsyncQueryHandler.
        Uri uri = Uri.withAppendedPath(DbContentProvider.CONTENT_URI_ALUMNOS,
                String.valueOf(alumno.getId()));
        mAlumnoAsyncQueryHandler.startUpdate(TOKEN_UPDATE, null, uri,
                Alumno.toContentValues(alumno), null, null);
    }

    // Obtiene la referencia a las vistas del layout.
    private void initVistas(View v) {
        spnCurso = (Spinner) v.findViewById(R.id.spnCurso);
        txtNombre = (EditText) v.findViewById(R.id.txtNombre);
        txtTelefono = (EditText) v.findViewById(R.id.txtTelefono);
        txtDireccion = (EditText) v.findViewById(R.id.txtDireccion);
        FloatingActionButton btnGuardar = (FloatingActionButton) v.findViewById(R.id.btnGuardar);
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
        txtNombre.setText(alumno.getNombre());
        txtTelefono.setText(alumno.getTelefono());
        txtDireccion.setText(alumno.getDireccion());
        spnCurso.setSelection(adaptadorCursos.getPosition(alumno.getCurso()), true);
    }

    // Llena el objeto Alumno con los datos de las vistas.
    private void vistasToAlumno() {
        alumno.setNombre(txtNombre.getText().toString());
        alumno.setTelefono(txtTelefono.getText().toString());
        alumno.setDireccion(txtDireccion.getText().toString());
        alumno.setCurso((String) spnCurso.getSelectedItem());
    }

    // Retorna una url aleatoria correspondiente a una imagen para el avatar.
    private String getRandomAvatarUrl() {
        final String[] tipos = {"abstract", "animals", "business", "cats", "city", "food",
                                "night", "life", "fashion", "people", "nature", "sports",
                                "technics", "transport"};
        return BASE_URL + tipos[mAleatorio.nextInt(tipos.length)] + "/" + (mAleatorio.nextInt(10)
                + 1) + "/";
    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
    }

    @Override
    public void onInsertComplete(int token, Object cookie, Uri uri) {
        // Como resultado de la inserción se obtiene la uri del alumno insertado, de la que se
        // extrae su id.
        if (uri != null) {
            long id = Long.parseLong(uri.getLastPathSegment());
            // Se informa de si ha ido bien.
            if (id >= 0) {
                alumno.setId(id);
                Toast.makeText(requireActivity(), getString(R.string.insercion_correcta),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireActivity(), getString(R.string.insercion_incorrecta),
                        Toast.LENGTH_SHORT).show();
            }
            // Se resetean las vistas para poder agregar otro alumno (seguimos en
            // modo Agregar).
            resetVistas();
        }
    }

    @Override
    public void onUpdateComplete(int token, Object cookie, int result) {
        // Como resultado obtenemos el número de registros actualizados.
        if (result > 0) {
            Toast.makeText(requireActivity(), getString(R.string.actualizacion_correcta),
                    Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        } else {
            Toast.makeText(requireActivity(), getString(R.string.actualizacion_incorrecta),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteComplete(int token, Object cookie, int result) {
    }

}
