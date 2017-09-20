package es.iessaladillo.pedrojoya.pr196.ui.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr196.Constants;
import es.iessaladillo.pedrojoya.pr196.R;
import es.iessaladillo.pedrojoya.pr196.utils.FragmentUtils;

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

    @SuppressWarnings("SameParameterValue")
    public static void startForResult(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(new Intent(fragment.getActivity(), StudentActivity.class),
                requestCode);
    }

    @SuppressWarnings("SameParameterValue")
    public static void startForResult(Fragment fragment, long studentId, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), StudentActivity.class);
        intent.putExtra(Constants.EXTRA_STUDENT_ID, studentId);
        fragment.startActivityForResult(intent, requestCode);
    }

}
