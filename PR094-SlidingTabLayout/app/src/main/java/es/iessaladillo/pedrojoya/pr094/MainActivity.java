package es.iessaladillo.pedrojoya.pr094;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.common.view.SlidingTabLayout;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {

        private ViewPager vpPaginador;
        private SlidingTabLayout stTabs;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            vpPaginador = (ViewPager) rootView.findViewById(R.id.vpPaginador);
            vpPaginador.setAdapter(new PaginasAdapter(getChildFragmentManager()));
            stTabs = (SlidingTabLayout) rootView.findViewById(R.id.stTabs);
            stTabs.setViewPager(vpPaginador);
            stTabs.setSelectedIndicatorColors(Color.RED);
            stTabs.setDividerColors(Color.GREEN);
            return rootView;
        }
    }

    // Adaptador para el paginador de fragmentos.
    public class PaginasAdapter extends FragmentPagerAdapter {

        // Constantes.
        private int NUM_PAGINAS = 5;

        // Constructor. Recibe el gestor de fragmentos.
        public PaginasAdapter(FragmentManager fm) {
            super(fm);
        }

        // Retorna el fragmento correspondiente a una página. Recibe el número
        // de página.
        @Override
        public Fragment getItem(int position) {
            // Se crea el fragmento y se le pasa como argumento el número de
            // pestaña para que lo escriba en su TextView.
            Fragment frgPagina = new PaginaFragment();
            Bundle parametros = new Bundle();
            parametros.putInt(PaginaFragment.PAR_NUM_PAGINA, position + 1);
            frgPagina.setArguments(parametros);
            return frgPagina;
        }

        // Retorna el número de páginas.
        @Override
        public int getCount() {
            return NUM_PAGINAS;
        }

        // Retorna el título de una página. Recibe el número de página.
        @Override
        public CharSequence getPageTitle(int position) {
            return "Pestaña" + (position + 1);
        }
    }

    public class PaginaFragment extends Fragment {

        public static final String PAR_NUM_PAGINA = "numPagina";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Se infla el layout del fragmento y se muestra el número de página en
            // el TextView.
            View v = inflater.inflate(R.layout.fragment_pagina, container, false);
            TextView lblPagina = (TextView) v.findViewById(R.id.lblPagina);
            lblPagina.setText(this.getArguments().getInt(PAR_NUM_PAGINA) + "");
            return v;
        }

    }
}
