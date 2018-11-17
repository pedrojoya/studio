package es.iessaladillo.pedrojoya.pr249.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr249.R;
import es.iessaladillo.pedrojoya.pr249.utils.ConfigurationUtils;
import es.iessaladillo.pedrojoya.pr249.utils.FragmentUtils;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG_DETAIL_FRAGMENT = "TAG_DETAIL_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (ConfigurationUtils.inLandscape(this)) {
            // Not posible in landscape orientation.
            finish();
        } else {
            String item = getIntent().getStringExtra(DetailFragment.EXTRA_ITEM);
            if (getSupportFragmentManager().findFragmentById(R.id.flDetail) == null) {
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flDetail,
                        DetailFragment.newInstance(item), TAG_DETAIL_FRAGMENT);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void start(Context context, String item) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailFragment.EXTRA_ITEM, item);
        context.startActivity(intent);
    }

}