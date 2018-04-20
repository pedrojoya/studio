package es.iessaldillo.pedrojoya.pr208.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import es.iessaldillo.pedrojoya.pr208.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button btnSearch;
    private EditText txtSearch;
    private TextView lblResult;
    private CompositeDisposable mCompositeDisposable;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mCompositeDisposable = new CompositeDisposable();
        btnSearch = ActivityCompat.requireViewById(this, R.id.btnConsultar);
        btnSearch.setOnClickListener(view -> consultar());
        txtSearch = ActivityCompat.requireViewById(this, R.id.txtConsulta);
        lblResult = ActivityCompat.requireViewById(this, R.id.lblResultado);
    }

    private void consultar() {
/*
        lblResult.setText("");
        viewModel.searchPeople(txtSearch.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(response -> lblResult.setText(String.valueOf(response.getPeopleResponseResults()
                                .get(0).getName())),
                        this::showError);
*/

        lblResult.setText("");

/*
        viewModel.searchPeople(txtSearch.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(this::showResult, this::showError);
*/

        viewModel.searchCharacter(txtSearch.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(this::showResult, this::showError);

    }

    private void showResult(String name) {
        lblResult.setText(name);
    }

    private void showError(Throwable error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    // TODO Ver https://newfivefour.com/android-rxjava-wait-for-network-calls-finish.html

}
