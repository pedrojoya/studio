package es.iessaladillo.pedrojoya.pr179;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements ConceptosAdapter.OnItemClickListener {

    private static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";

    private CustomTabsServiceConnection mCustomTabsServiceConnection;
    private CustomTabsClient mCustomTabsClient;
    private CustomTabsSession mCustomTabsSession;
    private CustomTabsIntent mCustomTabsIntent;
    private ArrayList<Concepto> mConceptos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configToolbar();
        initVistas();
    }

    private void configToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    private void configCustomTabs() {
        // Se crea el servicio de conexión con CustomTabs.
        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                // Una vez conectados, almacenamos el cliente de conexión.
                mCustomTabsClient = customTabsClient;
                // Indicamos al navegador que se vaya inicializando.
                mCustomTabsClient.warmup(0L);
                // Iniciamos una nueva sesión con el navegador.
                mCustomTabsSession = mCustomTabsClient.newSession(null);
                // Se realiza la vinculación.
                CustomTabsClient.bindCustomTabsService(MainActivity.this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);
                // Se preparan las posibles URL.
                prepararCustomTabs(mConceptos);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                // Al desconectar liberamos los recursos asignados al cliente.
                mCustomTabsClient = null;
            }
        };
        // Creamos el intent personalizado para CustomTabs.
        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setShowTitle(true)
                .setStartAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .build();
    }

    private void initVistas() {
        RecyclerView grdConceptos = (RecyclerView) findViewById(R.id.grdConceptos);
        if (grdConceptos != null) {
            grdConceptos.setHasFixedSize(true);
            // El grid tendrá dos o tres columnas depediendo de la orientación.
            grdConceptos.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.gridColumns)));
            grdConceptos.setItemAnimator(new DefaultItemAnimator());
            mConceptos = getDatos();
            ConceptosAdapter adaptador = new ConceptosAdapter(mConceptos);
            adaptador.setOnItemClickListener(this);
            grdConceptos.setAdapter(adaptador);
        }
    }

    private void prepararCustomTabs(ArrayList<Concepto> conceptos) {
        for (Concepto concepto: conceptos) {
            mCustomTabsSession.mayLaunchUrl(Uri.parse("http://www.thefreedictionary.com/" + concepto.getEnglish()), null, null);
        }
    }

    private ArrayList<Concepto> getDatos() {
        ArrayList<Concepto> conceptos = new ArrayList<>();
        conceptos.add(new Concepto(R.drawable.animal, "Animal", "Animal"));
        conceptos.add(new Concepto(R.drawable.bridge, "Bridge", "Puente"));
        conceptos.add(new Concepto(R.drawable.flag, "Flag", "Bandera"));
        conceptos.add(new Concepto(R.drawable.food, "Food", "Comida"));
        conceptos.add(new Concepto(R.drawable.fruit, "Fruit", "Fruta"));
        conceptos.add(new Concepto(R.drawable.glass, "Glass", "Vaso"));
        conceptos.add(new Concepto(R.drawable.plant, "Plant", "Planta"));
        conceptos.add(new Concepto(R.drawable.science, "Science", "Ciencia"));
        conceptos.add(new Concepto(R.drawable.sea, "Sea", "Mar"));
        conceptos.add(new Concepto(R.drawable.space, "Space", "Espacio"));
        conceptos.add(new Concepto(R.drawable.art, "Art", "Arte"));
        conceptos.add(new Concepto(R.drawable.furniture, "Furniture",
                "Mobiliario"));
        return conceptos;
    }

    @Override
    public void onItemClick(View view, Concepto concepto, int position) {
        String sUrl = "http://www.thefreedictionary.com/" + concepto.getEnglish();
        mCustomTabsIntent.launchUrl(this, Uri.parse(sUrl));
    }

    @Override
    protected void onStart() {
        super.onStart();
        configCustomTabs();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCustomTabsClient = null;
        mCustomTabsSession = null;
        mCustomTabsServiceConnection = null;
    }

}
