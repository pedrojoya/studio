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
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class MainActivity extends ActionBarActivity {

    private static final String FOTO_URL = "http://lorempixel.com/500/375/nature/1";

    private ImageView imgCuadro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        //configTransitions();
        setContentView(R.layout.activity_main);
        imgCuadro = (ImageView) findViewById(R.id.imgFoto);
        Picasso.with(this).load(FOTO_URL).into(imgCuadro);
        imgCuadro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OtraActivity.class);
                intent.putExtra(OtraActivity.EXTRA_FOTO_URL, FOTO_URL);
                ActivityCompat.startActivity(MainActivity.this, intent,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                                imgCuadro, OtraActivity.TN_CUADRO)
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

}
