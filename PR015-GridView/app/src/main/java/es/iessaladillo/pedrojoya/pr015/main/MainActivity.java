package es.iessaladillo.pedrojoya.pr015.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import es.iessaladillo.pedrojoya.pr015.R;
import es.iessaladillo.pedrojoya.pr015.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr015.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.pr015.data.Database;
import es.iessaladillo.pedrojoya.pr015.data.Repository;
import es.iessaladillo.pedrojoya.pr015.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr015.data.model.Word;

public class MainActivity extends AppCompatActivity {

    private GridView grdWords;

    private MainActivityViewModel mViewModel;
    private MessageManager mMessageManager;
    @SuppressWarnings("FieldCanBeLocal")
    private Repository mRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageManager = new ToastMessageManager();
        mRepository = RepositoryImpl.getInstance(Database.getInstance());
        mViewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(mRepository)).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        grdWords = findViewById(R.id.grdWords);
        grdWords.setAdapter(
                new MainActivityAdapter(this, mViewModel.getData()));
        grdWords.setOnItemClickListener((adapterView, view, position, id) -> showWord(position));
    }

    private void showWord(int position) {
        Word word = (Word) grdWords.getItemAtPosition(position);
        mMessageManager.showMessage(grdWords,
                getString(R.string.main_activity_translation, word.getSpanish(), word.getEnglish()));
    }

}