package pedrojoya.iessaladillo.es.pr105;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String STATE_SELECTED_NAVITEM_ID = "selectedNavItemPosition";
    private static final String PREFERENCES_FILE = "prefs";
    private static final String PREF_NAV_DRAWER_OPENED = "navdrawerOpened";
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private int mSelectedNavItemId = -1;
    private CircleImageView mImgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        if (savedInstanceState != null) {
            mSelectedNavItemId =
                    savedInstanceState.getInt(STATE_SELECTED_NAVITEM_ID);
            MenuItem item = mNavigationView.getMenu().findItem(mSelectedNavItemId);
            item.setChecked(true);
            setTitle(item.getTitle());
        } else {
            onNavigationItemSelected(mNavigationView.getMenu().getItem(0));
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_NAVITEM_ID, mSelectedNavItemId);
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        configToolbar();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mImgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        configNavigationDrawer();
    }

    // Configura la toolbar.
    private void configToolbar() {
        // Se establece la toolbar como action bar y se obliga a mostrar
        // el icono para abrir el naviagation drawer.
        //setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    // Configura el navigation drawer.
    private void configNavigationDrawer() {
        Picasso.with(this).load("http://lorempixel.com/200/200/people/").into(mImgProfile);
        mNavigationView.setNavigationItemSelectedListener(this);
        if (!leerPreferencia()) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            guardarPreferencia();
        }
    }

    private void cargarFragmento(String title) {
        setTitle(title);
        NestedScrollViewFragment frg = NestedScrollViewFragment.newInstance(title);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, frg, title).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Se abre el panel.
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mSelectedNavItemId = menuItem.getItemId();
        // Se carga el fragmento correspondiente.
        cargarFragmento(menuItem.getTitle().toString());
        // Se selecciona el elemento.
        menuItem.setChecked(true);
        // Se cierra el navigation drawer.
        mDrawerLayout.closeDrawers();
        return true;
    }

    private void guardarPreferencia() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(PREF_NAV_DRAWER_OPENED, true);
        editor.apply();
    }

    public boolean leerPreferencia() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(PREF_NAV_DRAWER_OPENED, false);
    }

}