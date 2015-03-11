package es.iessaladillo.pedrojoya.pr127;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView lblCuadro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        configTransitions();
        setContentView(R.layout.activity_main);
        lblCuadro = (TextView) findViewById(R.id.lblCuadro);
        lblCuadro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OtraActivity.class);
                ActivityCompat.startActivity(MainActivity.this, intent,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                                lblCuadro, OtraActivity.EXTRA_CUADRO)
                                .toBundle());
            }
        });
    }

    private void configTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decor = getWindow().getDecorView();
            int actionBarId = R.id.action_bar_container;
            Slide reenterTransition = new Slide(Gravity.LEFT);
            Explode exitTransition = new Explode();
            reenterTransition.excludeTarget(android.R.id.statusBarBackground, true);
            reenterTransition.excludeTarget(android.R.id.navigationBarBackground, true);
            reenterTransition.excludeTarget(decor.findViewById(actionBarId), true);
            reenterTransition.setDuration(3000);
            exitTransition.excludeTarget(android.R.id.statusBarBackground, true);
            exitTransition.excludeTarget(android.R.id.navigationBarBackground, true);
            exitTransition.excludeTarget(decor.findViewById(actionBarId), true);
            exitTransition.setDuration(3000);
            getWindow().setExitTransition(exitTransition);
            getWindow().setReenterTransition(reenterTransition);
        }
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
}
