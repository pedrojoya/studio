package es.iessaladillo.pedrojoya.pr234.ui.main;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr234.R;
import es.iessaladillo.pedrojoya.pr234.base.YesNoDialogFragment;
import es.iessaladillo.pedrojoya.pr234.utils.SnackbarUtils;

public class MainFragment extends Fragment implements YesNoDialogFragment.Listener {

    private static final String TAG_DIALOG_FRAGMENT = "TAG_DIALOG_FRAGMENT";
    private static final int RC_DIALOG_FRAGMENT = 1;

    private Button btnDelete;

    static MainFragment newInstance() {
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
                getString(R.string.main_fragment_no), this, RC_DIALOG_FRAGMENT);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), TAG_DIALOG_FRAGMENT);
    }

    @Override
    public void onPositiveButtonClick(DialogInterface dialog) {
        SnackbarUtils.snackbar(btnDelete, getString(R.string.main_fragment_delete));
    }

    @Override
    public void onNegativeButtonClick(DialogInterface dialog) {
        SnackbarUtils.snackbar(btnDelete, getString(R.string.main_fragment_no_delete));
    }

}
