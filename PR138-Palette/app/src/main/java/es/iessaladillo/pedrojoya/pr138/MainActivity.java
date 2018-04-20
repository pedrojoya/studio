package es.iessaladillo.pedrojoya.pr138;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity {

    private static final String PHOTO_BASE_URL = "http://lorempixel.com/400/200/abstract/";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;
    @BindView(R.id.lblVibrant)
    TextView lblVibrant;
    @BindView(R.id.lblLightVibrant)
    TextView lblLightVibrant;
    @BindView(R.id.lblDarkVibrant)
    TextView lblDarkVibrant;
    @BindView(R.id.lblMuted)
    TextView lblMuted;
    @BindView(R.id.lblLightMuted)
    TextView lblLightMuted;
    @BindView(R.id.lblDarkMuted)
    TextView lblDarkMuted;

    private int mCount = 0;
    private Palette mPalette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        loadPhoto();
    }

    @OnClick(R.id.imgPhoto)
    public void loadPhoto() {
        Picasso.with(this).load(PHOTO_BASE_URL + (mCount % 10 + 1) + "/")
                .into(imgPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        obtainPalette();
                        mCount++;
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    private void obtainPalette() {
        Bitmap bitmap = ((BitmapDrawable) imgPhoto.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(palette -> {
            mPalette = palette;
            showSwatchs(palette);
        });
    }

    private void showSwatchs(Palette palette) {
        showSwatch(lblVibrant, palette.getVibrantSwatch());
        showSwatch(lblLightVibrant, palette.getLightVibrantSwatch());
        showSwatch(lblDarkVibrant, palette.getDarkVibrantSwatch());
        showSwatch(lblMuted, palette.getMutedSwatch());
        showSwatch(lblLightMuted, palette.getLightMutedSwatch());
        showSwatch(lblDarkMuted, palette.getDarkMutedSwatch());
        updateToolbar(lblMuted);
    }

    private void showSwatch(TextView textView, Palette.Swatch swatch) {
        textView.setBackgroundColor(Color.WHITE);
        textView.setTextColor(Color.BLACK);
        if (swatch != null) {
            textView.setBackgroundColor(swatch.getRgb());
            textView.setTextColor(swatch.getBodyTextColor());
        }
    }

    @OnClick({R.id.lblVibrant, R.id.lblLightVibrant, R.id.lblDarkVibrant,
            R.id.lblMuted, R.id.lblLightMuted, R.id.lblDarkMuted})
    public void updateToolbar(TextView textView) {
        if (mPalette != null) {
            int defaultColor = ContextCompat.getColor(this, R.color.colorPrimary);
            int backgroundColor = getBackgroundColor(textView, defaultColor);
            toolbar.setBackgroundColor(backgroundColor);
            toolbar.setTitleTextColor(textView.getCurrentTextColor());
            // Default status bar color is the one from the theme.
            int statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            switch (textView.getId()) {
                case R.id.lblVibrant:
                case R.id.lblLightVibrant:
                case R.id.lblDarkVibrant:
                    statusBarColor = mPalette.getDarkVibrantColor(statusBarColor);
                    break;
                default:
                    statusBarColor = mPalette.getDarkMutedColor(statusBarColor);
            }
            setStatusBarColor(getWindow(), statusBarColor);
        }
    }

    // Returns background color of the received view or default one.
    private int getBackgroundColor(View view, int defaultColor) {
        int color = defaultColor;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        return color;
    }

    private static void setStatusBarColor(Window window, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }
}
