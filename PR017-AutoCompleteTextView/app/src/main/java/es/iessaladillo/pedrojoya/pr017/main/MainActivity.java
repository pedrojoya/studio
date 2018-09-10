package es.iessaladillo.pedrojoya.pr017.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import es.iessaladillo.pedrojoya.pr017.Constants;
import es.iessaladillo.pedrojoya.pr017.R;
import es.iessaladillo.pedrojoya.pr017.data.Database;
import es.iessaladillo.pedrojoya.pr017.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr017.utils.KeyboardUtils;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView txtWord;
    private WebView webView;
    private Button btnTranslate;

    private MainActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(
                RepositoryImpl.getInstance(Database.getInstance()))).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        txtWord = ActivityCompat.requireViewById(this, R.id.txtWord);
        webView = ActivityCompat.requireViewById(this, R.id.wvWeb);
        btnTranslate = ActivityCompat.requireViewById(this, R.id.btnTranslate);

        txtWord.setAdapter(new MainActivityAdapter(this, viewModel.getWords()));
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
            }

        });
        btnTranslate.setOnClickListener(v -> searchWord(txtWord.getText().toString()));
        txtWord.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIsValidForm();
            }

        });
        txtWord.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!TextUtils.isEmpty(txtWord.getText().toString().trim())) {
                    searchWord(txtWord.getText().toString().trim());
                    return true;
                }
            }
            return false;
        });
        // Initial state.
        checkIsValidForm();
        if (!TextUtils.isEmpty(viewModel.getLoadedWord())) {
            searchWord(viewModel.getLoadedWord());
        }
    }

    private void searchWord(String word) {
        viewModel.setLoadedWord(word);
        KeyboardUtils.hideKeyboard(this);
        webView.loadUrl(Constants.BASE_URL + word);
    }

    private void checkIsValidForm() {
        btnTranslate.setEnabled(!TextUtils.isEmpty(txtWord.getText().toString().trim()));
    }

}