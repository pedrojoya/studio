package es.iessaladillo.pedrojoya.pr180.main;


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
import es.iessaladillo.pedrojoya.pr180.R;
import es.iessaladillo.pedrojoya.pr180.base.EventObserver;
import es.iessaladillo.pedrojoya.pr180.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr180.data.remote.HttpClient;
import es.iessaladillo.pedrojoya.pr180.data.remote.echo.EchoDataSourceImpl;
import es.iessaladillo.pedrojoya.pr180.data.remote.photo.PhotoDataSourceImpl;
import es.iessaladillo.pedrojoya.pr180.data.remote.search.SearchDataSourceImpl;
import es.iessaladillo.pedrojoya.pr180.utils.KeyboardUtils;
import okhttp3.OkHttpClient;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private static final String BASE_URL = "https://picsum.photos/300/300?image=";

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
        OkHttpClient httpClient = HttpClient.getInstance(requireContext());
        viewModel = ViewModelProviders.of(this, new MainFragmentViewModelFactory(
            new RepositoryImpl(new SearchDataSourceImpl(httpClient),
                new EchoDataSourceImpl(httpClient), new PhotoDataSourceImpl(httpClient)))).get(
            MainFragmentViewModel.class);
        setupViews(requireView());
        observeLoading();
        observeErrorMessage();
        observeSuccessMessage();
        observePhoto();
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

    private void observeLoading() {
        viewModel.loading().observe(getViewLifecycleOwner(),
            loading -> pbProgress.setVisibility(loading ? View.VISIBLE : View.INVISIBLE));
    }

    private void observeErrorMessage() {
        viewModel.errorMessage().observe(getViewLifecycleOwner(),
            new EventObserver<>(this::showMessage));
    }

    private void observeSuccessMessage() {
        viewModel.successMessage().observe(getViewLifecycleOwner(),
            new EventObserver<>(this::showMessage));
    }

    private void observePhoto() {
        viewModel.photo().observe(getViewLifecycleOwner(), this::showPhoto);
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

    private void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showPhoto(Bitmap bitmap) {
        pbProgress.setVisibility(View.INVISIBLE);
        imgPhoto.setImageBitmap(bitmap);
    }

}
