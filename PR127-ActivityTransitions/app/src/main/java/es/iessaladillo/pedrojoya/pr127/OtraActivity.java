package es.iessaladillo.pedrojoya.pr127;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class OtraActivity extends ActionBarActivity {

    public static final String TN_CUADRO = "Cuadro";
    public static final String EXTRA_FOTO_URL = "fotoUrl";

    private ImageView imgFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        configTransitions();
        setContentView(R.layout.activity_otra );
        ActivityCompat.postponeEnterTransition(this);
        imgFoto = ActivityCompat.requireViewById(this, R.id.imgFoto);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_FOTO_URL)).into(imgFoto, new Callback() {
            @Override public void onSuccess() {
                ActivityCompat.startPostponedEnterTransition(OtraActivity.this);
            }

            @Override public void onError() {

            }
        });
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(OtraActivity.this);
            }
        });
        ViewCompat.setTransitionName(imgFoto, TN_CUADRO);
        // ActivityCompat.startPostponedEnterTransition(OtraActivity.this);
    }

    private void configTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade transition = new Fade();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            transition.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
//
//
//            View decor = getWindow().getDecorView();
//            int actionBarId = R.id.action_bar_container;
//            Slide enterTransition = new Slide(Gravity.TOP);
//            Slide returnTransition = new Slide(Gravity.RIGHT);
//            enterTransition.excludeTarget(android.R.id.statusBarBackground, true);
//            enterTransition.excludeTarget(android.R.id.navigationBarBackground, true);
//            enterTransition.excludeTarget(decor.findViewById(actionBarId), true);
//            enterTransition.setDuration(3000);
//            returnTransition.excludeTarget(android.R.id.statusBarBackground, true);
//            returnTransition.excludeTarget(android.R.id.navigationBarBackground, true);
//            returnTransition.excludeTarget(decor.findViewById(actionBarId), true);
//            returnTransition.setDuration(3000);
//            getWindow().setEnterTransition(enterTransition);
//            getWindow().setReturnTransition(returnTransition);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            ActivityCompat.finishAfterTransition(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
