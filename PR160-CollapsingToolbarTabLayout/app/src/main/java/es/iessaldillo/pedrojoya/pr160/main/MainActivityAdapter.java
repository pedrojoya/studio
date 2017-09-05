package es.iessaldillo.pedrojoya.pr160.main;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import es.iessaldillo.pedrojoya.pr160.R;
import es.iessaldillo.pedrojoya.pr160.utils.CachedFragmentPagerAdapter;

class MainActivityAdapter extends CachedFragmentPagerAdapter{

    @SuppressWarnings("WeakerAccess")
    public static final int NUMBER_OF_PAGES = 3;

    private final int[] pageHeaders = {R.drawable.photo0, R.drawable.photo1, R.drawable.photo2};
    private final Context mContext;

    public MainActivityAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
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
        return mContext.getString(R.string.main_activity_tabTitle, position);
    }

    @DrawableRes
    public int getPageHeader(int position) {
        return pageHeaders[position];
    }

}
