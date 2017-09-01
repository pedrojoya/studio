package es.iessaladillo.pedrojoya.pr049.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr049.R;
import es.iessaladillo.pedrojoya.pr049.utils.ConfigurationUtils;
import es.iessaladillo.pedrojoya.pr049.utils.FragmentUtils;

public class DetailActivity extends AppCompatActivity implements DetailFragment.Callback {

    private static final String TAG_DETAIL_FRAGMENT = "TAG_DETAIL_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (ConfigurationUtils.hasLandscapeOrientation(this)) {
            // Not posible in landscape orientation.
            finish();
        } else {
            String item = getIntent().getStringExtra(DetailFragment.EXTRA_ITEM);
            int position = getIntent().getIntExtra(DetailFragment.EXTRA_POSITION, 0);
            if (getSupportFragmentManager().findFragmentById(R.id.flDetail) == null) {
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flDetail,
                        DetailFragment.newInstance(item, position), TAG_DETAIL_FRAGMENT);
            }
        }
    }

    @Override
    public void onDetailShown(int position) {
        // Do nothing.
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void start(Context context, String item, int position) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailFragment.EXTRA_ITEM, item);
        intent.putExtra(DetailFragment.EXTRA_POSITION, position);
        context.startActivity(intent);
    }

}