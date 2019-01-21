package es.iessaladillo.pedrojoya.pr080.ui.main;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr080.R;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr080.data.remote.echo.EchoDataSourceImpl;
import es.iessaladillo.pedrojoya.pr080.data.remote.photo.PhotoDataSourceImpl;
import es.iessaladillo.pedrojoya.pr080.data.remote.search.SearchDataSourceImpl;
import es.iessaladillo.pedrojoya.pr080.utils.KeyboardUtils;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private static final String BASE_URL = "https://picsum.photos/100/100?image=";

    private MainFragmentViewModel viewModel;

    private EditText txtName;
    private ProgressBar pbProgress;
    private EditText txtPhotoNumber;
    private ImageView imgPhoto;

    public MainFragment() {
    }

    static MainFragment newInstance() {
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
        viewModel = ViewModelProviders.of(this, new MainFragmentViewModelFactory(
            new RepositoryImpl(new SearchDataSourceImpl(), new EchoDataSourceImpl(),
                new PhotoDataSourceImpl()))).get(
            MainFragmentViewModel.class);
        setupViews(requireView());
        observeSearchResult();
        observeEchoResult();
        observeLoadPhotoResult();
    }

    private void setupViews(View view) {
        txtName = ViewCompat.requireViewById(view, R.id.txtName);
        Button btnSearch = ViewCompat.requireViewById(view, R.id.btnSearch);
        Button btnEcho = ViewCompat.requireViewById(view, R.id.btnEcho);
        pbProgress = ViewCompat.requireViewById(view, R.id.pbProgress);
        Button btnLoadPhoto = ViewCompat.requireViewById(view, R.id.btnLoadPhoto);
        txtPhotoNumber = ViewCompat.requireViewById(view, R.id.txtPhotoNumber);
        imgPhoto = ViewCompat.requireViewById(view, R.id.imgPhoto);

        btnSearch.setOnClickListener(v -> search());
        btnEcho.setOnClickListener(v -> echo());
        btnLoadPhoto.setOnClickListener(v -> loadPhoto());

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

    private void loadPhoto() {
        try {
            int photoNumber = Integer.parseInt(txtPhotoNumber.getText().toString());
            String photoUrl = BASE_URL + photoNumber;
            KeyboardUtils.hideSoftKeyboard(requireActivity());
            viewModel.loadPhoto(photoUrl);
        } catch (NumberFormatException ignored) {
        }
    }

    private void observeSearchResult() {
        viewModel.getSearchResultLiveData().observe(getViewLifecycleOwner(), resource -> {
            if (resource.isLoading()) {
                pbProgress.setVisibility(View.VISIBLE);
            } else if (resource.hasError()) {
                showErrorSearching(resource.getException());
            } else if (resource.hasSuccess()) {
                showResult(resource.getData());
            }
        });
    }

    private void observeEchoResult() {
        viewModel.getEchoResultLiveData().observe(getViewLifecycleOwner(), resource -> {
            if (resource.isLoading()) {
                pbProgress.setVisibility(View.VISIBLE);
            } else if (resource.hasError()) {
                showErrorRequestingEcho(resource.getException());
            } else if (resource.hasSuccess()) {
                showResult(resource.getData());
            }
        });
    }

    private void observeLoadPhotoResult() {
        viewModel.getPhotoLiveData().observe(getViewLifecycleOwner(), resource -> {
            if (resource.isLoading()) {
                pbProgress.setVisibility(View.VISIBLE);
            } else if (resource.hasError()) {
                showErrorRequestingEcho(resource.getException());
            } else if (resource.hasSuccess()) {
                showPhoto(resource.getData());
            }
        });
    }

    private void showErrorSearching(Event<Exception> exceptionEvent) {
        Exception exception = exceptionEvent.getContentIfNotHandled();
        if (exception != null) {
            pbProgress.setVisibility(View.INVISIBLE);
            String message = exception.getMessage();
            if (message == null) message = getString(R.string.main_error_searching);
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showErrorRequestingEcho(Event<Exception> exceptionEvent) {
        Exception exception = exceptionEvent.getContentIfNotHandled();
        if (exception != null) {
            pbProgress.setVisibility(View.INVISIBLE);
            String message = exception.getMessage();
            if (message == null) message = getString(R.string.main_error_requesting_echo);
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showResult(Event<String> result) {
        String message = result.getContentIfNotHandled();
        if (message != null) {
            pbProgress.setVisibility(View.INVISIBLE);
            Toast.makeText(requireContext(),
                !TextUtils.isEmpty(message) ? message : getString(R.string.main_no_results),
                Toast.LENGTH_SHORT).show();
        }
    }

    private void showPhoto(Bitmap bitmap) {
        pbProgress.setVisibility(View.INVISIBLE);
        imgPhoto.setImageBitmap(bitmap);
    }

}
