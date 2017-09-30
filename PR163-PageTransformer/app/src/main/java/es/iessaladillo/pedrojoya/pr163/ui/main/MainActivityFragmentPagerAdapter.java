package es.iessaladillo.pedrojoya.pr163.ui.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import es.iessaladillo.pedrojoya.pr163.R;

class MainActivityFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 3;
    private final int[] mColors = {R.color.color1, R.color.color2, R.color.color3};
    private final Context context;

    public MainActivityFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return MainActivityPageFragment.newInstance(position + 1, mColors[position]);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(R.string.main_activity_page_title, position + 1);
    }
}
