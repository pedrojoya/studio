package es.iessaladillo.pedrojoya.pr236;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.livinglifetechway.quickpermissions.annotations.WithPermissions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import es.iessaladillo.pedrojoya.pr236.utils.FileUtils;
import es.iessaladillo.pedrojoya.pr236.utils.IntentsUtils;

public class MainActivity extends AppCompatActivity {

    private static final String RAW_FILE_NAME = "lorem.txt";
    private static final String ASSET_FILE_NAME = "audio.mp3";
    private static final int RP_WRITE_EXTERNAL = 1;

    private RadioGroup rgSource;
    private RadioGroup rgDestination;
    private Button btnDuplicate;
    private RadioButton rbPersonalExternal;
    private RadioButton rbPublicExternal;
    private RadioButton rbExternalCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        enableOptions(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
    }

    private void initViews() {
        rgSource = ActivityCompat.requireViewById(this, R.id.rgSource);
        rgDestination = ActivityCompat.requireViewById(this, R.id.rgDestination);
        btnDuplicate = ActivityCompat.requireViewById(this, R.id.btnDuplicate);
        rbPersonalExternal = ActivityCompat.requireViewById(this, R.id.rbPersonalExternal);
        rbPublicExternal = ActivityCompat.requireViewById(this, R.id.rbPublicExternal);
        rbExternalCache = ActivityCompat.requireViewById(this, R.id.rbExternalCache);

        btnDuplicate.setOnClickListener(v -> btnDuplicateOnClick());
    }

    private void btnDuplicateOnClick() {
        if (hasRequiredPermission()) {
            duplicateFileWithPermission();
        } else {
            duplicateFile();
        }
    }

    @WithPermissions(
            permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE}
    )
    void duplicateFileWithPermission() {
        duplicateFile();
    }

    private void duplicateFile() {
        try {
            File outputFile = new File(getDestinyFolder(), getSourceFilename());
            FileUtils.copyFile(getSourceInputStream(), outputFile);
            Log.d(getString(R.string.app_name), outputFile.getPath());
            Snackbar.make(btnDuplicate,
                    getString(R.string.main_activity_file_duplicated, outputFile.getPath()),
                    Snackbar.LENGTH_LONG).setAction(R.string.main_activity_open,
                    v -> showFile(outputFile, getSourceMimeType())).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSubfolderType() {
        return rgSource.getCheckedRadioButtonId()
                       == R.id.rbRaw ? Environment.DIRECTORY_DOWNLOADS : Environment
                       .DIRECTORY_MUSIC;
    }

    private File getDestinyFolder() {
        switch (rgDestination.getCheckedRadioButtonId()) {
            case R.id.rbPersonalExternal:
                return getExternalFilesDir(getSubfolderType());
            case R.id.rbPublicExternal:
                return Environment.getExternalStoragePublicDirectory(getSubfolderType());
            case R.id.rbInternalCache:
                return getCacheDir();
            case R.id.rbExternalCache:
                return getExternalCacheDir();
            default:
                return getFilesDir();
        }
    }

    private InputStream getSourceInputStream() throws IOException {
        return rgSource.getCheckedRadioButtonId() == R.id.rbRaw ? getResources().openRawResource(
                R.raw.lorem) : getAssets().open(getSourceFilename());
    }

    private String getSourceFilename() {
        return rgSource.getCheckedRadioButtonId() == R.id.rbRaw ? RAW_FILE_NAME : ASSET_FILE_NAME;
    }

    @NonNull
    private String getSourceMimeType() {
        return rgSource.getCheckedRadioButtonId() == R.id.rbRaw ? "text/plain" : "audio/mp3";
    }

    private boolean hasRequiredPermission() {
        int id = rgDestination.getCheckedRadioButtonId();
        return (id == R.id.rbPersonalExternal) || (id == R.id.rbPublicExternal) || (id
                == R.id.rbExternalCache);
    }

    private void showFile(File file, String mimeType) {
        // Files stored in personal directories can't be open from external apps.
        try {
            startActivity(IntentsUtils.newViewFileIntent(file, mimeType));
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.main_activity_error_opening_file),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void enableOptions(boolean mounted) {
        rbPersonalExternal.setEnabled(mounted);
        rbPublicExternal.setEnabled(mounted);
        rbExternalCache.setEnabled(mounted);
    }

}
