package es.iessaladillo.pedrojoya.pr017.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import es.iessaladillo.pedrojoya.pr017.Constants;
import es.iessaladillo.pedrojoya.pr017.R;
import es.iessaladillo.pedrojoya.pr017.data.Database;
import es.iessaladillo.pedrojoya.pr017.data.Repository;
import es.iessaladillo.pedrojoya.pr017.data.RepositoryImpl;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView txtWord;
    private WebView wvWeb;
    private Button btnTranslate;

    private Repository mRepository;
    private MainActivityViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRepository = RepositoryImpl.getInstance(Database.getInstance());
        mViewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(mRepository)).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        txtWord = findViewById(R.id.txtWord);
        wvWeb = findViewById(R.id.wvWeb);
        btnTranslate = findViewById(R.id.btnTranslate);

        txtWord.setAdapter(new MainActivityAdapter(this, mViewModel.getWords()));
        wvWeb.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                wvWeb.setVisibility(View.VISIBLE);
            }

        });
        btnTranslate.setOnClickListener(v -> {
            mViewModel.setLoadedWord(txtWord.getText().toString());
            searchWord(mViewModel.getLoadedWord());
        });
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
        // Initial state.
        checkIsValidForm();
        if (!mViewModel.getLoadedWord().isEmpty()) {
            searchWord(mViewModel.getLoadedWord());
        }
    }

    private void searchWord(String word) {
        wvWeb.loadUrl(Constants.BASE_URL + word);
    }

    private void checkIsValidForm() {
        btnTranslate.setEnabled(!TextUtils.isEmpty(txtWord.getText().toString()));
    }

}