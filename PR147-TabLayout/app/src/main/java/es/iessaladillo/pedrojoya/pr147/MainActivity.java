package es.iessaladillo.pedrojoya.pr147;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr147.utils.CachedFragmentPagerAdapter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configToolbar();
        configViewPager();
    }

    // Configura la Toolbar.
    private void configToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    // Configura el ViewPager.
    private void configViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter =
                new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addPage(getString(R.string.opcion, 1),
                R.drawable.ic_tab1, getResources().getColor(R.color.colorPrimary));
        viewPagerAdapter.addPage(getString(R.string.opcion, 2),
                R.drawable.ic_tab2, getResources().getColor(R.color.colorAccent));
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        /**** Para mostrar iconos en las tabs.
         for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(viewPagerAdapter.getPageIcon(i));
         }
         ****/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Adaptador para el ViewPager.
    static class ViewPagerAdapter extends CachedFragmentPagerAdapter {

        private final ArrayList<String> mTitulos = new ArrayList<>();
        private final ArrayList<Integer> mIconos = new ArrayList<>();
        private final ArrayList<Integer> mColores = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addPage(String titulo, int icono, int color) {
            mTitulos.add(titulo);
            mIconos.add(icono);
            mColores.add(color);
        }

        // Retorna el fragmento correspondiente a la posición recibida.
        @Override
        public Fragment getItem(int position) {
            return TabFragment.newInstance(mColores.get(position));
        }

        // Retorna el número de fragmentos del adaptador.
        @Override
        public int getCount() {
            return mTitulos.size();
        }

        // Retorna el título asociado a una determinada página.
        @Override
        public CharSequence getPageTitle(int position) {
            return " " + mTitulos.get(position);
        }

        // Retorna el resId del icono asociado a una determinada página.
        @DrawableRes
        public int getPageIcon(int position) {
            return mIconos.get(position);
        }

    }

}
