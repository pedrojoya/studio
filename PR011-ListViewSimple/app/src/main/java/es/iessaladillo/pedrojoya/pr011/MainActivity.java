package es.iessaladillo.pedrojoya.pr011;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        OnItemClickListener,
        OnClickListener {

    private static final String STATE_LISTA = "estadoLista";
    private static final String STATE_LISTA_CONTENIDO = "contenidoLista";

    private ArrayAdapter<String> mAdaptador;
    private Parcelable mEstadoLista;
    private ArrayList<String> mListaContenido;

    private EditText txtNombre;
    private ImageButton btnAgregar;
    private ListView lstAlumnos;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            // Se obtiene el estado anterior de la lista.
            mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
            mListaContenido = savedInstanceState.getStringArrayList(STATE_LISTA_CONTENIDO);
        }
        else {
            mListaContenido = new ArrayList<>();
        }
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        btnAgregar = (ImageButton) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(this);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtNombre.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkDatos(s.toString());
            }

        });
        checkDatos(txtNombre.getText().toString());
        txtNombre.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // Si se ha pulsado Done.
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String nombre = txtNombre.getText().toString();
                    if (!TextUtils.isEmpty(nombre)) {
                        agregarAlumno(nombre);
                        return true;
                    }
                }
                return false;
            }
        });
        lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setEmptyView(findViewById(R.id.lblNoHayAlumnos));
        lstAlumnos.setOnItemClickListener(this);
    }

    private void setAdaptador() {
        // Se obtienen los datos para el mAdaptador de la lista.
        // ArrayList<String> alumnos = new ArrayList<String>(
        // Arrays.asList(getResources().getStringArray(R.array.alumnos)));
        // Se crea el adaptador ArrayAdapter con layout estándar.
        mAdaptador = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mListaContenido);
        lstAlumnos.setAdapter(mAdaptador);
    }

    // btnAgregar activo o no dependiendo de si hay nombre.
    private void checkDatos(String nombre) {
        btnAgregar.setEnabled(!TextUtils.isEmpty(nombre));
    }

    // Al hacer click sobre un elemento de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se elimina el elemento sobre el que se ha pulsado.
        eliminarAlumno(lst.getItemAtPosition(position));
    }

    // Al hacer click sobre el botón.
    @Override
    public void onClick(View v) {
        // Dependiendo de la vista pulsada.
        switch (v.getId()) {
            case R.id.btnAgregar:
                String nombre = txtNombre.getText().toString();
                if (!TextUtils.isEmpty(nombre)) {
                    agregarAlumno(nombre);
                }
                break;
        }
    }

    // Agrega un alumno a la lista.
    private void agregarAlumno(String nombre) {
        // Se agrega el alumno.
        mAdaptador.add(nombre);
        // Se pone en blanco txtNombre.
        txtNombre.setText("");
        checkDatos(txtNombre.getText().toString());
    }

    // Elimina un alumno de la lista.
    private void eliminarAlumno(Object item) {
        mAdaptador.remove((String) item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del ListView.
        mEstadoLista = lstAlumnos.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
        outState.putStringArrayList(STATE_LISTA_CONTENIDO, mListaContenido);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdaptador();
        // Se retaura el estado de la lista.
        if (mEstadoLista != null) {
            lstAlumnos.onRestoreInstanceState(mEstadoLista);
        }
    }

}