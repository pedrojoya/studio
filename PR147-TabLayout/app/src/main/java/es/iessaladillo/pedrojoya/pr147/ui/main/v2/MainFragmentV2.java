package es.iessaladillo.pedrojoya.pr147.ui.main.v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import es.iessaladillo.pedrojoya.pr147.R;
import es.iessaladillo.pedrojoya.pr147.base.TabLayoutMediator;

@SuppressWarnings("WeakerAccess")
public class MainFragmentV2 extends Fragment {

    public static MainFragmentV2 newInstance() {
        return new MainFragmentV2();
    }

    private final int[] titleResIds = {R.string.lorem_title, R.string
        .likes_title};
    private final int[] iconResIds = {R.drawable.ic_share_white_24dp, R.drawable
        .ic_thumb_up_white_24dp};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_v2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(requireView());
    }

    private void setupViews(View view) {
        setupToolbar(view);
        setupViewPager(view);
    }

    private void setupToolbar(View view) {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(
            ViewCompat.requireViewById(view, R.id.toolbar));
    }

    private void setupViewPager(View view) {
        ViewPager2 viewPager = ViewCompat.requireViewById(view, R.id.viewPager);
        TabLayout tabLayout = ViewCompat.requireViewById(view, R.id.tabLayout);
        FloatingActionButton fab = ViewCompat.requireViewById(view, R.id.fab);

        fab.setImageResource(R.drawable.ic_share_white_24dp);
        fab.show();
        MainFragmentAdapterV2 adapter = new MainFragmentAdapterV2(requireFragmentManager());
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(titleResIds[position]);
            tab.setIcon(iconResIds[position]);
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    // Al hacer drag siempre hay que ocultar
                    fab.hide();
                } else if (state == ViewPager.SCROLL_STATE_SETTLING){
                    // Al colocarse en un página, dependiendo de la página de la que se trate
                    // se actualiza el icono del fab y se muestra.
                    if (viewPager.getCurrentItem() == 0) {
                        fab.setImageResource(R.drawable.ic_share_white_24dp);
                        fab.show();
                    } else {
                        fab.setImageResource(R.drawable.ic_thumb_up_white_24dp);
                        fab.show();
                    }
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Al cambiar la pestaña, dependiendo de la que sea se actualiza
                // el icono del fab y se muestra.
                if (tab.getPosition() == 0) {
                    fab.setImageResource(R.drawable.ic_share_white_24dp);
                    fab.show();
                } else {
                    fab.setImageResource(R.drawable.ic_thumb_up_white_24dp);
                    fab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Al cambiar de pestaña siempre hay que ocultar el fab.
                fab.hide();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
