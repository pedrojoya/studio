package es.iessaladillo.pedrojoya.pr147.main;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import es.iessaladillo.pedrojoya.pr147.R;
import es.iessaladillo.pedrojoya.pr147.utils.CachedFragmentPagerAdapter;

class MainActivityAdapter extends CachedFragmentPagerAdapter {

    private static final int FRAGMENT_COUNT = 2;

    private final Context mContext;
    private final int[] mTitleResIds = {R.string.lorem_fragment_title, R.string
            .likes_fragment_title};
    private final int[] mIconResIds = {R.drawable.ic_share_white_24dp, R.drawable
            .ic_thumb_up_white_24dp};

    public MainActivityAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return LoremFragment.newInstance();
        }
        return LikesFragment.newInstance();
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(mTitleResIds[position]);
    }

    @DrawableRes
    public int getPageIcon(int position) {
        return mIconResIds[position];
    }

}
