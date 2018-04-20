package info.devexchanges.snaprecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    private void initVistas() {
        RecyclerView lstAppsHorizontal = ActivityCompat.requireViewById(this, R.id.lstAppsHorizontal);
        RecyclerView lstAppsVertical = ActivityCompat.requireViewById(this, R.id.lstAppsVertical);

        SnapHelper snapHelperHorizontal = new LinearSnapHelper();
        // SnapHelper snapHelperHorizontal = new GravitySnapHelper(Gravity.START);
        // SnapHelper snapHelperHorizontal = new GravitySnapHelper(Gravity.END);
        snapHelperHorizontal.attachToRecyclerView(lstAppsHorizontal);
        lstAppsHorizontal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        lstAppsHorizontal.setHasFixedSize(true);
        lstAppsHorizontal.setAdapter(new AppsAdapter(this, R.layout
                .activity_main_item_horizontal, getListaApps()));

        SnapHelper snapHelperVertical = new LinearSnapHelper();
        // SnapHelper snapHelperVertical = new GravitySnapHelper(Gravity.TOP);
        // SnapHelper snapHelperVertical = new GravitySnapHelper(Gravity.BOTTOM);
        snapHelperVertical.attachToRecyclerView(lstAppsVertical);
        lstAppsVertical.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        lstAppsVertical.setHasFixedSize(true);
        lstAppsVertical.setAdapter(new AppsAdapter(this, R.layout.activity_main_item_vertical,
                getListaApps()));
    }

    private ArrayList<App> getListaApps() {
        ArrayList<App> items = new ArrayList<>();
        items.add(new App("Google+", R.drawable.google_plus));
        items.add(new App("Facebook", R.drawable.facebook));
        items.add(new App("LinkedIn", R.drawable.linkedin));
        items.add(new App("Youtube", R.drawable.youtube));
        items.add(new App("Instagram", R.drawable.instagram));
        items.add(new App("Skype", R.drawable.skype));
        items.add(new App("Twitter", R.drawable.twitter));
        items.add(new App("Wikipedia", R.drawable.wikipedia));
        items.add(new App("Whats app", R.drawable.what_apps));
        items.add(new App("Pokemon Go", R.drawable.pokemon_go));
        return items;
    }

}
