package es.iessaladillo.pedrojoya.pr180.main;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr180.R;
import es.iessaladillo.pedrojoya.pr180.base.Event;
import es.iessaladillo.pedrojoya.pr180.base.RequestState;
import es.iessaladillo.pedrojoya.pr180.data.remote.HttpClient;
import es.iessaladillo.pedrojoya.pr180.utils.KeyboardUtils;

public class MainFragment extends Fragment {

    private EditText txtName;
    private ProgressBar pbProgress;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnSearch;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnEcho;
    private MainActivityViewModel viewModel;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity(),
                new MainActivityViewModelFactory(HttpClient.getInstance(getContext()))).get(
                MainActivityViewModel.class);
        initViews(getView());
        observeSearch();
        observeEcho();
    }

    private void initViews(View view) {
        txtName = ViewCompat.requireViewById(view, R.id.txtName);
        btnSearch = ViewCompat.requireViewById(view, R.id.btnSearch);
        btnEcho = ViewCompat.requireViewById(view, R.id.btnEcho);
        pbProgress = ViewCompat.requireViewById(view, R.id.pbProgress);

        btnSearch.setOnClickListener(v -> search());
        btnEcho.setOnClickListener(v -> echo());
    }

    private void search() {
        String text = txtName.getText().toString();
        if (TextUtils.isEmpty(text.trim())) return;
        KeyboardUtils.hideSoftKeyboard(requireActivity());
        viewModel.search(text);
    }

    private void echo() {
        String text = txtName.getText().toString();
        if (TextUtils.isEmpty(text.trim())) return;
        KeyboardUtils.hideSoftKeyboard(requireActivity());
        viewModel.requestEcho(text);
    }

    private void observeSearch() {
        viewModel.getSearchLiveData().observe(this, searchRequest -> {
            if (searchRequest instanceof RequestState.Error) {
                showErrorSearching((RequestState.Error) searchRequest);
            } else if (searchRequest instanceof RequestState.Result) {
                @SuppressWarnings("unchecked")
                RequestState.Result<Event<String>> searchResult = (RequestState.Result<Event<String>>) searchRequest;
                String result = searchResult.getData().getContentIfNotHandled();
                if (result != null) {
                    showResult(result);
                }
            } else if (searchRequest instanceof RequestState.Loading) {
                RequestState.Loading searchLoading = (RequestState.Loading) searchRequest;
                pbProgress.setVisibility(searchLoading.isLoading() ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    private void observeEcho() {
        viewModel.getEchoLiveData().observe(this, echoRequest -> {
            if (echoRequest instanceof RequestState.Error) {
                showErrorRequestingEcho((RequestState.Error) echoRequest);
            } else if (echoRequest instanceof RequestState.Result) {
                @SuppressWarnings("unchecked")
                RequestState.Result<Event<String>> echoResult = (RequestState.Result<Event<String>>) echoRequest;
                String result = echoResult.getData().getContentIfNotHandled();
                if (result != null) {
                    showResult(result);
                }
            } else if (echoRequest instanceof RequestState.Loading) {
                RequestState.Loading echoLoading = (RequestState.Loading) echoRequest;
                pbProgress.setVisibility(echoLoading.isLoading() ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    private void showErrorSearching(RequestState.Error searchError) {
        Exception exception = searchError.getException().getContentIfNotHandled();
        if (exception != null) {
            pbProgress.setVisibility(View.INVISIBLE);
            String message = exception.getMessage();
            if (message == null) message = getString(R.string.main_fragment_error_searching);
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showErrorRequestingEcho(RequestState.Error echoError) {
        Exception exception = echoError.getException().getContentIfNotHandled();
        if (exception != null) {
            pbProgress.setVisibility(View.INVISIBLE);
            String message = exception.getMessage();
            if (message == null) message = getString(R.string.main_fragment_error_requesting_echo);
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showResult(String result) {
        pbProgress.setVisibility(View.INVISIBLE);
        Toast.makeText(requireActivity(), result, Toast.LENGTH_SHORT).show();
    }

}
