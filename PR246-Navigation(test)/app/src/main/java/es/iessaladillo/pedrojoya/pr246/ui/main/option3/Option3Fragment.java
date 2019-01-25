package es.iessaladillo.pedrojoya.pr246.ui.main.option3;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import es.iessaladillo.pedrojoya.pr246.R;

public class Option3Fragment extends Fragment {


    private Toolbar toolbar;
    private NavController navController;
    private DrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.option3_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
    }

    @Override
    public void onDestroyView() {
        AppBarLayout appbarLayut = requireActivity().findViewById(R.id.appbarLayout);
        //appbarLayut.setExpanded(true);
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        drawerLayout = requireActivity().findViewById(R.id.drawerLayout);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController, drawerLayout);
        /*navController.addOnNavigatedListener(
                new FirstLevelActionBarOnNavigatedListener((AppCompatActivity) requireActivity(), drawerLayout,
                        new int[] {R.id.option1Fragment, R.id.option2Fragment, R.id.option3Fragment}));
*/
        //        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        //        toolbar.inflateMenu(R.menu.option1_fragment);
        //        toolbar.invalidate();
    }

}
