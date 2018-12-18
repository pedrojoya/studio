package es.iessaladillo.pedrojoya.pr057.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr057.R;
import es.iessaladillo.pedrojoya.pr057.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr057.data.local.Database;
import es.iessaladillo.pedrojoya.pr057.data.local.model.Student;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private MainFragmentAdapter listAdapter;
    private MainFragmentViewModel viewModel;
    private TextView lblEmptyView;

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
        Objects.requireNonNull(getView());
        viewModel = ViewModelProviders.of(this,
            new MainFragmentViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
            MainFragmentViewModel.class);
        setupViews(getView());
        viewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            listAdapter.submitList(students);
            lblEmptyView.setVisibility(students.size() > 0 ? View.INVISIBLE : View.VISIBLE);
        });
    }

    private void setupViews(View view) {
        setupListView(view);
        setupFab(view);
    }

    private void setupListView(View view) {
        RecyclerView lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);

        lblEmptyView.setOnClickListener(v -> viewModel.addStudent(Database.newFakeStudent()));
        listAdapter = new MainFragmentAdapter();
        listAdapter.setOnItemClickListener(
            (v, position) -> showStudent(listAdapter.getItem(position)));
        listAdapter.setOnShowAssignmentsListener(
            position -> showAssignments(listAdapter.getItem(position)));
        listAdapter.setOnShowMarksListener(position -> showMarks(listAdapter.getItem(position)));
        listAdapter.setOnShowContextualModeListener(this::startContextualMode);
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(new GridLayoutManager(requireContext(),
            getResources().getInteger(R.integer.main_lstStudents_columns)));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    viewModel.deleteStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
                }
            });
        // Se enlaza con el RecyclerView.
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void setupFab(View view) {
        ViewCompat.requireViewById(view, R.id.fab).setOnClickListener(
            v -> viewModel.addStudent(Database.newFakeStudent()));

    }

    private void showStudent(Student student) {
        Toast.makeText(requireContext(),
            getString(R.string.main_activity_click_on, student.getName()), Toast.LENGTH_SHORT)
            .show();
    }

    private void showAssignments(Student student) {
        Toast.makeText(requireContext(),
            getString(R.string.main_show_assignments, student.getName()), Toast.LENGTH_SHORT)
            .show();
    }

    public void showMarks(Student student) {
        Toast.makeText(requireContext(), getString(R.string.main_show_marks, student.getName()),
            Toast.LENGTH_SHORT).show();
    }

    public void call(Student student) {
        Toast.makeText(requireContext(),
            getString(R.string.main_activity_call_sb, student.getName()), Toast.LENGTH_SHORT)
            .show();
    }

    public void sendMessage(Student student) {
        Toast.makeText(requireContext(),
            getString(R.string.main_send_message_to, student.getName()), Toast.LENGTH_SHORT).show();
    }

    private void startContextualMode(final int position) {
        ((AppCompatActivity) requireActivity()).startSupportActionMode(new ActionMode
            .Callback() {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                mode.setTitle(listAdapter.getItem(position).getName());
                return false;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.fragment_main_item_contextual, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnuCall:
                        call(listAdapter.getItem(position));
                        break;
                    case R.id.mnuSendMessage:
                        sendMessage(listAdapter.getItem(position));
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Nothing done.
            }

        });
    }


}
