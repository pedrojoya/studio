package es.iessaladillo.pedrojoya.pr045.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import es.iessaladillo.pedrojoya.pr045.R;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private static final int DEFAULT_PAGE = 2;

    static Fragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(requireView());
    }

    private void setupViewPager(View view) {
        ViewPager vpPages = ViewCompat.requireViewById(view, R.id.vpPages);

        MainFragmentAdapter adapter = new MainFragmentAdapter();
        vpPages.setAdapter(adapter);
        vpPages.setCurrentItem(DEFAULT_PAGE);
    }

}
