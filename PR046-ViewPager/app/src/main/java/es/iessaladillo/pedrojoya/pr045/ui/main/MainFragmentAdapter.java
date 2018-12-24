package es.iessaladillo.pedrojoya.pr045.ui.main;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import es.iessaladillo.pedrojoya.pr045.R;

class MainFragmentAdapter extends PagerAdapter {

    private static final int NUMBER_OF_PAGES = 5;

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        View view = LayoutInflater.from(collection.getContext()).inflate(R.layout
                .fragment_main_page,
            collection, false);
        setupPage(view, position);
        collection.addView(view, 0);
        return view;
    }

    private void setupPage(View view, int position) {
        TextView lblPage = ViewCompat.requireViewById(view, R.id.lblPage);
        Button btnShow = ViewCompat.requireViewById(view, R.id.btnShow);

        lblPage.setText(String.valueOf(position));
        btnShow.setOnClickListener(v -> show(view, position));
    }

    private void show(View view, int position) {
        Snackbar.make(view, view.getContext().getString(R.string.main_message, position),
            Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @Override
    public void startUpdate(@NonNull ViewGroup arg0) {
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup arg0) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader arg1) {
    }

}
