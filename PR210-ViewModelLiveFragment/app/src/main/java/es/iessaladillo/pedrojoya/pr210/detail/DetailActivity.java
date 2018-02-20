package es.iessaladillo.pedrojoya.pr210.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.iessaladillo.pedrojoya.pr210.R;
import es.iessaladillo.pedrojoya.pr210.utils.ConfigurationUtils;
import es.iessaladillo.pedrojoya.pr210.utils.FragmentUtils;

public class DetailActivity extends DetailFragmentBaseActivity<DetailActivityViewModel> {

    private static final String TAG_DETAIL_FRAGMENT = "TAG_DETAIL_FRAGMENT";
    private static final String EXTRA_ITEM = "EXTRA_ITEM";
    @SuppressWarnings("FieldCanBeLocal")
    private DetailActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mViewModel = ViewModelProviders.of(this).get(DetailActivityViewModel.class);
        if (ConfigurationUtils.hasLandscapeOrientation(this)) {
            // Not posible in landscape orientation.
            finish();
        } else {
            String item = getIntent().getStringExtra(DetailFragment.EXTRA_ITEM);
            mViewModel.setCurrentItem(item);
            if (getSupportFragmentManager().findFragmentById(R.id.flDetail) == null) {
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flDetail,
                        DetailFragment.newInstance(), TAG_DETAIL_FRAGMENT);
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
        intent.putExtra(DetailActivity.EXTRA_ITEM, item);
        context.startActivity(intent);
    }

    @Override
    public Class<DetailActivityViewModel> getViewModelClass() {
        return DetailActivityViewModel.class;
    }

}