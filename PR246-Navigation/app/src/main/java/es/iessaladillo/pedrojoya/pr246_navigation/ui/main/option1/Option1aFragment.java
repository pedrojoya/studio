package es.iessaladillo.pedrojoya.pr246_navigation.ui.main.option1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import es.iessaladillo.pedrojoya.pr246_navigation.R;
import es.iessaladillo.pedrojoya.pr246_navigation.base.FirstLevelActionBarOnNavigatedListener;


public class Option1aFragment extends Fragment {


    private TextView lblMessage;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavController navController;

    public Option1aFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.option1a_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
        navController = Navigation.findNavController(view);
        lblMessage = ViewCompat.requireViewById(view, R.id.lblMessage);
        int counter = Option1aFragmentArgs.fromBundle(getArguments()).getCounter();
        lblMessage.setText(String.valueOf(counter));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // OJO drawerLayout puede ser null porque la actividad no tenga drawer layout.
        drawerLayout = requireActivity().findViewById(R.id.drawerLayout);
        if (drawerLayout != null) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController, drawerLayout);
/*
        navController.addOnNavigatedListener(
                new FirstLevelActionBarOnNavigatedListener((AppCompatActivity) requireActivity(), drawerLayout,
                        new int[] {R.id.option1Fragment, R.id.option2Fragment, R.id.option3Fragment}));
*/
    }

    @Override
    public void onDestroy() {
        if (drawerLayout != null) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
        super.onDestroy();
    }
}
