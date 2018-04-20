package es.iessaladillo.pedrojoya.pr045.main;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr045.R;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_PAGE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ViewPager vpPages = ActivityCompat.requireViewById(this, R.id.vpPages);

        MainActivityAdapter adapter = new MainActivityAdapter(this);
        vpPages.setAdapter(adapter);
        vpPages.setCurrentItem(DEFAULT_PAGE);
    }

}