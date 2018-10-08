package es.iessaldillo.pedrojoya.pr160.ui.main;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import es.iessaldillo.pedrojoya.pr160.R;
import es.iessaldillo.pedrojoya.pr160.base.CachedFragmentPagerAdapter;

class MainActivityAdapter extends CachedFragmentPagerAdapter{

    @SuppressWarnings("WeakerAccess")
    public static final int NUMBER_OF_PAGES = 3;

    private final int[] pageHeaders = {R.drawable.photo0, R.drawable.photo1, R.drawable.photo2};
    private final Context context;

    public MainActivityAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context.getApplicationContext();
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(R.string.main_activity_tabTitle, position);
    }

    @DrawableRes
    public int getPageHeader(int position) {
        return pageHeaders[position];
    }

}
