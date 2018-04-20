package es.iessaladillo.pedrojoya.pr234.ui.main;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import es.iessaladillo.pedrojoya.pr234.R;
import es.iessaladillo.pedrojoya.pr234.base.YesNoDialogFragment;

public class MainFragment extends Fragment implements YesNoDialogFragment.Listener {

    private static final String TAG_DIALOG_FRAGMENT = "TAG_DIALOG_FRAGMENT";
    private static final int RC_DIALOG_FRAGMENT = 1;

    private Button btnDelete;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
    }

    private void initViews(View view) {
        btnDelete = ViewCompat.requireViewById(view, R.id.btnDelete);
        btnDelete.setOnClickListener(v -> showConfirmationDialog());
    }

    private void showConfirmationDialog() {
        YesNoDialogFragment dialogFragment = YesNoDialogFragment.newInstance(
                getString(R.string.main_fragment_confirm_deletion),
                getString(R.string.main_fragment_sure), getString(R.string.main_fragment_yes),
                getString(R.string.main_fragment_no));
        dialogFragment.setTargetFragment(this, RC_DIALOG_FRAGMENT);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), TAG_DIALOG_FRAGMENT);
    }

    @Override
    public void onPositiveButtonClick(DialogInterface dialog) {
        Snackbar.make(btnDelete, getString(R.string.main_fragment_delete), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClick(DialogInterface dialog) {
        Snackbar.make(btnDelete, getString(R.string.main_fragment_no_delete), Snackbar
                .LENGTH_SHORT).show();
    }

}
