package es.iessaladillo.pedrojoya.pr045.ui.main;

import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr045.R;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_PAGE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewPager();
    }

    private void setupViewPager() {
        ViewPager vpPages = ActivityCompat.requireViewById(this, R.id.vpPages);

        MainActivityAdapter adapter = new MainActivityAdapter(this);
        vpPages.setAdapter(adapter);
        vpPages.setCurrentItem(DEFAULT_PAGE);
    }

}