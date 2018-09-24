package es.iessaladillo.pedrojoya.pr132.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr132.R;
import es.iessaladillo.pedrojoya.pr132.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr132.data.local.Database;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private MainFragmentViewModel viewModel;
    private MainFragmentAdapter listAdapter;
    private ShareActionProvider shareActionProvider;

    public MainFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        viewModel = ViewModelProviders.of(this,
                new MainFragmentViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
                MainFragmentViewModel.class);
        initViews(getView());
    }

    private void initViews(View view) {
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        RecyclerView lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);

        TextView lblEmpty = ViewCompat.requireViewById(view, R.id.lblEmpty);
        listAdapter = new MainFragmentAdapter();
        listAdapter.setEmptyView(lblEmpty);
        listAdapter.setOnItemClickListener(
                (v, position) -> deleteStudent(listAdapter.getItem(position)));
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);
        listAdapter.submitList(viewModel.getStudents());
    }

    private Intent createShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, buildStudentListString());
        return intent;
    }

    private String buildStudentListString() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        for (int i = 0; i < listAdapter.getItemCount(); i++) {
            sb.append(listAdapter.getItem(i)).append("\n");
        }
        return sb.toString();
    }

    private void deleteStudent(String student) {
        viewModel.deleteStudent(student);
        listAdapter.submitList(viewModel.getStudents());
        // Update share intent.
        shareActionProvider.setShareIntent(createShareIntent());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main, menu);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(
                menu.findItem(R.id.mnuShare));
        shareActionProvider.setShareIntent(createShareIntent());
    }

    static MainFragment newInstance() {
        return new MainFragment();
    }

}
