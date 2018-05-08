package es.iessaladillo.pedrojoya.pr153.tools;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

@SuppressWarnings({"unused", "WeakerAccess"})
public class DataBindingTools {

    private DataBindingTools() {
    }

    @BindingAdapter(value = {"imageUrl", "placeholder", "error"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String url, Drawable drawablePlaceholder,
            Drawable drawableError) {
        if (!TextUtils.isEmpty(url)) {
            RequestCreator peticion = Picasso.with(imageView.getContext()).load(url);
            if (drawablePlaceholder != null) {
                peticion.placeholder(drawablePlaceholder);
            }
            if (drawableError != null) {
                peticion.error(drawableError);
            }
            peticion.into(imageView);
        }
    }

    @BindingAdapter("errorMessage")
    public static void showErrorMessage(ViewGroup viewGroup, String message) {
        if (viewGroup != null && message != null) {
            Snackbar.make(viewGroup, message,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @BindingAdapter(value = {"app:asActionBarOf", "app:displayHomeAsUp"}, requireAll = false)
    public static void setAsActionBar(Toolbar toolbar, Context context,
            Boolean displayHomeAsUp) {
        if (context != null) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.setSupportActionBar(toolbar);
            if (displayHomeAsUp != null && displayHomeAsUp &&
                    appCompatActivity.getSupportActionBar() != null) {
                appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }


}
