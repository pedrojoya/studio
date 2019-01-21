package es.iessaladillo.pedrojoya.pr082.data.remote.photo;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageRequest;

class PhotoRequest extends ImageRequest {

    private static final int PHOTO_MAX_WIDTH = 300;
    private static final int PHOTO_MAX_HEIGHT = 300;

    PhotoRequest(String url, Listener<Bitmap> listener, ErrorListener errorListener) {
        super(url, listener, PHOTO_MAX_WIDTH, PHOTO_MAX_HEIGHT, ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.ALPHA_8, errorListener);
    }


}
