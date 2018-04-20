package es.iessaladillo.pedrojoya.pr059.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr059.R;

public class MainActivity extends AppCompatActivity {

    private ListView lstStudents;
    private SearchView searchView;

    private ArrayAdapter<String> mAdapter;
    private MainActivityViewModel viewModel;
    private MenuItem mnuSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.students));
        lstStudents.setAdapter(mAdapter);
        lstStudents.setOnItemClickListener((adapterView, view, position, id) -> showStudent(
                (String) lstStudents.getItemAtPosition(position)));
    }

    private void showStudent(String student) {
        Toast.makeText(this,
                getResources().getString(R.string.main_activity_student_clicked, student),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        mnuSearch = menu.findItem(R.id.mnuSearch);
        searchView = (SearchView) mnuSearch.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        mnuSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                viewModel.setSearchViewExpanded(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                viewModel.setSearchViewExpanded(false);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter adapter when text is changed
                mAdapter.getFilter().filter(query);
                viewModel.setSearchQuery(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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

}
