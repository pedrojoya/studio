package es.iessaladillo.pedrojoya.pr178;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MenuBottomSheetDialogFragment extends BottomSheetDialogFragment implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String ARG_ALUMNO = "arg_alumno";

    private Alumno mAlumno;

    static MenuBottomSheetDialogFragment newInstance(Alumno alumno) {
        MenuBottomSheetDialogFragment frg = new MenuBottomSheetDialogFragment();
        Bundle argumentos = new Bundle();
        argumentos.putParcelable(ARG_ALUMNO, alumno);
        frg.setArguments(argumentos);
        return frg;
    }

    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new
            BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN
                    || newState == BottomSheetBehavior.STATE_SETTLING) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottomsheet, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mAlumno = getArguments().getParcelable(ARG_ALUMNO);
        }
        if (getView() != null) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View)
                    getView()
                    .getParent()).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();
            if (behavior != null && behavior instanceof BottomSheetBehavior) {
                ((BottomSheetBehavior) behavior).setBottomSheetCallback(
                        mBottomSheetBehaviorCallback);
            }
            NavigationView navigationView = (NavigationView) getView().findViewById(
                    R.id.navigation_view);
            if (navigationView != null) {
                navigationView.setNavigationItemSelectedListener(this);
            }
            if (mAlumno != null && navigationView != null) {
                navigationView.getMenu().findItem(R.id.bsmenuTitulo).setTitle(mAlumno.getNombre());
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bsmenuLlamar:
                Toast.makeText(getContext(), getString(R.string.llamar_a, mAlumno.getNombre()),
                        Toast.LENGTH_SHORT).show();
                dismiss();
                return true;
            case R.id.bsmenuMensaje:
                Toast.makeText(getContext(),
                        getString(R.string.enviar_mensaje_a, mAlumno.getNombre()),
                        Toast.LENGTH_SHORT).show();
                dismiss();
                return true;
            case R.id.bsmenuNotas:
                Toast.makeText(getContext(), getString(R.string.ver_notas_de, mAlumno.getNombre()),
                        Toast.LENGTH_SHORT).show();
                dismiss();
                return true;
        }
        return false;
    }

}
