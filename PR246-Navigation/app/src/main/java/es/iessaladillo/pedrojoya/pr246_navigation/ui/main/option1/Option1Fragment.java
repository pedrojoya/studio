package es.iessaladillo.pedrojoya.pr246_navigation.ui.main.option1;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import es.iessaladillo.pedrojoya.pr246_navigation.R;
import es.iessaladillo.pedrojoya.pr246_navigation.base.FirstLevelActionBarOnNavigatedListener;

public class Option1Fragment extends Fragment {

    private Button btnNavigate;
    private NavController navController;
    private Option1ViewModel viewModel;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.option1_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(Option1ViewModel.class);
        navController = Navigation.findNavController(view);
        toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
        btnNavigate = view.findViewById(R.id.btnNavigate);
        // Usamos un método para generar el listener con la acción de navegación correspondiente.
        //btnNavigate.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_secundaryFragment));
        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.counter++;
                Option1FragmentDirections.ActionMainToSecondary action = Option1FragmentDirections.actionMainToSecondary();
                action.setCounter(viewModel.counter);
                navController.navigate(action);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        drawerLayout = requireActivity().findViewById(R.id.drawerLayout);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController);
        navController.addOnNavigatedListener(
                new FirstLevelActionBarOnNavigatedListener((AppCompatActivity) requireActivity(),
                        drawerLayout,
                        new int[]{R.id.option1Fragment, R.id.option2Fragment, R.id.option3Fragment}));
        //        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        //        toolbar.inflateMenu(R.menu.option1_fragment);
        //        toolbar.invalidate();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.option1_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.anotherActivity:
                navController.navigate(Option1FragmentDirections.actionMainToAnother()
                        .setCounter(viewModel.counter));
                return true;
            case R.id.mnuToOption3:
                NavOptions.Builder builder = new NavOptions.Builder()
                        .setLaunchSingleTop(true);
/*
                        .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                        .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                        .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                        .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim);
*/
                navController.navigate(R.id.actionMainToDetail, null, builder.build());

                //navController.navigate(R.id.actionMainToDetail);
                return true;
            default:
                // La selección de un item del menú se la pasamos al controlador de navegación
                return NavigationUI.onNavDestinationSelected(item, navController)
                        || super.onOptionsItemSelected(item);
        }
    }

}
