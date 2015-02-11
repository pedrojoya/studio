package es.iessaladillo.pedrojoya.pr113.Actividades;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import es.iessaladillo.pedrojoya.pr113.App;
import es.iessaladillo.pedrojoya.pr113.Model.Tarea;
import es.iessaladillo.pedrojoya.pr113.R;
import es.iessaladillo.pedrojoya.pr113.api.ApiService;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public class TareaActivity extends ActionBarActivity {

    public static final String EXTRA_TAREA = "tarea";

    @InjectView(R.id.lblUsuario)
    TextView mLblConcepto;
    @InjectView(R.id.txtUsuario)
    EditText mTxtConcepto;
    @InjectView(R.id.lblClave)
    TextView mLblResponsable;
    @InjectView(R.id.txtClave)
    EditText mTxtResponsable;

    private Tarea mTarea;
    private MenuItem mItemGuardar;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);
        ButterKnife.inject(this);
        // Se obtiene la tarea con la que ha sido llamado (si la hay).
        if (getIntent() != null && getIntent().hasExtra(EXTRA_TAREA)) {
            mTarea = (Tarea) getIntent().getParcelableExtra(EXTRA_TAREA);
        }
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        // Si tenemos tarea que mostrar, la mostramos.
        if (mTarea != null) {
            mTxtConcepto.setText(mTarea.getConcepto());
            mTxtResponsable.setText(mTarea.getResponsable());
        }
        // Se cambia el color del TextView dependiendo de si el EditText
        // correspondiente tiene el foco o no.
        mTxtConcepto.setOnFocusChangeListener(new OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(mLblConcepto, hasFocus);
            }

        });
        mTxtResponsable.setOnFocusChangeListener(new OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(mLblResponsable, hasFocus);
            }

        });
        mTxtConcepto.addTextChangedListener(new TextWatcher() {

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
                checkVisibility(mTxtConcepto, mLblConcepto);
            }

        });
        mTxtResponsable.addTextChangedListener(new TextWatcher() {

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
                checkVisibility(mTxtResponsable, mLblResponsable);
            }

        });
        // Comprobaciones iniciales.
        setColorSegunFoco(mLblConcepto, true);
        checkVisibility(mTxtResponsable, mLblResponsable);
        checkVisibility(mTxtConcepto, mLblConcepto);
    }

    public void agregar() {
        if (!TextUtils.isEmpty(mTxtConcepto.getText().toString()) && !TextUtils.isEmpty(mTxtResponsable.getText().toString())) {
            App.getApiService().createTarea(new Tarea(mTxtConcepto.getText().toString(), mTxtResponsable.getText().toString()));
        } else {
            Toast.makeText(this, "Debe introducir todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizar() {
        if (!TextUtils.isEmpty(mTxtConcepto.getText().toString()) && !TextUtils.isEmpty(mTxtResponsable.getText().toString())) {
            App.getApiService().updateTarea(mTarea.getObjectId(), new Tarea(mTxtConcepto.getText().toString(), mTxtResponsable.getText().toString()));
        } else {
            Toast.makeText(this, "Debe introducir todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    // Cuando se produce el evento de que la tarea se ha agregado correctamente.
    @Subscribe
    public void onTareaCreated(ApiService.TareaCreatedEvent event) {
        finish();
    }

    // Cuando se produce el evento de que la tarea se ha actualizado.
    @Subscribe
    public void onTareaUpdated(ApiService.TareaUpdatedEvent event) {
        finish();
    }

    // Si se ha producido un evento de error en la petición.
    @Subscribe
    public void onApiError(ApiService.ApiErrorEvent event) {
        RetrofitError error = event.getError();
        switch (error.getKind()) {
            // Si hay un problema con la red.
            case NETWORK:
                Toast.makeText(this, "Comprueba que tienes internet", Toast.LENGTH_SHORT).show();
                break;
            case HTTP:
                int status = error.getResponse().getStatus();
                if (status == HttpStatus.SC_FORBIDDEN) {
                    Toast.makeText(this, "Hay que estar logueado", Toast.LENGTH_SHORT).show();
                } else if (status == HttpStatus.SC_BAD_REQUEST) {
                    try {
                        JSONObject jError = new JSONObject(new String(((TypedByteArray) error.getResponse().getBody()).getBytes()));
                        int code = jError.getInt("code");
                        if (code == 202) {
                            Toast.makeText(this, "Ese usuario ya existe", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (status == HttpStatus.SC_NOT_FOUND) {
                    Toast.makeText(this, "Usuario desconocido", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Log.d("Mia", error.getMessage());
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Se registra la actividad en el bus.
        App.getEventBus().register(this);
        checkDatos();
    }

    @Override
    public void onPause() {
        super.onPause();
        // La actividad se desregistra del bus.
        App.getEventBus().unregister(this);
    }

    // Activa o desactiva el item de menú Guardar dependiendo de si hay datos.
    private void checkDatos() {
        if (mItemGuardar != null) {
            mItemGuardar.setEnabled(!TextUtils.isEmpty(mTxtConcepto.getText()
                    .toString())
                    && !TextUtils.isEmpty(mTxtResponsable.getText().toString()));
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
        getMenuInflater().inflate(R.menu.menu_activity_tarea, menu);
        mItemGuardar = menu.findItem(R.id.mnuGuardar);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuGuardar:
                // Dependiendo de si hay tarea o no.
                if (mTarea == null) {
                    agregar();
                } else {
                    actualizar();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}



