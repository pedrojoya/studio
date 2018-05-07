package es.iessaladillo.pedrojoya.pr030;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.pr030.utils.FileUtils;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements PickOrCaptureDialogFragment.Listener {

    private static final int RC_CAPTURE = 0;
    private static final int RC_SELECT = 1;

    private static final String PREF_PHOTO_PATH = "PREF_PHOTO_PATH";

    private static final int OPTION_PICK = 0;
    private static final String STATE_PHOTO_PATH = "STATE_PHOTO_PATH";
    private static final String STATE_FILENAME = "STATE_FILENAME";
    private static final String PHOTO_NAME = "mifoto.jpg";

    private String photoPath; // where to save captured photo.
    private String filename; // filename for scaled private version of the photo.

    private ImageView imgPhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        imgPhoto = ActivityCompat.requireViewById(this, R.id.imgPhoto);
        showPhotoFromPreferences();
    }

    private void showPhotoFromPreferences() {
        String pathFoto = getPreferences(MODE_PRIVATE).getString(PREF_PHOTO_PATH, "");
        if (!TextUtils.isEmpty(pathFoto)) {
            imgPhoto.setImageURI(Uri.fromFile(new File(pathFoto)));
        }
    }

    // Don't use ViewModel, because it will be destroyed if we change device orientation
    // being in the capture photo app activity.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_PHOTO_PATH, photoPath);
        outState.putString(STATE_FILENAME, filename);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        photoPath = savedInstanceState.getString(STATE_PHOTO_PATH);
        filename = savedInstanceState.getString(STATE_FILENAME);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuPhoto) {
            (new PickOrCaptureDialogFragment()).show(this.getSupportFragmentManager(),
                    PickOrCaptureDialogFragment.class.getSimpleName());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }

    @SuppressWarnings("SameParameterValue")
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void select(String photoName) {
        filename = photoName;
        // Select photo from gallery.
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        // Se deshabilita temporalmente el sensor de orientación.
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        startActivityForResult(i, RC_SELECT);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationaleForReadExternalStorage(final PermissionRequest request) {
        new AlertDialog.Builder(this).setMessage(R.string.permission_readexternalstorage_rationale)
                .setPositiveButton(R.string.permission_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.permission_reject, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showDeniedForReadExternalStorage() {
        Toast.makeText(this, R.string.permission_unable, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showNeverAskForReadExternalStorage() {
        Toast.makeText(this, R.string.permission_readexternalstorage_rationale, Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("SameParameterValue")
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void capture(String photoName) {
        filename = photoName;
        // Capture photo.
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null) {
            // Create temp file with date and time in his name.
            File photoFile = FileUtils.createPictureFile(this,
                    "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss",
                            Locale.getDefault()).format(new Date()) + "_.jpg",
                    true, true);
            if (photoFile != null) {
                // Save photo file path for later.
                photoPath = photoFile.getAbsolutePath();
                // Get uri for file from fileprovider (needed for API >= 25). Auhority must match
                // with the one declared in manifest.
                Uri fotoURI = FileProvider.getUriForFile(this.getApplicationContext(),
                        BuildConfig.APPLICATION_ID + ".provider", photoFile);
                // We tell camera app whre to save the captured image.
                i.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
                //i.setData(fotoURI);
                i.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                // Se deshabilita temporalmente el sensor de orientación.
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                startActivityForResult(i, RC_CAPTURE);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_CAPTURE:
                    addPhotoToGallery(photoPath);
                    showScaledCopy(photoPath);
                    break;
                case RC_SELECT:
                    // Get real path of photo file from the uri retorned by Gallery.
                    photoPath = getRealPath(intent.getData());
                    if (!TextUtils.isEmpty(photoPath)) {
                        showScaledCopy(photoPath);
                    }
                    break;
            }
        }
        // Se vuelve a habilitar el sensor de orientación.
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    private String getRealPath(Uri galleryUri) {
        String path = "";
        // Query gallery's content provider for real path of photo file.
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = getContentResolver().query(galleryUri, filePath, null, null, null);
        if (c != null && c.moveToFirst()) {
            int columnIndex = c.getColumnIndex(filePath[0]);
            path = c.getString(columnIndex);
            c.close();
        }
        return path;
    }

    private void addPhotoToGallery(String photoPath) {
        String[] files = {photoPath};
        String[] mimes = {"image/*"};
        MediaScannerConnection.scanFile(this, files, mimes,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d(getString(R.string.app_name), "Added to gallery");
                    }
                });
/*
        // Ask media scanner to scan the photo file.
        Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // Get uri for photo file from fileprovider.
        Uri uri = FileProvider.getUriForFile(this.getApplicationContext(),
                BuildConfig.APPLICATION_ID + ".provider", new File(photoPath));
        i.setData(uri);
        i.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        this.sendBroadcast(i);
*/
    }

    private void showScaledCopy(String pathFoto) {
        // Create a scaled copy of the photo and show it in ImageView.
        CreateScaleCopyAndShowTask task = new CreateScaleCopyAndShowTask(this,
                getPreferences(MODE_PRIVATE),
                FileUtils.createPictureFile(this, filename, true, false),
                getResources().getDimensionPixelSize(R.dimen.activity_main_imgPhoto_width),
                getResources().getDimensionPixelSize(R.dimen.activity_main_imgPhoto_height));
        task.execute(pathFoto);
    }

    // Option selected from PickOrCaptureDialogFragment.
    @Override
    public void onItemClick(DialogFragment dialog, int which) {
        if (which == OPTION_PICK) {
            MainActivityPermissionsDispatcher.selectWithPermissionCheck(this, PHOTO_NAME);

        } else {
            MainActivityPermissionsDispatcher.captureWithPermissionCheck(this, PHOTO_NAME);
        }
    }

    private static class CreateScaleCopyAndShowTask extends AsyncTask<String, Void, Bitmap> {

        final WeakReference<MainActivity> mainActivityWeakReference;
        private final SharedPreferences pref;
        private final File file;
        private final int viewWidth;
        private final int viewHeight;

        CreateScaleCopyAndShowTask(@NonNull MainActivity mainActivity,
                @NonNull SharedPreferences pref, File file, int viewWidth, int viewHeight) {
            mainActivityWeakReference = new WeakReference<>(mainActivity);
            this.pref = pref;
            this.file = file;
            this.viewWidth = viewWidth;
            this.viewHeight = viewHeight;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return scale(params[0], viewWidth, viewHeight);
        }

        // Una vez finalizado el hilo de trabajo. Se ejecuta en el hilo
        // principal. Recibe el Bitmap de la foto escalada (o null si error).
        @Override
        protected void onPostExecute(Bitmap bitmapFoto) {
            if (bitmapFoto != null) {
                saveScaledPhoto(bitmapFoto);
                showPhoto(bitmapFoto);
            }
        }

        private void showPhoto(Bitmap bitmapFoto) {
            MainActivity mainActivity = mainActivityWeakReference.get();
            if (mainActivity != null) {
                mainActivity.imgPhoto.setImageBitmap(bitmapFoto);
            }
        }

        private Bitmap scale(String photoPath, int viewWidth, int viewHeight) {
            try {
                // Calculate the right scale factor.
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(photoPath, options);
                int width = options.outWidth;
                int height = options.outHeight;
                int scaleFactor = Math.min(width / viewWidth, height / viewHeight);
                // Scale image with the scale factor.
                options.inJustDecodeBounds = false;
                options.inSampleSize = scaleFactor;
                return BitmapFactory.decodeFile(photoPath, options);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private void saveScaledPhoto(Bitmap bitmap) {
            if (file != null) {
                if (saveBitmapInFile(bitmap, file)) {
                    saveInPreferences(file.getAbsolutePath());
                }
            }
        }

        private boolean saveBitmapInFile(Bitmap bitmap, File file) {
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        private void saveInPreferences(String scaledPhotoPath) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(PREF_PHOTO_PATH, scaledPhotoPath);
            editor.apply();
        }

    }

}
