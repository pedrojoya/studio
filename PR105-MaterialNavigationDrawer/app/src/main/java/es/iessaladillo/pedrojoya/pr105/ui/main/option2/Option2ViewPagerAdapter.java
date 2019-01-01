package es.iessaladillo.pedrojoya.pr105.ui.main.option2;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class Option2ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();
    private final List<Integer> mFragmentIcons = new ArrayList<>();

    Option2ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    Option2ViewPagerAdapter addFragment(Fragment fragment, String title, @DrawableRes int resIdIcon) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
        mFragmentIcons.add(resIdIcon);
        return this;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "  " + mFragmentTitles.get(position);
    }

    @DrawableRes
    int getPageIcon(int position) {
        return mFragmentIcons.get(position);
    }

}
