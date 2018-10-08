package es.iessaladillo.pedrojoya.pr169.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr169.R;
import es.iessaladillo.pedrojoya.pr169.base.Event;
import es.iessaladillo.pedrojoya.pr169.base.RequestState;
import es.iessaladillo.pedrojoya.pr169.data.models.TranslateResponse;
import es.iessaladillo.pedrojoya.pr169.data.remote.ApiService;
import es.iessaladillo.pedrojoya.pr169.util.KeyboardUtils;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private EditText txtWord;
    private EditText txtTranslation;
    private ProgressBar pbTranslating;

    private MainFragmentViewModel viewModel;

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, new MainFragmentViewModelFactory(
                ApiService.getInstance(requireContext()).getApi()))
                .get(MainFragmentViewModel.class);
        setupViews(view);
        observeTranslation();
    }

    private void setupViews(View view) {
        txtWord = ViewCompat.requireViewById(view, R.id.txtWord);
        txtTranslation = ViewCompat.requireViewById(view, R.id.txtTranslation);
        pbTranslating = ViewCompat.requireViewById(view, R.id.pbTranslating);

        setupToolbar(view);
        setupFab(view);
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

    private void setupFab(View view) {
        FloatingActionButton fab = ViewCompat.requireViewById(view, R.id.fab);
        fab.setOnClickListener(v -> translate());
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
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
        viewModel.getTranslation().observe(getViewLifecycleOwner(), request -> {
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
