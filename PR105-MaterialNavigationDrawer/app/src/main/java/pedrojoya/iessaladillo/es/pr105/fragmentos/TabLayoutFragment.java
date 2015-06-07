package pedrojoya.iessaladillo.es.pr105.fragmentos;


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

import pedrojoya.iessaladillo.es.pr105.R;
import pedrojoya.iessaladillo.es.pr105.utils.HideShowNestedScrollListener;


public class TabLayoutFragment extends Fragment {

    private static final String ARG_TITULO = "titulo";

    private HideShowNestedScrollListener mScrollListener;
    private FloatingActionButton fabAccion;

    private String mTitulo;

    // Retorna una nueva intancia del fragmento.
    public static TabLayoutFragment newInstance(String titulo) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITULO, titulo);
        fragment.setArguments(args);
        return fragment;
    }

    public TabLayoutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se obtienen los argumentos.
        if (getArguments() != null) {
            mTitulo = getArguments().getString(ARG_TITULO);
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

    // Obtiene e inicializa las vistas.
    private void initVistas(View view) {
        fabAccion = (FloatingActionButton) view.findViewById(R.id.fabAccion);
        configToolbar(view);
        configViewPager(view);
    }

    // Configura la toolbar.
    private void configToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity actividad = ((AppCompatActivity) getActivity());
        actividad.setSupportActionBar(toolbar);
        actividad.setTitle(mTitulo);
        if (actividad.getSupportActionBar() != null) {
            actividad.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            actividad.getSupportActionBar().setHomeButtonEnabled(true);
            actividad.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    // Configura el ViewPager.
    private void configViewPager(View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(Tab1Fragment.newInstance(),
                getString(R.string.alumnos), R.drawable.ic_action_face_white);
        viewPagerAdapter.addFragment(Tab2Fragment.newInstance(),
                getString(R.string.textinputlayout), R.drawable.ic_discuss);
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        /**** Para mostrar iconos en las tabs.
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(viewPagerAdapter.getPageIcon(i));
        }
        ****/
        // Se añade un listener para poder mostrar / ocultar el FAB dependiendo
        // del fragmento que se muestre en el ViewPager.
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
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

    // Adaptador para el ViewPager.
    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        private final List<Integer> mFragmentIcons = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Añade un fragmento al adaptador. Recibe el fragmento, el título
        // para la tab y el icono para la tab.
        public void addFragment(Fragment fragment, String title,
                                @DrawableRes int resIdIcon) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
            mFragmentIcons.add(resIdIcon);
        }

        // Retorna el fragmento correspondiente a la posición recibida.
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
            return " " + mFragmentTitles.get(position);
        }

        // Retorna el resId del icono asociado a una determinada página.
        @DrawableRes
        public int getPageIcon(int position) {
            return mFragmentIcons.get(position);
        }

    }

}
