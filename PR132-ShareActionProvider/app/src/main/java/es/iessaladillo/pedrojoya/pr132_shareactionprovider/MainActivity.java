package es.iessaladillo.pedrojoya.pr132_shareactionprovider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private ListView lstStudents;

    private ArrayAdapter<String> mAdapter;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        lstStudents = findViewById(R.id.lstStudents);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.students))));
        lstStudents.setEmptyView(findViewById(R.id.lblEmpty));
        lstStudents.setAdapter(mAdapter);
        lstStudents.setOnItemClickListener(
                (adapterView, view, position, id) -> remove(mAdapter.getItem(position)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(
                menu.findItem(R.id.mnuShare));
        mShareActionProvider.setShareIntent(createShareIntent());
        return super.onCreateOptionsMenu(menu);
    }

    private Intent createShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getStudentsList());
        return intent;
    }

    private String getStudentsList() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        for (int i = 0; i < mAdapter.getCount(); i++) {
            sb.append(mAdapter.getItem(i)).append("\n");
        }
        return sb.toString();
    }

    private void remove(String elemento) {
        mAdapter.remove(elemento);
        // Update share intent.
        mShareActionProvider.setShareIntent(createShareIntent());
    }

}
