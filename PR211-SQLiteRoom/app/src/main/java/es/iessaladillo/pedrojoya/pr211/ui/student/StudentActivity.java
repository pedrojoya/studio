package es.iessaladillo.pedrojoya.pr211.ui.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr211.Constants;
import es.iessaladillo.pedrojoya.pr211.R;
import es.iessaladillo.pedrojoya.pr211.utils.FragmentUtils;

public class StudentActivity extends AppCompatActivity {

    private static final String TAG_STUDENT_FRAGMENT = "TAG_STUDENT_FRAGMENT";

    private long studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        obtainIntentData(getIntent());
        initViews();
        loadFragment();
    }

    private void obtainIntentData(Intent intent) {
        if (intent != null && intent.hasExtra(Constants.EXTRA_STUDENT_ID)) {
            studentId = intent.getLongExtra(Constants.EXTRA_STUDENT_ID, 0);
        }
    }

    private void initViews() {
        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadFragment() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_STUDENT_FRAGMENT) == null) {
            StudentFragment studentFragment = getIntent().hasExtra(
                    Constants.EXTRA_STUDENT_ID) ? StudentFragment.newInstance(
                    studentId) : StudentFragment.newInstance();
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    studentFragment, TAG_STUDENT_FRAGMENT);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, StudentActivity.class));
    }

    public static void start(Context context, long studentId) {
        Intent intent = new Intent(context, StudentActivity.class);
        intent.putExtra(Constants.EXTRA_STUDENT_ID, studentId);
        context.startActivity(intent);
    }

}
