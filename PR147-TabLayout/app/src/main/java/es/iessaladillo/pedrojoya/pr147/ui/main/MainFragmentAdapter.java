package es.iessaladillo.pedrojoya.pr147.ui.main;

import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import es.iessaladillo.pedrojoya.pr147.R;
import es.iessaladillo.pedrojoya.pr147.ui.likes.LikesFragment;
import es.iessaladillo.pedrojoya.pr147.ui.lorem.LoremFragment;

class MainFragmentAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENT_COUNT = 2;

    private final Context context;
    private final int[] titleResIds = {R.string.lorem_title, R.string
            .likes_title};
    private final int[] iconResIds = {R.drawable.ic_share_white_24dp, R.drawable
            .ic_thumb_up_white_24dp};

    MainFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context.getApplicationContext();
    }

    @Override
    @NonNull
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
    int getPageIcon(int position) {
        return iconResIds[position];
    }

}
