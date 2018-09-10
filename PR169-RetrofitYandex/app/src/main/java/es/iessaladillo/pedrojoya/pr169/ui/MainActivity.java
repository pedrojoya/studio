package es.iessaladillo.pedrojoya.pr169.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;

import es.iessaladillo.pedrojoya.pr169.R;
import es.iessaladillo.pedrojoya.pr169.base.Event;
import es.iessaladillo.pedrojoya.pr169.base.RequestState;
import es.iessaladillo.pedrojoya.pr169.data.models.TranslateResponse;
import es.iessaladillo.pedrojoya.pr169.data.remote.ApiService;
import es.iessaladillo.pedrojoya.pr169.util.KeyboardUtils;

public class MainActivity extends AppCompatActivity {

    private EditText txtWord;
    private EditText txtTranslation;
    private ProgressBar pbTranslating;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(
                ApiService.getInstance(getApplicationContext()).getApi()))
                .get(MainActivityViewModel.class);
        observeTranslation();
        initViews();
    }

    private void initViews() {
        txtWord = ActivityCompat.requireViewById(this, R.id.txtWord);
        txtTranslation = ActivityCompat.requireViewById(this, R.id.txtTranslation);
        pbTranslating = ActivityCompat.requireViewById(this, R.id.pbTranslating);

        setupToolbar();
        setupFab();
        txtWord.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                translate();
                return true;
            } else {
                return false;
            }
        });
        txtWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(txtTranslation.getText().toString())) {
                    txtTranslation.setText("");
                }
            }
        });
        // Read-only.
        txtTranslation.setOnKeyListener((v, keyCode, event) -> true);
    }

    private void setupFab() {
        FloatingActionButton fab = ActivityCompat.requireViewById(this, R.id.fab);
        fab.setOnClickListener(view -> translate());
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void translate() {
        KeyboardUtils.hideKeyboard(txtWord);
        resetViews();
        if (!TextUtils.isEmpty(txtWord.getText().toString())) {
            viewModel.translateFromApi(txtWord.getText().toString());
        }
    }

    private void resetViews() {
        txtTranslation.setText("");
        txtTranslation.setFocusable(false);
    }

    private void observeTranslation() {
        viewModel.getTranslation().observe(this, request -> {
            if (request != null) {
                if (request instanceof RequestState.Loading) {
                    pbTranslating.setVisibility(
                            ((RequestState.Loading) request).isLoading() ? View.VISIBLE : View.INVISIBLE);
                } else if (request instanceof RequestState.Error) {
                    showRequestError(((RequestState.Error) request).getException());
                } else if (request instanceof RequestState.Result) {
                    //noinspection unchecked
                    showTranslation(((RequestState.Result<TranslateResponse>) request).getData());
                }
            }
        });
    }

    private void showTranslation(TranslateResponse response) {
        pbTranslating.setVisibility(View.INVISIBLE);
        if (response.getCode() == 200) {
            txtTranslation.setText(TextUtils.join(",", response.getText()));
        } else {
            showTranslationError();
        }
    }

    private void showRequestError(Event<Exception> event) {
        pbTranslating.setVisibility(View.INVISIBLE);
        Exception exception = event.getContentIfNotHandled();
        if (exception != null) {
            Snackbar.make(txtTranslation,
                    getString(R.string.main_activity_request_error, exception.getMessage()),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showTranslationError() {
        Snackbar.make(txtTranslation, R.string.main_activity_translation_error,
                Snackbar.LENGTH_SHORT).show();
    }

}
