package es.iessaladillo.pedrojoya.pr220.utils;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

@SuppressWarnings({"unused", "WeakerAccess"})
public class DataBindingTools {

    private DataBindingTools() {
    }

    @BindingAdapter(value = {"app:imageUrl", "app:placeholder", "app:error"}, requireAll = false)
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

    @BindingAdapter("app:initialFrom")
    public static void setInitialFrom(ImageView imageView, @NonNull String from) {
        if (!TextUtils.isEmpty(from)) {
            TextDrawable.IBuilder builder = TextDrawable.builder().beginConfig().width(100).height(
                    100).toUpperCase().endConfig().round();
            imageView.setImageDrawable(
                    builder.build(from.substring(0, 1), ColorGenerator.MATERIAL.getColor(from)));
        }
    }

    @BindingAdapter("app:onRefresh")
    public static void setOnRefreshListener(SwipeRefreshLayout swipeRefreshLayout,
            SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    @BindingAdapter("app:showLoading")
    public static void setOnLoading(SwipeRefreshLayout swipeRefreshLayout,
            Boolean loading) {
        if (swipeRefreshLayout != null && loading != null) {
            swipeRefreshLayout.setRefreshing(loading);
        }
    }


    @SuppressWarnings("ConstantConditions")
    @BindingAdapter(value = {"app:asActionBar", "app:displayHomeAsUp"}, requireAll = false)
    public static void setAsActionBar(Toolbar toolbar, AppCompatActivity appCompatActivity,
            Boolean displayHomeAsUp) {
        appCompatActivity.setSupportActionBar(toolbar);
        if (displayHomeAsUp != null && displayHomeAsUp) {
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @BindingAdapter(value = {"app:errorEnabled", "app:error"})
    public static void setErrorEnabled(TextInputLayout til, boolean errorEnabled, String error) {
        til.setErrorEnabled(errorEnabled);
        til.setError(errorEnabled ? error : "");
    }

    @BindingAdapter(value = {"app:colorTint", "app:tintOnFocusOn"}, requireAll = false)
    public static void setColorTint(ImageView imageView, @ColorInt int color, EditText editText) {
        if (editText != null) {
            View.OnFocusChangeListener focusListener = editText.getOnFocusChangeListener();
            editText.setOnFocusChangeListener((v, hasFocus) -> {
                // In order no to override the already set OnFocusChangeListener.
                if (focusListener != null) {
                    focusListener.onFocusChange(v, hasFocus);
                }
                Drawable drawable = DrawableCompat.wrap(imageView.getDrawable());
                if (hasFocus) {
                    DrawableCompat.setTint(drawable, color);
                } else
                    {
                    DrawableCompat.setTint(drawable, Color.BLACK);
                }
            });
        }
    }

    @BindingAdapter("app:showStringResId")
    public static void onStringResIdAppeared(ViewGroup viewGroup, ViewMessage viewMessage) {
        if (viewMessage != null && !viewMessage.askIsShownAndMarkAsShown()) {
            Toast.makeText(viewGroup.getContext(), viewMessage.getMessageResId(), Toast.LENGTH_SHORT).show();
        }
    }

}
