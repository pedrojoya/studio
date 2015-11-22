package pedrojoya.iessaladillo.es.pr105.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr105.fragmentos.NestedScrollViewFragment;
import pedrojoya.iessaladillo.es.pr105.R;
import pedrojoya.iessaladillo.es.pr105.fragmentos.TabLayoutFragment;
import pedrojoya.iessaladillo.es.pr105.fragmentos.CollapsingToolbarLayoutFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String STATE_SELECTED_NAVITEM_ID = "selectedNavItemPosition";
    private static final String PREFERENCES_FILE = "prefs";
    private static final String PREF_NAV_DRAWER_OPENED = "navdrawerOpened";

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private CircleImageView mImgProfile;

    private int mSelectedNavItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Se restaura el estado de la opción seleccionada en el nav drawer.
        if (savedInstanceState != null) {
            mSelectedNavItemId =
                    savedInstanceState.getInt(STATE_SELECTED_NAVITEM_ID);
            MenuItem item = mNavigationView.getMenu().findItem(mSelectedNavItemId);
            item.setChecked(true);
            setTitle(item.getTitle());
        } else {
            // Si no hay estado anterior, se selecciona la primera opción
            // en el nav drawer.
            onNavigationItemSelected(mNavigationView.getMenu().getItem(0));
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se guarda la opción seleccionada actualmente en el nav drawer.
        outState.putInt(STATE_SELECTED_NAVITEM_ID, mSelectedNavItemId);
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mImgProfile = (CircleImageView) mNavigationView.getHeaderView(0).findViewById(R.id.imgProfile);
        configNavigationDrawer();
    }

    // Configura el navigation drawer.
    private void configNavigationDrawer() {
        // Se obtiene la imagen de perfil.
        Picasso.with(this).load("http://lorempixel.com/200/200/people/").into(mImgProfile);
        // La actividad actuará de listener cuando se seleccione una opción
        // del nav drawer.
        mNavigationView.setNavigationItemSelectedListener(this);
        // Si nunca se ha abierto el nav drawer se abre automáticamente.
        if (!leerPreferencia()) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            guardarPreferencia();
        }
    }

    // Muestra el fragmento o actividad corresponiente a la opción
    // seleccionada en el nav drawer.
    private void mostrarOpcion(String title) {
        Fragment frg;
        switch (mSelectedNavItemId) {
            case R.id.nav_nestedscrollview:
                frg = NestedScrollViewFragment.newInstance(title);
                getSupportFragmentManager().beginTransaction().replace(R.id.content, frg, title).commit();
                break;
            case R.id.nav_tablayout:
                frg = TabLayoutFragment.newInstance(title);
                getSupportFragmentManager().beginTransaction().replace(R.id.content, frg, title).commit();
                break;
            case R.id.nav_collapsingtoolbarlayout:
                frg = CollapsingToolbarLayoutFragment.newInstance("Collapsing toolbar");
                getSupportFragmentManager().beginTransaction().replace(R.id.content, frg, title).commit();
                break;
            default:
                startActivity(new Intent(this, DetalleActivity.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Cuando se pulsa el icono de la toolbar se abre el panel.
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Cuando se selecciona una opción del nav drawer.
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mSelectedNavItemId = menuItem.getItemId();
        // Se muestra el fragmento o actividad correspondiente.
        mostrarOpcion(menuItem.getTitle().toString());
        // Se selecciona el elemento.
        menuItem.setChecked(true);
        // Se cierra el navigation drawer.
        mDrawerLayout.closeDrawers();
        return true;
    }

    // Guarda la preferencia de que el nav drawer ya ha sido abierto.
    private void guardarPreferencia() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(PREF_NAV_DRAWER_OPENED, true);
        editor.apply();
    }

    // Retorna la preferencia de si el nav drawer ya ha sido abierto.
    private boolean leerPreferencia() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(PREF_NAV_DRAWER_OPENED, false);
    }

}