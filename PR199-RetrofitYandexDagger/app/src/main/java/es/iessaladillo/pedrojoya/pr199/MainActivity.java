package es.iessaladillo.pedrojoya.pr199;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import es.iessaladillo.pedrojoya.pr199.api.Constants;
import es.iessaladillo.pedrojoya.pr199.api.YandexAPI;
import es.iessaladillo.pedrojoya.pr199.models.TranslateResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"CanBeFinal", "unused"})
public class MainActivity extends AppCompatActivity {

    private EditText txtTermino;
    private TextView lblTraduccion;

    @SuppressWarnings("WeakerAccess")
    @Inject
    YandexAPI apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se realiza la inyección de dependencias.
        ((App) getApplication()).getAppComponent().inject(this);
        setupToolbar();
        initViews();
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        txtTermino = ActivityCompat.requireViewById(this, R.id.txtTermino);
        txtTermino.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == getResources().getInteger(R.integer.imeTraducir)) {
                            traducir();
                            toggleSoftKeyboard();
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );
        FloatingActionButton fab = ActivityCompat.requireViewById(this, R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        traducir();
                    }
                }

        );
        lblTraduccion = ActivityCompat.requireViewById(this, R.id.lblTraduccion);
    }

    private void toggleSoftKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, 0);
    }

    private void traducir() {
        resetViews();
        if (!TextUtils.isEmpty(txtTermino.getText().toString())) {
            getTraduccion(txtTermino.getText().toString());
        }
    }

    private void resetViews() {
        lblTraduccion.setText("");
    }

    private void getTraduccion(String text) {
        Call<TranslateResponse> call = apiService.getTranslation(BuildConfig.YANDEX_API_KEY, text,
                Constants.LANG);
        call.enqueue(new Callback<TranslateResponse>() {
            @Override
            public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    showTraduccion(response.body());
                } else {
                    Snackbar.make(lblTraduccion, R.string.error_peticion, Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TranslateResponse> call, Throwable t) {
                Snackbar.make(lblTraduccion, R.string.error_peticion, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void showTraduccion(TranslateResponse response) {
        if (response.getCode() == 200) {
            lblTraduccion.setText(response.getText().get(0));
        } else {
            Snackbar.make(lblTraduccion, R.string.error_peticion, Snackbar.LENGTH_SHORT).show();
        }
    }

}
