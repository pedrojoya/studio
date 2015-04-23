package es.iessaladillo.pedrojoya.pr028.fragmentos;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr028.R;
import es.iessaladillo.pedrojoya.pr028.bd.Instituto;
import es.iessaladillo.pedrojoya.pr028.modelos.Alumno;
import es.iessaladillo.pedrojoya.pr028.proveedores.InstitutoContentProvider;

public class AlumnoFragment extends Fragment {

    // Constantes.
    public static final String EXTRA_MODO = "modo";
    public static final String EXTRA_ID = "id";
    public static final String MODO_AGREGAR = "AGREGAR";
    public static final String MODO_EDITAR = "EDITAR";

    // Variables a nivel de clase.
    private EditText txtNombre;
    private EditText txtTelefono;
    private EditText txtDireccion;
    private Spinner spnCurso;

    private String modo;
    private Alumno alumno;
    private ArrayAdapter<CharSequence> adaptadorCursos;
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
        // tanto para cuando no está desplegado como para cuando sí lo está. La
        // fuente de datos para el adaptador es un array de constantes de
        // cadena.
        adaptadorCursos = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.cursos, android.R.layout.simple_spinner_item);
        adaptadorCursos
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCurso.setAdapter(adaptadorCursos);
    }

    // Realiza las operaciones iniciales necesarias en el modo Editar.
    private void setModoEditar() {
        // Se establece el modo Editar.
        modo = MODO_EDITAR;
        // Se cargan los datos del alumno a partir del id recibido.
        cargarAlumno(this.getArguments().getLong(EXTRA_ID));
        // Se escriben los datos del alumno en las vistas correspondientes.
        alumnoToVistas();
        // Se actuliza el título de la actividad en relación al modo.
        getActivity().setTitle(R.string.editar_alumno);
    }

    // Realiza las operaciones iniciales necesarias en el modo Agregar.
    private void setModoAgregar() {
        // Se establece el modo Agregar.
        modo = MODO_AGREGAR;
        // Se crea un nuevo objeto Alumno vacío.
        alumno = new Alumno();
        // Se actualiza el título de la actividad en relación al modo.
        getActivity().setTitle(R.string.agregar_alumno);
    }

    // Carga los datos del alumno provenientes de la BD en el objeto Alumno.
    private void cargarAlumno(long id) {
        // Se consulta en la BD los datos del alumno a través del content
        // provider en un hilo diferente al hilo principal.
        Uri uri = Uri.parse(InstitutoContentProvider.CONTENT_URI_ALUMNOS + "/"
                + id);
        CursorLoader cLoader = new CursorLoader(this.getActivity(), uri,
                Instituto.Alumno.TODOS, null, null, null);
        Cursor cursor = cLoader.loadInBackground();
        // Si no se ha encontrado el alumno, se informa y se pasa al modo
        // Agregar.
        if (cursor.getCount() == 1) {
            // Se coloca en el primer registro.
            cursor.moveToFirst();
            // Se carga en el objeto Alumno.
            alumno = Alumno.fromCursor(cursor);
        } else {
            Toast.makeText(this.getActivity(), R.string.alumno_no_encontrado,
                    Toast.LENGTH_LONG).show();
            setModoAgregar();
        }
        // Se cierra el cursor.
        cursor.close();
    }

    // Guarda el alumno en pantalla en la base de datos.
    public void guardarAlumno() {
        // Se llena el objeto Alumno con los datos de las vistas.
        vistasToAlumno();
        // Dependiendo del modo se inserta o actualiza el alumno (siempre y
        // cuando se hayan introducido los datos obligatorios).
        if (alumno.getNombre().length() > 0
                && alumno.getTelefono().length() > 0) {
            if (modo.equals(MODO_AGREGAR)) {
                agregarAlumno();
            } else {
                actualizarAlumno();
            }
        } else {
            Toast.makeText(this.getActivity(),
                    this.getString(R.string.datos_obligatorios),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Agrega un alumno a la base de datos.
    private void agregarAlumno() {
        alumno.setAvatar(getRandomAvatarUrl());
        // Realizo el insert a través del content provider. Como resultado se
        // obtiene la uri del alumno insertado, de la que se extrae su id.
        Uri resultado = this
                .getActivity()
                .getContentResolver()
                .insert(InstitutoContentProvider.CONTENT_URI_ALUMNOS,
                        Alumno.toContentValues(alumno));
        long id = Long.parseLong(resultado.getLastPathSegment());
        // Se informa de si todo ha ido bien.
        if (id >= 0) {
            alumno.setId(id);
            Toast.makeText(this.getActivity(),
                    getString(R.string.insercion_correcta), Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(this.getActivity(),
                    getString(R.string.insercion_incorrecta),
                    Toast.LENGTH_SHORT).show();
        }
        // Se resetean las vistas para poder agregar otro alumno (seguimos en
        // modo Agregar).
        resetVistas();
    }

    // Actualiza un alumno en la base de datos.
    private void actualizarAlumno() {
        // Se realiza el update en la BD a través del content provider. Como
        // resultado obtenemos el número de registros actualizados.
        Uri uri = Uri.parse(InstitutoContentProvider.CONTENT_URI_ALUMNOS + "/"
                + alumno.getId());
        if (this.getActivity().getContentResolver()
                .update(uri, Alumno.toContentValues(alumno), null, null) > 0) {
            Toast.makeText(this.getActivity(),
                    getString(R.string.actualizacion_correcta),
                    Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            Toast.makeText(this.getActivity(),
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
        txtNombre.setText(alumno.getNombre());
        txtTelefono.setText(alumno.getTelefono());
        txtDireccion.setText(alumno.getDireccion());
        spnCurso.setSelection(adaptadorCursos.getPosition(alumno.getCurso()),
                true);
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
        final String BASE_URL = "http://lorempixel.com/100/100/";
        final String[] tipos = {"abstract", "animals", "business", "cats", "city", "food",
                "night", "life", "fashion", "people", "nature", "sports", "technics", "transport"};
        return BASE_URL + tipos[mAleatorio.nextInt(tipos.length)] + "/" +
                (mAleatorio.nextInt(10) + 1) + "/";
    }

}