package es.iessaladillo.pedrojoya.pr094;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements
        PaginaFragment.HideShowInterface {

    private FrameLayout container;
    private MainFragment mFragmento;
    private boolean mIsToolbarShown = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            mFragmento = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFragmento)
                    .commit();
        }
    }

    @Override
    public void onHide() {
        mIsToolbarShown = false;
        mFragmento.onHide();
    }

    @Override
    public void onShow() {
        mIsToolbarShown = true;
        mFragmento.onShow();
    }

    @Override
    public boolean isToolbarShown() {
        return mIsToolbarShown;
    }

}
