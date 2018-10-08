package es.iessaladillo.pedrojoya.pr147.ui.main;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import es.iessaladillo.pedrojoya.pr147.R;
import es.iessaladillo.pedrojoya.pr147.ui.likes.LikesFragment;
import es.iessaladillo.pedrojoya.pr147.ui.lorem.LoremFragment;
import es.iessaladillo.pedrojoya.pr147.base.CachedFragmentPagerAdapter;

class MainActivityAdapter extends CachedFragmentPagerAdapter {

    private static final int FRAGMENT_COUNT = 2;

    private final Context context;
    private final int[] titleResIds = {R.string.lorem_fragment_title, R.string
            .likes_fragment_title};
    private final int[] iconResIds = {R.drawable.ic_share_white_24dp, R.drawable
            .ic_thumb_up_white_24dp};

    public MainActivityAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context.getApplicationContext();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return LoremFragment.newInstance();
        } else {
            return LikesFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(titleResIds[position]);
    }

    @DrawableRes
    public int getPageIcon(int position) {
        return iconResIds[position];
    }

}
