package es.iessaladillo.pedrojoya.pr017.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr017.R;
import es.iessaladillo.pedrojoya.pr017.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr017.data.local.Database;
import es.iessaladillo.pedrojoya.pr017.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr017.utils.TextViewUtils;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL =
            "http://www.wordreference.com/es/translation" + ".asp?tranword=";

    private AutoCompleteTextView txtWord;
    private WebView webView;
    private Button btnTranslate;

    private MainActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
                MainActivityViewModel.class);
        setupViews();
        checkInitialState();
    }

    private void checkInitialState() {
        checkIsValidForm();
        if (!TextUtils.isEmpty(viewModel.getLoadedWord())) {
            searchWord();
        }
    }

    private void setupViews() {
        txtWord = ActivityCompat.requireViewById(this, R.id.txtWord);
        webView = ActivityCompat.requireViewById(this, R.id.wvWeb);
        btnTranslate = ActivityCompat.requireViewById(this, R.id.btnTranslate);

        txtWord.setAdapter(new MainActivityAdapter(viewModel.getWords()));
        txtWord.setText(viewModel.getLoadedWord());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
            }

        });
        btnTranslate.setOnClickListener(v -> searchWord());
        TextViewUtils.addAfterTextChangedListener(txtWord, s -> checkIsValidForm());
        TextViewUtils.setOnImeActionListener(txtWord, EditorInfo.IME_ACTION_SEARCH,
                (v, event) -> searchWord());
    }

    private void searchWord() {
        String word = txtWord.getText().toString().trim();
        if (isValidForm()) {
            KeyboardUtils.hideSoftKeyboard(this);
            viewModel.setLoadedWord(word);
            webView.loadUrl(BASE_URL + word);
        }
    }

    private boolean isValidForm() {
        return !TextUtils.isEmpty(txtWord.getText().toString().trim());
    }

    private void checkIsValidForm() {
        btnTranslate.setEnabled(!TextUtils.isEmpty(txtWord.getText().toString().trim()));
    }

}