package es.iessaladillo.pedrojoya.pr169.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import es.iessaladillo.pedrojoya.pr169.R;
import es.iessaladillo.pedrojoya.pr169.data.models.TranslateResponse;
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
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        txtWord = ActivityCompat.requireViewById(this, R.id.txtWord);
        txtTranslation = ActivityCompat.requireViewById(this, R.id.txtTranslation);
        pbTranslating = ActivityCompat.requireViewById(this, R.id.pbTranslating);

        setupToolbar();
        setupFab();
        txtWord.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == getResources().getInteger(
                    R.integer.activity_main_content_txtWord_imeActionId)) {
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
            getTranslationFromApi(txtWord.getText().toString());
        }
    }

    private void resetViews() {
        txtTranslation.setText("");
        txtTranslation.setFocusable(false);
    }

    private void getTranslationFromApi(String word) {
        pbTranslating.setVisibility(View.VISIBLE);
        viewModel.getTranslationFromApi(word).observe(this, yandexRequest -> {
            if (yandexRequest != null) {
                if (yandexRequest.getThrowable() != null) {
                    showRequestError(yandexRequest.getThrowable());
                } else {
                    showTranslation(yandexRequest.getTranslateResponse());
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

    private void showRequestError(Throwable throwable) {
        pbTranslating.setVisibility(View.INVISIBLE);
        Snackbar.make(txtTranslation, getString(R.string.main_activity_request_error, throwable.getMessage())
                , Snackbar
                .LENGTH_SHORT)
                .show();
    }

    private void showTranslationError() {
        Snackbar.make(txtTranslation, R.string.main_activity_translation_error,
                Snackbar.LENGTH_SHORT).show();
    }

}
