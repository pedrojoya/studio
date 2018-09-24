package es.iessaladillo.pedrojoya.pr222.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr222.R;
import es.iessaladillo.pedrojoya.pr222.utils.ConfigurationUtils;
import es.iessaladillo.pedrojoya.pr222.utils.FragmentUtils;

public class DetailActivity extends AppCompatActivity implements DetailFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (ConfigurationUtils.inLandscape(this)) {
            // Not posible in landscape orientation.
            finish();
        } else {
            String item = getIntent().getStringExtra(DetailFragment.EXTRA_ITEM);
            long key = getIntent().getLongExtra(DetailFragment.EXTRA_KEY, 0);
            if (getSupportFragmentManager().findFragmentById(R.id.flDetail) == null) {
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flDetail,
                        DetailFragment.newInstance(item, key),
                        DetailFragment.class.getSimpleName());
            }
        }
    }

    @Override
    public void onDetailShown(String item, long key) {
        // Do nothing.
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void start(Context context, String item, long position) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailFragment.EXTRA_ITEM, item);
        intent.putExtra(DetailFragment.EXTRA_KEY, position);
        context.startActivity(intent);
    }

}