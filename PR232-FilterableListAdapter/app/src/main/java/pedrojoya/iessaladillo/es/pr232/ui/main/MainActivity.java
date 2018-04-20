package pedrojoya.iessaladillo.es.pr232.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pedrojoya.iessaladillo.es.pr232.R;
import pedrojoya.iessaladillo.es.pr232.data.model.Student;
import pedrojoya.iessaladillo.es.pr232.recycleradapter.OnItemClickListener;
import pedrojoya.iessaladillo.es.pr232.recycleradapter.OnItemLongClickListener;


public class MainActivity extends AppCompatActivity implements OnItemClickListener<Student>,OnItemLongClickListener<Student> {

    @SuppressWarnings("FieldCanBeLocal")
    private RecyclerView lstStudents;
    private MainActivityAdapter adapter;
    private View emptyView;
    private MainActivityViewModel viewModel;
    private MenuItem mnuSearch;
    private SearchView searchView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        emptyView = ActivityCompat.requireViewById(this, R.id.emptyView);
        configToolbar();
        configRecyclerView();
        configFab();
    }

    private void configToolbar() {
        toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void configFab() {
        FloatingActionButton fabAccion = ActivityCompat.requireViewById(this, R.id.fabAccion);
        if (fabAccion != null) {
            fabAccion.setOnClickListener(
                    view -> addStudent());
        }
    }

    private void configRecyclerView() {
        adapter = new MainActivityAdapter();
        adapter.setEmptyView(emptyView);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(adapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        adapter.submitList(viewModel.getStudents(false));
    }

    private void addStudent() {
        viewModel.insertStudent();
        adapter.submitList(viewModel.getStudents(true));
        mnuSearch.collapseActionView();
    }

    private void updateStudent(Student student) {
        Student newStudent = (new Student(student)).reverseName();
        viewModel.updateStudent(student, newStudent);
        adapter.submitList(viewModel.getStudents(true));
    }

    private void deleteStudent(Student student) {
        viewModel.removeStudent(student);
        adapter.submitList(viewModel.getStudents(true));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        mnuSearch = menu.findItem(R.id.mnuSearch);
        searchView = (SearchView) mnuSearch.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint(getString(R.string.activity_main_mnuSearch_hint));
        //searchView.setIconifiedByDefault(false);
        // In order to save state.
        mnuSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                viewModel.setSearchViewExpanded(true);
                // Make toolbar not scrollable.
                changeToolbarScrollBehavior(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                viewModel.setSearchViewExpanded(false);
                changeToolbarScrollBehavior(true);
                return true;
            }
        });
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter adapter when text is changed
                adapter.getFilter().filter(query);
                viewModel.setSearchQuery(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void changeToolbarScrollBehavior(boolean scrollable) {
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        if (scrollable) {
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        } else {
            params.setScrollFlags(0);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Restore searching state (in this order).
        String query = viewModel.getSearchQuery();
        if (viewModel.isSearchViewExpanded()) {
            // If done directly, menu item disappears after collapsing.
            lstStudents.post(() -> {
                mnuSearch.expandActionView();
                if (!TextUtils.isEmpty(query)) {
                    searchView.setQuery(query, false);
                }
            });
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSort) {
            adapter.submitList(viewModel.toggleOrder());
            mnuSearch.collapseActionView();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, Student item, int position, long id) {
        updateStudent(item);
        mnuSearch.collapseActionView();
    }

    @Override
    public boolean onItemLongClick(View view, Student item, int position, long id) {
        deleteStudent(item);
        mnuSearch.collapseActionView();
        return true;
    }

}
