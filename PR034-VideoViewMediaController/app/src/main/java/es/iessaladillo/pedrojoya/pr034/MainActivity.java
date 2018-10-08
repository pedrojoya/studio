package es.iessaladillo.pedrojoya.pr034;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.livinglifetechway.quickpermissions.annotations.WithPermissions;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int RC_PICK_VIDEO = 0;

    private static final String STATE_CURRENT_POSITION = "STATE_CURRENT_POSITION";
    private static final String STATE_VIDEO_PATH = "STATE_VIDEO_PATH";

    private String videoPath;
    private int startPosition;

    private VideoView videoView;
    private MediaController mediaController;
    private ActionBar actionBar;
    private TextView lblEmptyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreInstanceState(savedInstanceState);
        setupViews();
        restoreVideo();
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            startPosition = savedInstanceState.getInt(STATE_CURRENT_POSITION, 0);
            videoPath = savedInstanceState.getString(STATE_VIDEO_PATH, "");
        }
    }

    private void setupViews() {
        setSupportActionBar(ActivityCompat.requireViewById(this, R.id.toolbar));
        actionBar = getSupportActionBar();

        videoView = ActivityCompat.requireViewById(this, R.id.videoView);
        videoView.setEnabled(false);
        videoView.setOnPreparedListener(mp -> {
            videoView.start();
            mediaController.show();
        });
        videoView.setZOrderOnTop(true);

        lblEmptyView = ActivityCompat.requireViewById(this, R.id.lblEmptyView);
        lblEmptyView.setOnClickListener(v -> pickVideo());

        mediaController = new MediaController(this) {
            @Override
            public void show() {
                super.show();
                //actionBar.show();
            }

            @Override
            public void hide() {
                super.hide();
                //actionBar.hide();
            }
        };
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }

    private void restoreVideo() {
        if (!TextUtils.isEmpty(videoPath)) {
            loadVideo(videoPath);
        } else {
            pickVideo();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_CURRENT_POSITION, videoView.getCurrentPosition());
        outState.putString(STATE_VIDEO_PATH, videoPath);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuVideo) {
            if (videoView.isPlaying()) {
                videoView.pause();
            }
            pickVideo();
        }
        return super.onOptionsItemSelected(item);
    }

    @WithPermissions(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE})
    public void pickVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK).setType("video/*");
        startActivityForResult(intent, RC_PICK_VIDEO);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && requestCode == RC_PICK_VIDEO) {
            Uri videoUriInGallery = intent.getData();
            videoPath = getRealPath(videoUriInGallery);
            startPosition = 0;
            if (!TextUtils.isEmpty(videoPath)) {
                loadVideo(videoPath);
            }
        }
    }

    private String getRealPath(Uri videoUriInGallery) {
        String path = "";
        String[] filePath = {MediaStore.Video.Media.DATA};
        Cursor c = getContentResolver().query(videoUriInGallery, filePath, null, null, null);
        if (c != null && c.moveToFirst()) {
            int columnIndex = c.getColumnIndex(filePath[0]);
            path = c.getString(columnIndex);
            c.close();
        }
        return path;
    }

    @WithPermissions(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE})
    public void loadVideo(String path) {
        lblEmptyView.setVisibility(View.INVISIBLE);
        videoView.setEnabled(true);
        videoView.setVideoPath(path);
        videoView.seekTo(startPosition);
    }

}
