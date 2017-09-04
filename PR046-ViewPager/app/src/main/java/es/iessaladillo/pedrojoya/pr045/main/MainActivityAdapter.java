package es.iessaladillo.pedrojoya.pr045.main;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr045.R;

class MainActivityAdapter extends PagerAdapter {

    private static final int NUMBER_OF_PAGES = 5;

    private final LayoutInflater mLayoutInflater;

    public MainActivityAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = mLayoutInflater.inflate(R.layout.activity_main_page, collection, false);
        setupPage(view, position);
        collection.addView(view, 0);
        return view;
    }

    private void setupPage(View view, int position) {
        TextView lblPage = view.findViewById(R.id.lblPage);
        Button btnShow = view.findViewById(R.id.btnShow);

        lblPage.setText(String.valueOf(position));
        btnShow.setOnClickListener(v -> show(view, position));
    }

    private void show(View view, int position) {
        Toast.makeText(view.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public void startUpdate(ViewGroup arg0) {
    }

    @Override
    public void finishUpdate(ViewGroup arg0) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader arg1) {
    }

}
