package es.iessaladillo.pedrojoya.pr138.ui.main;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import es.iessaladillo.pedrojoya.pr138.R;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private static final String BASE_URL = "https://picsum.photos/400/200?image=";
    private final Random random = new Random();
    private Toolbar toolbar;
    private ImageView imgPhoto;
    private TextView lblVibrant;
    private TextView lblLightVibrant;
    private TextView lblDarkVibrant;
    private TextView lblMuted;
    private TextView lblLightMuted;
    private TextView lblDarkMuted;
    private Palette palette;

    static MainFragment newInstance() {
        return new MainFragment();
    }

    private static void setStatusBarColor(Window window, int color) {
        window.setStatusBarColor(color);
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
        setupViews(requireView());
        // Load initial photo.
        loadPhoto();
    }

    private void setupViews(View view) {
        toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
        imgPhoto = ViewCompat.requireViewById(view, R.id.imgPhoto);
        lblVibrant = ViewCompat.requireViewById(view, R.id.lblVibrant);
        lblLightVibrant = ViewCompat.requireViewById(view, R.id.lblLightVibrant);
        lblDarkVibrant = ViewCompat.requireViewById(view, R.id.lblDarkVibrant);
        lblMuted = ViewCompat.requireViewById(view, R.id.lblMuted);
        lblLightMuted = ViewCompat.requireViewById(view, R.id.lblLightMuted);
        lblDarkMuted = ViewCompat.requireViewById(view, R.id.lblDarkMuted);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        imgPhoto.setOnClickListener(v -> loadPhoto());
        lblVibrant.setOnClickListener(v -> updateToolbar((TextView) v));
        lblLightVibrant.setOnClickListener(v -> updateToolbar((TextView) v));
        lblDarkVibrant.setOnClickListener(v -> updateToolbar((TextView) v));
        lblMuted.setOnClickListener(v -> updateToolbar((TextView) v));
        lblLightMuted.setOnClickListener(v -> updateToolbar((TextView) v));
        lblDarkMuted.setOnClickListener(v -> updateToolbar((TextView) v));
    }

    private void loadPhoto() {
        Picasso.with(requireContext()).load(BASE_URL + random.nextInt(180)).into(imgPhoto,
            new Callback() {
                @Override
                public void onSuccess() {
                    obtainPalette();
                }

                @Override
                public void onError() {
                }
            });
    }

    private void obtainPalette() {
        Bitmap bitmap = ((BitmapDrawable) imgPhoto.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(palette -> {
            this.palette = palette;
            if (palette != null) {
                showSwatchs(palette);
            }
        });
    }

    private void showSwatchs(@NonNull Palette palette) {
        showSwatch(lblVibrant, palette.getVibrantSwatch());
        showSwatch(lblLightVibrant, palette.getLightVibrantSwatch());
        showSwatch(lblDarkVibrant, palette.getDarkVibrantSwatch());
        showSwatch(lblMuted, palette.getMutedSwatch());
        showSwatch(lblLightMuted, palette.getLightMutedSwatch());
        showSwatch(lblDarkMuted, palette.getDarkMutedSwatch());
        updateToolbar(lblMuted);
    }

    private void showSwatch(@NonNull TextView textView, Palette.Swatch swatch) {
        textView.setBackgroundColor(Color.WHITE);
        textView.setTextColor(Color.BLACK);
        if (swatch != null) {
            textView.setBackgroundColor(swatch.getRgb());
            textView.setTextColor(swatch.getBodyTextColor());
        }
    }

    private void updateToolbar(@NonNull TextView textView) {
        if (palette != null) {
            int defaultColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary);
            int backgroundColor = getBackgroundColor(textView, defaultColor);
            toolbar.setBackgroundColor(backgroundColor);
            toolbar.setTitleTextColor(textView.getCurrentTextColor());
            // Default status bar color is the one from the theme.
            int statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark);
            switch (textView.getId()) {
                case R.id.lblVibrant:
                case R.id.lblLightVibrant:
                case R.id.lblDarkVibrant:
                    statusBarColor = palette.getDarkVibrantColor(statusBarColor);
                    break;
                default:
                    statusBarColor = palette.getDarkMutedColor(statusBarColor);
            }
            setStatusBarColor(requireActivity().getWindow(), statusBarColor);
        }
    }

    // Returns background color of the received view or default one.
    private int getBackgroundColor(View view, int defaultColor) {
        int color = defaultColor;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable) color = ((ColorDrawable) background).getColor();
        return color;
    }

}
