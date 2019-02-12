package es.iessaladillo.pedrojoya.pr147.ui.main.v2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import es.iessaladillo.pedrojoya.pr147.ui.likes.LikesFragment;
import es.iessaladillo.pedrojoya.pr147.ui.lorem.LoremFragment;

class MainFragmentAdapterV2 extends FragmentStateAdapter {

    private static final int FRAGMENT_COUNT = 2;

    MainFragmentAdapterV2(FragmentManager fm) {
        super(fm);
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
    public int getItemCount() {
        return FRAGMENT_COUNT;
    }

}
