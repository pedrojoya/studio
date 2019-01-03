package es.iessaladillo.pedrojoya.pr119.ui.main;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr119.R;
import es.iessaladillo.pedrojoya.pr119.utils.FileUtils;
import es.iessaladillo.pedrojoya.pr119.utils.IntentsUtils;

public class MainFragment extends Fragment {

    private static final String RAW_FILE_NAME = "lorem.txt";
    private static final String ASSET_FILE_NAME = "audio.mp3";
    private static final int RP_WRITE_EXTERNAL = 1;

    private RadioGroup rgSource;
    private RadioGroup rgDestination;
    private Button btnDuplicate;
    private RadioButton rbPersonalExternal;
    private RadioButton rbPublicExternal;
    private RadioButton rbExternalCache;

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(requireView());
        enableOptions(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
    }

    private void setupViews(View view) {
        rgSource = ViewCompat.requireViewById(view, R.id.rgSource);
        rgDestination = ViewCompat.requireViewById(view, R.id.rgDestination);
        btnDuplicate = ViewCompat.requireViewById(view, R.id.btnDuplicate);
        rbPersonalExternal = ViewCompat.requireViewById(view, R.id.rbPersonalExternal);
        rbPublicExternal = ViewCompat.requireViewById(view, R.id.rbPublicExternal);
        rbExternalCache = ViewCompat.requireViewById(view, R.id.rbExternalCache);

        btnDuplicate.setOnClickListener(v -> btnDuplicateOnClick());
    }

    private void btnDuplicateOnClick() {
        if (hasRequiredPermission() && !canWriteOnExternalStorage()) {
            requestPermission();
        } else {
            duplicateFile();
        }
    }

    private void duplicateFile() {
        try {
            File outputFile = new File(getDestinyFolder(), getSourceFilename());
            FileUtils.copyFile(getSourceInputStream(), outputFile);
            Log.d(getString(R.string.app_name), outputFile.getPath());
            Snackbar.make(btnDuplicate,
                getString(R.string.main_file_duplicated, outputFile.getPath()),
                Snackbar.LENGTH_LONG).setAction(R.string.main_open,
                v -> showFile(outputFile, getSourceMimeType())).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enableOptions(boolean mounted) {
        rbPersonalExternal.setEnabled(mounted);
        rbPublicExternal.setEnabled(mounted);
        rbExternalCache.setEnabled(mounted);
    }

    private String getSubfolderType() {
        return rgSource.getCheckedRadioButtonId()
                   == R.id.rbRaw ? Environment.DIRECTORY_DOWNLOADS : Environment
                   .DIRECTORY_MUSIC;
    }

    private File getDestinyFolder() {
        switch (rgDestination.getCheckedRadioButtonId()) {
            case R.id.rbPersonalExternal:
                return requireContext().getExternalFilesDir(getSubfolderType());
            case R.id.rbPublicExternal:
                return Environment.getExternalStoragePublicDirectory(getSubfolderType());
            case R.id.rbInternalCache:
                return requireContext().getCacheDir();
            case R.id.rbExternalCache:
                return requireContext().getExternalCacheDir();
            default:
                return requireContext().getFilesDir();
        }
    }

    private InputStream getSourceInputStream() throws IOException {
        return rgSource.getCheckedRadioButtonId() == R.id.rbRaw ? getResources().openRawResource(
            R.raw.lorem) : requireContext().getAssets().open(getSourceFilename());
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
            startActivity(IntentsUtils.newViewFileIntent(requireContext(), file, mimeType));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(), getString(R.string.main_error_opening_file),
                Toast.LENGTH_SHORT).show();
        }
    }

    private boolean canWriteOnExternalStorage() {
        return hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @SuppressWarnings("SameParameterValue")
    private boolean hasPermission(String permissionName) {
        return ContextCompat.checkSelfPermission(requireContext(), permissionName)
            == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showRationalePermissionDialog();
        } else {
            // IMPORTANT: Call requestPermission method from Fragment class or fragment's
            // onRequestPermissionsResult won't be called.
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RP_WRITE_EXTERNAL);
        }
    }

    private void showRationalePermissionDialog() {
        new AlertDialog.Builder(requireContext()).setMessage(
            R.string.main_permission_required_explanation).setTitle(
            R.string.main_permission_required).setPositiveButton(android.R.string.ok,
            (dialogInterface, i) -> requestPermissions(
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                RP_WRITE_EXTERNAL)).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RP_WRITE_EXTERNAL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                duplicateFile();
            } else {
                // "Don't ask again" checked?
                // preguntar.
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Not checked.
                    Snackbar.make(btnDuplicate, R.string.main_error_permission_required, Snackbar.LENGTH_LONG).show();
                } else {
                    // Checked.
                    Snackbar.make(btnDuplicate, R.string.main_action_permission_required, Snackbar.LENGTH_LONG)
                        .setAction(R.string.main_configure,
                            view -> IntentsUtils.startInstalledAppDetailsActivity(
                                requireActivity()))
                        .show();
                }
            }
        }
    }

}
