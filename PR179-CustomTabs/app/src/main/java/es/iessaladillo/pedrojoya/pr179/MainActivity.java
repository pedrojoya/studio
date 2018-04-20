package es.iessaladillo.pedrojoya.pr179;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    private static final String BASE_URL = "http://www.thefreedictionary.com/";

    private CustomTabsServiceConnection mCustomTabsServiceConnection;
    private CustomTabsClient mCustomTabsClient;
    private CustomTabsSession mCustomTabsSession;
    private CustomTabsIntent mCustomTabsIntent;
    private ArrayList<Word> mWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void setupRecyclerView() {
        RecyclerView grdWords = ActivityCompat.requireViewById(this, R.id.grdWords);
        grdWords.setHasFixedSize(true);
        // 2 or 3 columns depending on orientation.
        grdWords.setLayoutManager(
                new GridLayoutManager(this, getResources().getInteger(R.integer.gridColumns)));
        grdWords.setItemAnimator(new DefaultItemAnimator());
        mWords = getWords();
        MainActivityAdapter adapter = new MainActivityAdapter(mWords);
        adapter.setOnItemClickListener((view, word, position) -> showWeb(word));
        grdWords.setAdapter(adapter);
    }

    private void setupCustomTabs() {
        // Create service connection.
        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName,
                    CustomTabsClient customTabsClient) {
                // Save client.
                mCustomTabsClient = customTabsClient;
                // Warmup browser.
                mCustomTabsClient.warmup(0L);
                // Start new session with browser.
                mCustomTabsSession = mCustomTabsClient.newSession(new CustomTabsCallback() {
                    @Override
                    public void onNavigationEvent(int navigationEvent, Bundle extras) {
                        Toast.makeText(MainActivity.this, navigationEvent, Toast.LENGTH_SHORT)
                                .show();
                        super.onNavigationEvent(navigationEvent, extras);
                    }
                });
                // Bind service.
                CustomTabsClient.bindCustomTabsService(MainActivity.this, CUSTOM_TAB_PACKAGE_NAME,
                        mCustomTabsServiceConnection);
                // Prepare possible urls.
                prepareCustomTabs(mWords);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                // Free client resources.
                mCustomTabsClient = null;
            }
        };
        // Create custom intent for CustomTabs.
        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession).setToolbarColor(
                ContextCompat.getColor(this, R.color.colorPrimary))
                .setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setShowTitle(true)
                .setStartAnimations(this, android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right)
                .setExitAnimations(this, android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right)
                .build();
    }

    private void prepareCustomTabs(ArrayList<Word> words) {
        for (Word word : words) {
            mCustomTabsSession.mayLaunchUrl(
                    Uri.parse("http://www.thefreedictionary.com/" + word.getEnglish()), null, null);
        }
    }

    private ArrayList<Word> getWords() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(R.drawable.animal, "Animal", "Animal"));
        words.add(new Word(R.drawable.bridge, "Bridge", "Puente"));
        words.add(new Word(R.drawable.flag, "Flag", "Bandera"));
        words.add(new Word(R.drawable.food, "Food", "Comida"));
        words.add(new Word(R.drawable.fruit, "Fruit", "Fruta"));
        words.add(new Word(R.drawable.glass, "Glass", "Vaso"));
        words.add(new Word(R.drawable.plant, "Plant", "Planta"));
        words.add(new Word(R.drawable.science, "Science", "Ciencia"));
        words.add(new Word(R.drawable.sea, "Sea", "Mar"));
        words.add(new Word(R.drawable.space, "Space", "Espacio"));
        words.add(new Word(R.drawable.art, "Art", "Arte"));
        words.add(new Word(R.drawable.furniture, "Furniture", "Mobiliario"));
        return words;
    }

    private void showWeb(Word word) {
        String sUrl = BASE_URL + word.getEnglish();
        mCustomTabsIntent.launchUrl(this, Uri.parse(sUrl));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupCustomTabs();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Free resources.
        mCustomTabsClient = null;
        mCustomTabsSession = null;
        mCustomTabsServiceConnection = null;
    }

}
