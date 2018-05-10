package es.iessaladillo.pedrojoya.pr246_navigation.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import es.iessaladillo.pedrojoya.pr246_navigation.R;

public class MainFragment extends Fragment {

    private Button btnNavigate;

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
        btnNavigate = view.findViewById(R.id.btnNavigate);
        // Usamos un método para generar el listener con la acción de navegación correspondiente.
        btnNavigate.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_secundaryFragment));
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
        // La selección de un item del menú se la pasamos al controlador de navegación
        return NavigationUI.onNavDestinationSelected(item,
                Navigation.findNavController(requireActivity(), R.id.navHostFragment))
                || super.onOptionsItemSelected(item);
    }

}
