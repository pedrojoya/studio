package pedrojoya.iessaladillo.es.pr105;


import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class TabLayoutFragment extends Fragment {

    private static final String ARG_PARAM1 = "opcion";

    private String mOpcion;
    private HideShowNestedScrollListener mScrollListener;
    private ViewPager viewPager;
    private FloatingActionButton fabAccion;
    private ViewPagerAdapter viewPagerAdapter;

    public static TabLayoutFragment newInstance(String param1) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public TabLayoutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOpcion = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tablayout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            initVistas(getView());
        }
    }

    private void initVistas(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity actividad = ((AppCompatActivity) getActivity());
        actividad.setSupportActionBar(toolbar);
        actividad.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actividad.getSupportActionBar().setHomeButtonEnabled(true);
        actividad.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        if (viewPager != null) {
            configViewPager(viewPager);
        }
        fabAccion = (FloatingActionButton) view.findViewById(R.id.fabAccion);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(viewPagerAdapter.getPageIcon(i));
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ViewCompat.animate(fabAccion).scaleX(1).scaleY(1);
                } else {
                    ViewCompat.animate(fabAccion).scaleX(0).scaleY(0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void configViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(Tab1Fragment.newInstance("Alumnos"), "Alumnos", R.drawable.ic_action_face_white);
        viewPagerAdapter.addFragment(Tab2Fragment.newInstance("Lorem"), "Lorem", R.drawable.ic_discuss);
        viewPagerAdapter.addFragment(Tab2Fragment.newInstance("Tab 3"), "Tab 3", R.drawable.ic_done);
//        viewPagerAdapter.addFragment(Tab2Fragment.newInstance("Tab 4"), "Tab 4", R.drawable.ic_done);
//        viewPagerAdapter.addFragment(Tab2Fragment.newInstance("Tab 5"), "Tab 5", R.drawable.ic_done);
//        viewPagerAdapter.addFragment(Tab2Fragment.newInstance("Tab 6"), "Tab 6", R.drawable.ic_done);
//        viewPagerAdapter.addFragment(Tab2Fragment.newInstance("Tab 7"), "Tab 7", R.drawable.ic_done);
        viewPager.setAdapter(viewPagerAdapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        private final List<Integer> mFragmentIcons = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title, @DrawableRes int resIdIcon) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
            mFragmentIcons.add(Integer.valueOf(resIdIcon));
        }

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
            return "";
            //return mFragmentTitles.get(position);
        }

        @DrawableRes
        public int getPageIcon(int position) {
            return mFragmentIcons.get(position).intValue();
        }

    }

}
