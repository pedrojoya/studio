package es.iessaladillo.pedrojoya.pr254.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import es.iessaladillo.pedrojoya.pr254.R;

public class MainFragment extends Fragment {

    private NavController navController;
    private TextView txtMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setupViews(requireView());
    }

    private void setupViews(View view) {
        txtMessage = ViewCompat.requireViewById(view, R.id.txtMessage);
        Button btnShowSecondary = ViewCompat.requireViewById(view, R.id.btnShowSecondary);
        btnShowSecondary.setOnClickListener(v -> showSecondary());
    }

    private void showSecondary() {
        String message = txtMessage.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            MainFragmentDirections.ShowSecondaryAction showSecondaryAction = MainFragmentDirections.showSecondaryAction();
            showSecondaryAction.setMessage(message);
            navController.navigate(showSecondaryAction);
        }
    }

}
