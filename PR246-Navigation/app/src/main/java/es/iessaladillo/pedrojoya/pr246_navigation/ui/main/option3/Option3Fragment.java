package es.iessaladillo.pedrojoya.pr246_navigation.ui.main.option3;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import es.iessaladillo.pedrojoya.pr246_navigation.R;
import es.iessaladillo.pedrojoya.pr246_navigation.base.FirstLevelActionBarOnNavigatedListener;

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
        navController.addOnNavigatedListener(
                new FirstLevelActionBarOnNavigatedListener((AppCompatActivity) requireActivity(), drawerLayout,
                        new int[] {R.id.option1Fragment, R.id.option2Fragment, R.id.option3Fragment}));

        //        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        //        toolbar.inflateMenu(R.menu.option1_fragment);
        //        toolbar.invalidate();
    }

}
