package es.iessaladillo.pedrojoya.pr257.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import es.iessaladillo.pedrojoya.pr257.R;
import es.iessaladillo.pedrojoya.pr257.databinding.FragmentMainBinding;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private FragmentMainBinding b;
    private NavController navController;

    public MainFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container,
            false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainFragmentViewModel viewModel = ViewModelProviders.of(this).get(
            MainFragmentViewModel.class);
        navController = NavHostFragment.findNavController(this);
        b.setVm(viewModel);
        b.setNavController(navController);
        b.setLifecycleOwner(getViewLifecycleOwner());
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
