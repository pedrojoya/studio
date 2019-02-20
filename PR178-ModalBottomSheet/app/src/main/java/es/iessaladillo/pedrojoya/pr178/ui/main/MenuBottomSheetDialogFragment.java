package es.iessaladillo.pedrojoya.pr178.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import es.iessaladillo.pedrojoya.pr178.R;
import es.iessaladillo.pedrojoya.pr178.data.local.model.Student;

@SuppressWarnings("WeakerAccess")
public class MenuBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_STUDENT = "ARG_STUDENT";
    private static final int SHEET_PEAK_HEIGHT = 650;

    private Student student;

    static MenuBottomSheetDialogFragment newInstance(@NonNull Student student) {
        MenuBottomSheetDialogFragment frg = new MenuBottomSheetDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_STUDENT, student);
        frg.setArguments(arguments);
        return frg;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottomsheet_menu, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        student = Objects.requireNonNull(requireArguments().getParcelable(ARG_STUDENT));
        setupViews(requireView());
    }

    private void setupViews(View view) {
        setupBottomSheet(view);
    }

    private void setupBottomSheet(View view) {
        CoordinatorLayout.LayoutParams params =
            (CoordinatorLayout.LayoutParams) ((View) view.getParent())
            .getLayoutParams();
        BottomSheetBehavior bottomSheetBehavior =
            (BottomSheetBehavior) params.getBehavior();
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

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

            });
            // To assure sheet is completely shown.
            bottomSheetBehavior.setPeekHeight(SHEET_PEAK_HEIGHT);
        }
        setupNavigationView(view);
    }

    private void setupNavigationView(View view) {
        NavigationView navigationView = ViewCompat.requireViewById(view, R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(this::onNavItemSelected);
        navigationView.getMenu().findItem(R.id.mnuTitle).setTitle(student.getName());
    }

    public boolean onNavItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuCall:
                call();
                break;
            case R.id.mnuSendMessage:
                sendMessage();
                break;
            case R.id.mnuNotes:
                seeNotes();
                break;
            default:
                return false;
        }
        dismiss();
        return true;
    }

    private void call() {
        Toast.makeText(requireContext(), getString(R.string.bottomsheet_call, student.getName()),
            Toast.LENGTH_SHORT).show();
    }

    private void sendMessage() {
        Toast.makeText(requireContext(),
            getString(R.string.bottomsheet_send_message, student.getName()), Toast.LENGTH_SHORT)
            .show();
    }

    private void seeNotes() {
        Toast.makeText(requireContext(),
            getString(R.string.bottomsheet_see_notes, student.getName()), Toast.LENGTH_SHORT)
            .show();
    }

}
