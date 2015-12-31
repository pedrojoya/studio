package es.iessaladillo.pedrojoya.pr163;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final ViewPager.PageTransformer[] TRANSFORMACIONES =
            new ViewPager.PageTransformer[] {
                new UpTransformer(),
                new RotateTransformer(),
                new ScaleUpTransformer()
    };

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupViewPager();
        setupSpinner();
    }

    // Configura la toolbar.
    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // No mostrará título (en su lugar mostrará el spinner).
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    // Configura el ViewPager.
    private void setupViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(new PaginasAdapter(getSupportFragmentManager()));
    }

    // Configura el spinner.
    private void setupSpinner() {
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Se crea el adaptador a partir de un recurso de array de cadenas.
        // Es importante usar el contexto de la toolbar tematizado y no el
        // contexto habitual de la actividad, para que el spinner se muestre
        // correctamente en la toolbar.
        ToolbarSpinnerAdapter adaptador = new ToolbarSpinnerAdapter(
                getSupportActionBar().getThemedContext(),
                new ArrayList<String>(Arrays.asList(
                        getResources().getStringArray(R.array.transformaciones))));
        spinner.setAdapter(adaptador);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view,
                                       final int position, final long id) {
                // Cuando se selecciona un elemento en el spinner, se establece
                // la transformación correspondiente en el viewpager.
                mViewPager.setPageTransformer(true, TRANSFORMACIONES[position]);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                // Si no se ha seleccionado ningún elemento en el spinner
                // (inicialmente), se establece la primera transforamción en el
                // viewpager.
                mViewPager.setPageTransformer(true, TRANSFORMACIONES[0]);
            }
        });
    }

    public static class PaginaFragment extends Fragment {

        private static final String ARG_VALOR = "arg_valor";
        private static final String ARG_COLOR = "arg_color";

        public PaginaFragment() {
        }

        public static PaginaFragment newInstance(int valor, int colorResId) {
            PaginaFragment fragment = new PaginaFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_VALOR, valor);
            args.putInt(ARG_COLOR, colorResId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format,
                    getArguments().getInt(ARG_VALOR)));
            rootView.setBackgroundColor(
                    getResources().getColor(getArguments().getInt(ARG_COLOR)));
            return rootView;
        }
    }

    // Adaptador de paginas de ViewPager.
    public class PaginasAdapter extends FragmentPagerAdapter {

        private static final int NUM_PAGINAS = 3;
        private final int[] mColores = {R.color.color1, R.color.color2, R.color.color3};

        public PaginasAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Se retorna un nuevo fragmento inicializado con el número y
            // el color.
            return PaginaFragment.newInstance(position + 1, mColores[position]);
        }

        @Override
        public int getCount() {
            return NUM_PAGINAS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(R.string.titulo_pagina, position+1);
        }
    }

}
