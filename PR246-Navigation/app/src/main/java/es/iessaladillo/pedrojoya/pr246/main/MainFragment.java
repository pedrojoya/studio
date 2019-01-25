package es.iessaladillo.pedrojoya.pr246.main;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import es.iessaladillo.pedrojoya.pr246.R;
import es.iessaladillo.pedrojoya.pr246.utils.KeyboardUtils;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private NavController navController;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setupViews(requireView());
    }

    private void setupViews(View view) {
        Button btnDetail = ViewCompat.requireViewById(view, R.id.btnDetail);
        Button btnAnother = ViewCompat.requireViewById(view, R.id.btnAnother);
        EditText txtName = ViewCompat.requireViewById(view, R.id.txtName);
        btnDetail.setOnClickListener(v -> {
            KeyboardUtils.hideSoftKeyboard(requireActivity());
            String name = txtName.getText().toString();
            if (!TextUtils.isEmpty(name)) {
                navigateToDetail(name);
            }
        });
        btnAnother.setOnClickListener(v -> {
            KeyboardUtils.hideSoftKeyboard(requireActivity());
            String name = txtName.getText().toString();
            if (!TextUtils.isEmpty(name)) {
                navigateToAnother(name);
            }
        });
    }

    private void navigateToDetail(String name) {
        MainFragmentDirections.ActionMainToDetail action =
            MainFragmentDirections.actionMainToDetail(name);
        navController.navigate(action);
    }

    private void navigateToAnother(String name) {
        MainFragmentDirections.ActionMainToAnother action =
            MainFragmentDirections.actionMainToAnother(name);
        navController.navigate(action);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController)
            || super.onOptionsItemSelected(item);
    }
}
