package es.iessaladillo.pedrojoya.pr105.ui.main.option2;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.base.OnFragmentShownListener;
import es.iessaladillo.pedrojoya.pr105.base.OnToolbarAvailableListener;
import es.iessaladillo.pedrojoya.pr105.ui.main.option2.tab1.Option2Tab1Fragment;
import es.iessaladillo.pedrojoya.pr105.ui.main.option2.tab2.Option2Tab2Fragment;


public class Option2Fragment extends Fragment {

    private OnToolbarAvailableListener onToolbarAvailableListener;
    private OnFragmentShownListener onFragmentShownListener;

    private FloatingActionButton fab;

    public static Option2Fragment newInstance() {
        return new Option2Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_option2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(requireView());
        // In order to update the checked menuItem when coming from backstack.
        onFragmentShownListener.onFragmentShown(R.id.mnuOption2);
    }

    private void setupViews(View view) {
        setupToolbar(view);
        setupFab(view);
        setupViewPager(view);
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = ViewCompat.requireViewById(view, R.id.toolbar);

        toolbar.setTitle(getString(R.string.activity_main_option2));
        onToolbarAvailableListener.onToolbarAvailable(toolbar);
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        try {
            onToolbarAvailableListener = (OnToolbarAvailableListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(
                activity.toString() + " debe implementar la interfaz OnToolbarAvailableListener");
        }
        try {
            onFragmentShownListener = (OnFragmentShownListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                activity.toString() + " debe implementar la interfaz OnFragmentShownListener");
        }
    }

    private void setupFab(View view) {
        fab = ViewCompat.requireViewById(view, R.id.fab);
    }

    private void setupViewPager(View view) {
        ViewPager viewPager = ViewCompat.requireViewById(view, R.id.viewPager);
        TabLayout tabLayout = ViewCompat.requireViewById(view, R.id.tabLayout);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new Option2Tab1Fragment(),
            getString(R.string.option2_fragment_students), R.drawable.ic_face_white_24dp)
            .addFragment(new Option2Tab2Fragment(), getString(R.string.option2_fragment_data),
                R.drawable.ic_message_white_24dp);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        // Show icons in tabs.
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setIcon(viewPagerAdapter.getPageIcon(i));
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        private final List<Integer> mFragmentIcons = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        ViewPagerAdapter addFragment(Fragment fragment, String title, @DrawableRes int resIdIcon) {
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

}
