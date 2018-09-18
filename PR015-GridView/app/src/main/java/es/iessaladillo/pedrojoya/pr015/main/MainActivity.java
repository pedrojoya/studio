package es.iessaladillo.pedrojoya.pr015.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import es.iessaladillo.pedrojoya.pr015.R;
import es.iessaladillo.pedrojoya.pr015.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr015.data.local.Database;
import es.iessaladillo.pedrojoya.pr015.data.local.model.Word;
import es.iessaladillo.pedrojoya.pr015.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        GridView grdWords = ActivityCompat.requireViewById(this, R.id.grdWords);
        listAdapter = new MainActivityAdapter(viewModel.getWords());
        grdWords.setAdapter(listAdapter);
        grdWords.setOnItemClickListener(
                (adapterView, view, position, id) -> showWord(listAdapter.getItem(position)));
    }

    private void showWord(Word word) {
        ToastUtils.toast(this, getString(R.string.main_activity_translation, word.getSpanish(),
                word.getEnglish()));
    }

}