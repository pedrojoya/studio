package es.iessaldillo.pedrojoya.pr205;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    RecyclerView pager;

    private SnapHelper mPagerSnapHelper = new PagerSnapHelper();
    private PageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVistas();
    }

    private void initVistas() {
        pager.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        mPagerSnapHelper.attachToRecyclerView(pager);
        mPageAdapter=new PageAdapter(pager, getLayoutInflater());
        pager.setAdapter(mPageAdapter);
    }
}
