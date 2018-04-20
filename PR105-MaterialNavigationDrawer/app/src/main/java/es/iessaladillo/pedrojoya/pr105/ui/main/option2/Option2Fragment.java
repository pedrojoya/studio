package es.iessaladillo.pedrojoya.pr105.ui.main.option2;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.base.OnToolbarAvailableListener;


@SuppressWarnings("FieldCanBeLocal")
public class Option2Fragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton fab;
    private OnToolbarAvailableListener listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_option2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
    }

    private void initViews(View view) {
        setupToolbar(view);
        setupFab(view);
        setupViewPager(view);
    }

    private void setupToolbar(View view) {
        listener.onToolbarAvailable(ViewCompat.requireViewById(view, R.id.toolbar),
                getString(R.string.activity_main_option2));
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            listener = (OnToolbarAvailableListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.toString()
                    + " debe implementar la interfaz OnToolbarAvailableListener");
        }
    }

    private void setupFab(View view) {
        fab = ViewCompat.requireViewById(view, R.id.fab);
    }

    private void setupViewPager(View view) {
        viewPager = ViewCompat.requireViewById(view, R.id.viewPager);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new Option2Tab1Fragment(),
                getString(R.string.option2_fragment_students), R.drawable.ic_face_white_24dp)
                .addFragment(new Option2Tab2Fragment(),
                        getString(R.string.option2_fragment_data),
                        R.drawable.ic_message_white_24dp);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = ViewCompat.requireViewById(view, R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        // Show icons in tabs.
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            try {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null) {
                    tab.setIcon(viewPagerAdapter.getPageIcon(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
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

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        // Retorna el número de fragmentos del adaptador.
        @Override
        public int getCount() {
            return mFragments.size();
        }

        // Retorna el título asociado a una determinada página.
        @Override
        public CharSequence getPageTitle(int position) {
            return "  " + mFragmentTitles.get(position);
        }

        // Retorna el resId del icono asociado a una determinada página.
        @DrawableRes
        int getPageIcon(int position) {
            return mFragmentIcons.get(position);
        }

    }

}
