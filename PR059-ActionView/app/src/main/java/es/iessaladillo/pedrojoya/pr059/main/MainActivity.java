package es.iessaladillo.pedrojoya.pr059.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr059.R;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String STATE_SEARCH = "STATE_SEARCH";
    private static final String STATE_EXPANDED = "STATE_EXPANDED";

    private ListView lstStudents;
    private SearchView svSearch;

    private ArrayAdapter<String> mAdapter;
    private String mSearch;
    private boolean mIsSearchViewExpanded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreInstanceState(savedInstanceState);
        initViews();
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSearch = savedInstanceState.getString(STATE_SEARCH);
            mIsSearchViewExpanded = savedInstanceState.getBoolean(STATE_EXPANDED);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_SEARCH, svSearch.getQuery().toString());
        outState.putBoolean(STATE_EXPANDED, mIsSearchViewExpanded);
    }

    private void initViews() {
        lstStudents = findViewById(R.id.lstStudents);

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
        MenuItem mnuSearch = menu.findItem(R.id.mnuSearch);
        svSearch = (SearchView) mnuSearch.getActionView();
        mnuSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                mIsSearchViewExpanded = true;
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                mIsSearchViewExpanded = false;
                return true;
            }
        });
        svSearch.setOnQueryTextListener(this);
        // In this order.
        if (mIsSearchViewExpanded) {
            mnuSearch.expandActionView();
        }

        if (!TextUtils.isEmpty(mSearch)) {
            svSearch.setQuery(mSearch, false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextChange(String s) {
        // Filter (ArrayAdapter already implements Filterable).
        mAdapter.getFilter().filter(s);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

}
