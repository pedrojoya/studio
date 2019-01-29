package es.iessaladillo.pedrojoya.pr100.ui.main;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.livinglifetechway.quickpermissions.annotations.WithPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr100.BuildConfig;
import es.iessaladillo.pedrojoya.pr100.R;
import es.iessaladillo.pedrojoya.pr100.base.EventObserver;
import es.iessaladillo.pedrojoya.pr100.services.ExportToTextFileService;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private MainFragmentViewModel viewModel;
    private MainFragmentAdapter listAdapter;

    private RecyclerView lstStudents;
    private TextView lblEmptyView;
    private ProgressBar progressBar;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, new MainFragmentViewModelFactory(
            new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.alumnos))))).get(
            MainFragmentViewModel.class);
        setupViews(requireView());
        observeStudents();
        observeLoading();
        observeUri();
        observeErrorMessage();
    }

    private void setupViews(View view) {
        setupToolbar(view);
        setupFab(view);
        setupRecyclerView(view);
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
    }

    private void setupFab(View view) {
        ViewCompat.requireViewById(view, R.id.btnExport).setOnClickListener(v -> export());
        progressBar = ViewCompat.requireViewById(view, R.id.progressBar);
    }

    private void setupRecyclerView(View view) {
        lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);

        listAdapter = new MainFragmentAdapter();
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(new LinearLayoutManager(requireContext()));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.addItemDecoration(
            new DividerItemDecoration(requireContext(), RecyclerView.VERTICAL));
        lstStudents.setAdapter(listAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    viewModel.deleteStudent(viewHolder.getAdapterPosition());
                }
            });
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void observeStudents() {
        viewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            listAdapter.submitList(students);
            lblEmptyView.setVisibility(students.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void observeUri() {
        viewModel.getUri().observe(getViewLifecycleOwner(),
            new EventObserver<>(this::showSnackBar));
    }

    private void observeLoading() {
        viewModel.getLoading().observe(getViewLifecycleOwner(),
            loading -> progressBar.setVisibility(loading ? View.VISIBLE : View.INVISIBLE));
    }

    private void observeErrorMessage() {
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(),
            new EventObserver<>(this::showErrorMessage));
    }

    private void showSnackBar(Uri uri) {
        Snackbar.make(lstStudents, R.string.main_exported, Snackbar.LENGTH_LONG).setAction(
            R.string.main_open, v -> showFile(uri)).show();
    }

    private void showFile(Uri uri) {
        if (uri != null && uri.getPath() != null) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW).setDataAndType(
                    FileProvider.getUriForFile(requireContext(),
                        BuildConfig.APPLICATION_ID + ".fileprovider", new File(uri.getPath())),
                    "text/plain").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION));
            } catch (ActivityNotFoundException e) {
                Snackbar.make(lstStudents, getString(R.string.main_no_app_to_show_file),
                    Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void showErrorMessage(String message) {
        Snackbar.make(lstStudents, message, Snackbar.LENGTH_SHORT).show();
    }

    @SuppressWarnings("WeakerAccess")
    @WithPermissions(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void export() {
        List<String> currentStudents = viewModel.getStudents().getValue();
        if (currentStudents != null) {
            ExportToTextFileService.start(requireContext(), new ArrayList<>(currentStudents));
        }
    }

}
