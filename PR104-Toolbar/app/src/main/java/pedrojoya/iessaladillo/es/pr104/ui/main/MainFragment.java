package pedrojoya.iessaladillo.es.pr104.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import pedrojoya.iessaladillo.es.pr104.R;
import pedrojoya.iessaladillo.es.pr104.ui.secondary.SecondaryFragment;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupToolbar();
    }

    private void setupToolbar() {
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuNext) {
            navigateToSecondary();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToSecondary() {
        requireFragmentManager().beginTransaction()
            .replace(R.id.flContent, SecondaryFragment.newInstance(), SecondaryFragment.class.getSimpleName())
            .addToBackStack(SecondaryFragment.class.getSimpleName())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit();
    }

}
