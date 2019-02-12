package es.iessaladillo.pedrojoya.pr045.ui.main.v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import es.iessaladillo.pedrojoya.pr045.R;

@SuppressWarnings("WeakerAccess")
public class MainFragmentV2 extends Fragment {

    private static final int DEFAULT_PAGE = 2;

    public static Fragment newInstance() {
        return new MainFragmentV2();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_v2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(requireView());
    }

    private void setupViewPager(View view) {
        ViewPager2 vpPages = ViewCompat.requireViewById(view, R.id.vpPages);

        MainFragmentAdapterV2 adapter = new MainFragmentAdapterV2();
        adapter.submitList(Arrays.asList(0, 1, 2, 3, 4));
        vpPages.setAdapter(adapter);
        vpPages.setCurrentItem(DEFAULT_PAGE);
    }

}
