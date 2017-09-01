package es.iessaladillo.pedrojoya.pr020;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView lstStudents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        lstStudents = findViewById(R.id.lstStudents);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.students));
        lstStudents.setAdapter(mAdapter);
        lstStudents.setOnItemClickListener((adapterView, view, position, id) ->
                showMessage(getString(R.string.main_activity_student_click,
                (String) lstStudents.getItemAtPosition(position))));
        // Register ListView for context menu.
        registerForContextMenu(lstStudents);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lstStudents) {
            // Get position form menuInfo.
            int position = ((AdapterContextMenuInfo) menuInfo).position;
            // Update menu text to include student.
            getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
            menu.findItem(R.id.mnuEdit).setTitle(
                    getString(R.string.activity_main_menu_edit, lstStudents.getItemAtPosition(position)));
            menu.findItem(R.id.mnuDelete).setTitle(
                    getString(R.string.activity_main_menu_delete, lstStudents.getItemAtPosition(position)));
            menu.setHeaderTitle(R.string.main_activity_menu_header_title);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Get position form menuInfo.
        int position = ((AdapterContextMenuInfo) item.getMenuInfo()).position;
        switch (item.getItemId()) {
            case R.id.mnuEdit:
                showMessage(getString(R.string.activity_main_menu_edit, lstStudents.getItemAtPosition(position)));
                break;
            case R.id.mnuDelete:
                showMessage(getString(R.string.activity_main_menu_delete,
                        lstStudents.getItemAtPosition(position)));
                break;
            default:
                return super.onContextItemSelected(item);
        }
        // Event processed.
        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

}
