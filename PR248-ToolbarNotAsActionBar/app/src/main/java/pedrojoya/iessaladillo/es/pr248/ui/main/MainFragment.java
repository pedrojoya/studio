package pedrojoya.iessaladillo.es.pr248.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import pedrojoya.iessaladillo.es.pr248.R;
import pedrojoya.iessaladillo.es.pr248.ui.secondary.SecondaryFragment;
import pedrojoya.iessaladillo.es.pr248.utils.FragmentUtils;
import pedrojoya.iessaladillo.es.pr248.utils.ToastUtils;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    public MainFragment() {
    }

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupToolbar(getView());
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.fragment_main);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnuNext:
                    showSecondaryFragment();
                    return true;
                case R.id.mnuSettings:
                    showSettings();
                default:
                    return false;
            }
        });
    }

    private void showSecondaryFragment() {
        FragmentUtils.replaceFragmentAddToBackstack(requireActivity().getSupportFragmentManager(),
                R.id.flContent, SecondaryFragment.newInstance(),
                SecondaryFragment.class.getSimpleName(), SecondaryFragment.class.getSimpleName(),
                FragmentTransaction.TRANSIT_NONE);
    }

    private void showSettings() {
        ToastUtils.toast(getContext(), getString(R.string.main_settings));
    }

}
