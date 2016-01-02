package es.iessaldillo.pedrojoya.pr160;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import es.iessaldillo.pedrojoya.pr160.utils.CachedFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGINAS = 3;
    private PaginasAdapter mAdaptador;
    private TabLayout tabLayout;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        setupToolbar();
        setupViewPager();
        setupFab();
    }

    private void initVistas() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    // Configura la toolbar.
    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // El título debe aparecer en la toolbar, porque en el CollapsingToolbarLayout
            // se mostraría encima del TabLayout.
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
    }

    // Configura el viewPager.
    private void setupViewPager() {
        // Se crea y asigna el adaptador de páginas al viewPager.
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ArrayList<String> titulosPaginas = new ArrayList<>();
        for (int i = 0; i < NUM_PAGINAS; i++) {
            titulosPaginas.add(getString(R.string.tab, i + 1));
        }
        mAdaptador = new PaginasAdapter(getSupportFragmentManager(), titulosPaginas);
        viewPager.setAdapter(mAdaptador);
        viewPager.setOffscreenPageLimit(2);
        // Se configura el tabLayout para que trabaje con el viewPager.
        tabLayout.setupWithViewPager(viewPager);
        // Se añade un listener para que al cambiar la página se cambia la
        // imagen de cabecera.
        final ImageView imgCabecera = (ImageView) findViewById(R.id.imgCabecera);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            final int[] imagenes = {R.drawable.foto0, R.drawable.foto1, R.drawable.foto2};

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // Se establece la nueva imagen de cabecera y se fuerza el repintado.
                imgCabecera.setImageResource(imagenes[position]);
                imgCabecera.invalidate();
                // Se muestra u oculta el FAB.
                if (position == 1) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    // Configura el FAB.
    private void setupFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se llama al fabOnClick del fragmento actual.
                PaginaFragment frg = ((PaginaFragment) mAdaptador.getFragment(tabLayout
                        .getSelectedTabPosition()));
                if (frg != null) {
                    frg.fabOnClick(view);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Adaptador de páginas para el viewPager.
    public class PaginasAdapter extends CachedFragmentPagerAdapter {

        // Títulos de las páginas.
        private final ArrayList<String> mTitulos;

        public PaginasAdapter(FragmentManager fm, ArrayList<String> titulos) {
            super(fm);
            this.mTitulos = titulos;
        }

        @Override
        public Fragment getItem(int position) {
            return PaginaFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return mTitulos.size();
        }

        // Retorna el título de la página.
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitulos.get(position);
        }

    }

}
