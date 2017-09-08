package es.iessaladillo.pedrojoya.pr178.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr178.R;
import es.iessaladillo.pedrojoya.pr178.data.model.Student;


public class MainActivity extends AppCompatActivity implements MainActivityAdapter
        .OnItemClickListener, MainActivityAdapter.OnItemLongClickListener, ActionMode.Callback {

    private static final String TAG_BOTTOMSHEET_FRAGMENT = "TAG_BOTTOMSHEET_FRAGMENT";

    private RecyclerView lstStudents;
    private FloatingActionButton fab;
    private TextView lblEmptyView;
    private Toolbar toolbar;

    private MainActivityAdapter mAdapter;
    private ActionMode mActionMode;
    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory()).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        lstStudents = findViewById(R.id.lstStudents);
        lblEmptyView = findViewById(R.id.lblEmptyView);
        fab = findViewById(R.id.fab);

        setupToolbar();
        setupRecyclerView();
        setupFab();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupFab() {
        fab.setOnClickListener(view -> addStudent());
    }

    private void setupRecyclerView() {
        mAdapter = new MainActivityAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.setEmptyView(lblEmptyView);
        mAdapter.setData(mViewModel.getStudents());
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(mAdapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        // Drag & drop y Swipe to dismiss.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        mAdapter.swapItems(fromPos, toPos);
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        mAdapter.removeItem(viewHolder.getAdapterPosition());
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void addStudent() {
        mViewModel.addFakeStudent();
        mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
        lstStudents.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void showFloatingViews() {
        ViewCompat.animate(fab).translationY(0);
    }

    private void hideFloatingViews() {
        ViewCompat.animate(fab).translationY(
                fab.getHeight() + getResources().getDimensionPixelOffset(
                        R.dimen.activity_main_fab_margin));
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        mActionMode = actionMode;
        actionMode.getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
        hideFloatingViews();
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.mnuDelete) {
            mAdapter.removeSelectedItems();
            actionMode.finish();
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        mAdapter.clearSelections();
        this.mActionMode = null;
        showFloatingViews();
    }

    @Override
    public void onItemClick(View view, Student student, int position) {
        if (this.mActionMode != null) {
            toggleSelection(position);
        } else {
            showBottomSheetDialogFragment(student);
        }
    }

    private void showBottomSheetDialogFragment(Student student) {
        MenuBottomSheetDialogFragment dialogFragment = MenuBottomSheetDialogFragment.newInstance(
                student);
        dialogFragment.show(getSupportFragmentManager(), TAG_BOTTOMSHEET_FRAGMENT);
    }

    @Override
    public void onItemLongClick(View view, Student student, int position) {
        if (this.mActionMode != null) {
            toggleSelection(position);
        } else {
            toolbar.startActionMode(this);
            toggleSelection(position);
        }
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        mActionMode.setTitle(getString(R.string.main_activity_one_of_total, mAdapter
                        .getSelectedItemCount(),
                mAdapter.getItemCount()));
        if (mAdapter.getSelectedItemCount() == 0) {
            mActionMode.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.onDestroy();
    }

}
