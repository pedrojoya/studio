package es.iessaladillo.pedrojoya.pr095.ui.main;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

import es.iessaladillo.pedrojoya.pr095.Constants;
import es.iessaladillo.pedrojoya.pr095.R;
import es.iessaladillo.pedrojoya.pr095.data.local.Database;
import es.iessaladillo.pedrojoya.pr095.data.local.Repository;
import es.iessaladillo.pedrojoya.pr095.data.local.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr095.data.model.Song;
import es.iessaladillo.pedrojoya.pr095.services.MusicService;
import es.iessaladillo.pedrojoya.pr095.utils.IntentsUtils;

public class MainFragment extends Fragment {

    private FloatingActionButton btnPlayStop;
    private ListView lstSongs;

    private Repository repository;
    private DownloadManager downloadManager;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver downloadedSongBroadcastReceiver;
    private BroadcastReceiver completedSongBroadcastReceiver;
    private MainFragmentAdapter adapter;
    private Intent serviceIntent;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        repository = RepositoryImpl.getInstance(Database.getInstance());
        downloadManager = (DownloadManager) requireActivity().getSystemService(
                Context.DOWNLOAD_SERVICE);
        localBroadcastManager = LocalBroadcastManager.getInstance(requireActivity());
        serviceIntent = new Intent(requireActivity().getApplicationContext(), MusicService.class);
        initViews(getView());
        createCompletedSongBroadcastReceiver();
        createDownloadedSongBroadcastReceiver();
    }

    private void createCompletedSongBroadcastReceiver() {
        completedSongBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                int nextSongPosition =
                        (lstSongs.getCheckedItemPosition() + 1) % lstSongs.getCount();
                playSong(nextSongPosition);
            }

        };
    }

    private void createDownloadedSongBroadcastReceiver() {
        downloadedSongBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                showDownloadState(intent);
            }

        };
    }

    private void initViews(View view) {
        setupFab();
        setupListView(view);
    }

    private void setupListView(View view) {
        lstSongs = ViewCompat.requireViewById(view, R.id.lstSongs);
        adapter = new MainFragmentAdapter(requireActivity(), repository.getSongs(), lstSongs);
        if (lstSongs != null) {
            lstSongs.setAdapter(adapter);
            lstSongs.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            lstSongs.setOnItemClickListener(
                    (adapterView, view1, position, id) -> playSong(position));
            lstSongs.setEmptyView(ViewCompat.requireViewById(view, R.id.lblEmptyView));
            // API 21+ it will work with CoordinatorLayout.
            ViewCompat.setNestedScrollingEnabled(lstSongs, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerCompletedSongBroadcastReceiver();
        resgisterDownloadedSongBroadcastReceiver();
        showPlayingState();
    }

    private void registerCompletedSongBroadcastReceiver() {
        localBroadcastManager.registerReceiver(completedSongBroadcastReceiver,
                new IntentFilter(MusicService.ACTION_SONG_COMPLETED));
    }

    private void resgisterDownloadedSongBroadcastReceiver() {
        requireActivity().registerReceiver(downloadedSongBroadcastReceiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private void showPlayingState() {
        if (lstSongs.getCheckedItemPosition() == AdapterView.INVALID_POSITION) {
            showStopped();
        } else {
            showPlaying();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(completedSongBroadcastReceiver);
        requireActivity().unregisterReceiver(downloadedSongBroadcastReceiver);
    }

    private void showDownloadState(Intent intent) {
        long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c != null && c.moveToFirst()) {
            int state = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (state) {
                case DownloadManager.STATUS_SUCCESSFUL:
                    String sUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    // Update the icon of the item in the ListView.
                    adapter.notifyDataSetInvalidated();
                    playSong(Uri.parse(sUri));
                    break;
                case DownloadManager.STATUS_FAILED:
                    String reason = c.getString(c.getColumnIndex(DownloadManager.COLUMN_REASON));
                    showErrorDownloadingSong(reason);
                    break;
            }
            c.close();
        }
    }

    private void showErrorDownloadingSong(String reason) {
        Toast.makeText(requireActivity(), getString(R.string.main_fragment_download_failed, reason),
                Toast.LENGTH_SHORT).show();
    }

    private void downloadSong(Song song) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(song.getUrl()));
        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC,
                song.getName() + Constants.MP3_FILE_EXTENSION);
        // solicitud.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, song
        // .getName() + MP3_FILE_EXTENSION);
        request.setTitle(song.getName());
        request.setDescription(getString(R.string.main_fragment_description, song.getName(), song.getDuration()));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        downloadManager.enqueue(request);
    }

    private void showDownloading(Song song) {
        Toast.makeText(requireActivity(), getString(R.string.main_fragment_downloading, song.getName()),
                Toast.LENGTH_SHORT).show();
    }

    private void playSong(int position) {
        lstSongs.setItemChecked(position, true);
        // Update icon of the item in the ListView.
        adapter.notifyDataSetInvalidated();
        Song song = (Song) lstSongs.getItemAtPosition(position);
        File songFile = song.getPublicFile();
        if (songFile != null) {
            playSong(Uri.fromFile(songFile));
        } else {
            downloadSong(song);
            showDownloading(song);
        }
    }

    private void playSong(Uri uri) {
        // Update icon of the item in ListView.
        adapter.notifyDataSetInvalidated();
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File file = new File(directory, uri.getLastPathSegment());
        playSongOnService(file);
        showPlaying();
    }

    private void playSongOnService(File file) {
        serviceIntent.putExtra(MusicService.EXTRA_SONG_PATH, file.getAbsolutePath());
        requireActivity().startService(serviceIntent);
    }

    private void playstop() {
        if (lstSongs.getCheckedItemPosition() == AdapterView.INVALID_POSITION) {
            playSong(0);
        } else {
            pararServicio();
        }
    }

    private void pararServicio() {
        stopSongInService();
        // Update item.
        lstSongs.setItemChecked(lstSongs.getCheckedItemPosition(), false);
        showStopped();
    }

    private void stopSongInService() {
        requireActivity().stopService(serviceIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuDownloads) {
            startActivity(IntentsUtils.newStandardDownloadsActivityIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupFab() {
        btnPlayStop = requireActivity().findViewById(R.id.btnPlayStop);
        if (btnPlayStop != null) {
            btnPlayStop.setOnClickListener(view -> playstop());
        }
    }

    private void showPlaying() {
        if (btnPlayStop != null) {
            btnPlayStop.setImageResource(R.drawable.ic_stop_white_24dp);
        }
    }

    private void showStopped() {
        if (btnPlayStop != null) {
            btnPlayStop.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        }
    }

}
