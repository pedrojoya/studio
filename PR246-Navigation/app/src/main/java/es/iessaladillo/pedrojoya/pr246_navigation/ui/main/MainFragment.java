package es.iessaladillo.pedrojoya.pr246_navigation.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import es.iessaladillo.pedrojoya.pr246_navigation.R;

public class MainFragment extends Fragment {

    private Button btnNavigate;
    private NavController navController;
    private MainViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        navController = Navigation.findNavController(view);
        btnNavigate = view.findViewById(R.id.btnNavigate);
        // Usamos un método para generar el listener con la acción de navegación correspondiente.
        //btnNavigate.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_secundaryFragment));
        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.counter++;
                MainFragmentDirections.ActionMainToSecondary action = MainFragmentDirections
                        .actionMainToSecondary();
                action.setCounter(viewModel.counter);
                navController.navigate(action);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
//        toolbar.inflateMenu(R.menu.main_fragment);
//        toolbar.invalidate();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.anotherActivity) {
            navController.navigate(MainFragmentDirections.actionMainToAnother().setCounter(viewModel.counter));
            return true;
        }
        // La selección de un item del menú se la pasamos al controlador de navegación
        return NavigationUI.onNavDestinationSelected(item,
                Navigation.findNavController(requireActivity(), R.id.navHostFragment))
                || super.onOptionsItemSelected(item);
    }

}
