package pedrojoya.iessaladillo.es.pr248.ui.secondary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import pedrojoya.iessaladillo.es.pr248.R;

public class SecondaryFragment extends Fragment {

    public SecondaryFragment() {
    }

    public static SecondaryFragment newInstance() {
        return new SecondaryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_secondary, parent, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupToolbar(getView());
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
        toolbar.setTitle(R.string.secondary_title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        toolbar.inflateMenu(R.menu.fragment_secondary);
        toolbar.setOnMenuItemClickListener(item -> {
            navigateToSettings();
            return true;
        });
    }

    private void navigateToSettings() {
        Toast.makeText(requireContext(), getString(R.string.main_settings), Toast.LENGTH_SHORT).show();
    }

}
