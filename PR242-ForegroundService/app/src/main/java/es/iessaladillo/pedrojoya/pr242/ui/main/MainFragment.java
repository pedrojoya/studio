package es.iessaladillo.pedrojoya.pr242.ui.main;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.livinglifetechway.quickpermissions.annotations.WithPermissions;

import java.io.File;

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
import es.iessaladillo.pedrojoya.pr242.BuildConfig;
import es.iessaladillo.pedrojoya.pr242.R;
import es.iessaladillo.pedrojoya.pr242.base.Event;
import es.iessaladillo.pedrojoya.pr242.services.ExportToTextFileService;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private MainFragmentViewModel viewModel;
    private MainFragmentAdapter listAdapter;

    private RecyclerView lstStudents;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        setupViews(view);
        viewModel.getExportedLiveData().observe(getViewLifecycleOwner(), this::showSnackBar);
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
    }

    private void setupRecyclerView(View view) {
        lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);
        listAdapter = new MainFragmentAdapter(viewModel.getStudents());
        listAdapter.setEmptyView(ViewCompat.requireViewById(view, R.id.emptyView));
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.addItemDecoration(
                new DividerItemDecoration(requireContext(), RecyclerView.VERTICAL));
        lstStudents.setAdapter(listAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        deleteStudent(viewHolder.getAdapterPosition());
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void deleteStudent(int position) {
        viewModel.deleteStudent(position);
        listAdapter.notifyItemRemoved(position);
    }

    private void showSnackBar(Event<Uri> uriEvent) {
        Uri uri = uriEvent.getContentIfNotHandled();
        if (uri != null) {
            Snackbar.make(lstStudents, R.string.main_exported, Snackbar.LENGTH_LONG)
                    .setAction(R.string.main_open, v -> showFile(uri))
                    .show();
        }
    }

    private void showFile(Uri uri) {
        if (uri != null && uri.getPath() != null) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW).setDataAndType(
                        FileProvider.getUriForFile(requireContext(),
                                BuildConfig.APPLICATION_ID + ".fileprovider",
                                new File(uri.getPath())), "text/plain")
                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    @WithPermissions(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void export() {
        ExportToTextFileService.start(requireContext(), viewModel.getStudents());
    }

}
