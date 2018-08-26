package es.iessaladillo.pedrojoya.pr242.ui.main;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.livinglifetechway.quickpermissions.annotations.WithPermissions;

import java.io.File;

import es.iessaladillo.pedrojoya.pr242.R;
import es.iessaladillo.pedrojoya.pr242.services.ExportToTextFileService;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;
    private RecyclerView lstStudents;

    private MainActivityViewModel viewModel;
    private MainActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
        setupReceiver();
    }

    private void initViews() {
        setupToolbar();
        setupFAB();
        setupRecyclerView();
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        ActivityCompat.requireViewById(this, R.id.btnExport).setOnClickListener(v -> export());
    }

    private void setupRecyclerView() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        adapter = new MainActivityAdapter(viewModel.getStudents());
        adapter.setEmptyView(ActivityCompat.requireViewById(this, R.id.emptyView));
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(adapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        deleteStudent(viewHolder.getAdapterPosition());
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void deleteStudent(int position) {
        viewModel.deleteStudent(position);
        adapter.notifyItemRemoved(position);
    }

    private void setupReceiver() {
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Uri uri = intent.getParcelableExtra(ExportToTextFileService.EXTRA_FILENAME);
                showSnackBar(uri);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter exportarFilter = new IntentFilter(ExportToTextFileService.ACTION_EXPORTED);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, exportarFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Se quita del registro el receptor.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void showSnackBar(final Uri uri) {
        Snackbar.make(lstStudents, R.string.main_activity_exported, Snackbar.LENGTH_LONG).setAction(
                R.string.main_activity_open, v -> showFile(uri)).show();
    }

    private void showFile(Uri uri) {
        if (uri != null && uri.getPath() != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uriProvider = FileProvider.getUriForFile(this,
                    "es.iessaladillo.pedrojoya.pr242.fileprovider", new File(uri.getPath()));
            intent.setDataAndType(uriProvider, "text/plain");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    @WithPermissions(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void export() {
        ExportToTextFileService.start(this, viewModel.getStudents());
    }

}
