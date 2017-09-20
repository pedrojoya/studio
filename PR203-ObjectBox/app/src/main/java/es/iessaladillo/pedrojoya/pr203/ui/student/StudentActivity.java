package es.iessaladillo.pedrojoya.pr203.ui.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import es.iessaladillo.pedrojoya.pr203.App;
import es.iessaladillo.pedrojoya.pr203.R;
import es.iessaladillo.pedrojoya.pr203.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr203.data.model.Student;

public class StudentActivity extends AppCompatActivity {

    private static final String EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID";
    private static final String STATE_PHOTO_URL = "STATE_PHOTO_URL";

    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.txtName)
    TextInputEditText txtName;
    @BindView(R.id.txtAddress)
    TextInputEditText txtAddress;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Random random = new Random();
    private String photoUrl;
    private RepositoryImpl repository;
    private long studentId;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        ButterKnife.bind(this);
        repository = RepositoryImpl.getInstance(((App) getApplication()).getBoxStore());
        obtainIntentData();
        restoreInstanceState(savedInstanceState);
        initViews();
        if (studentId != 0) {
            loadStudent(studentId);
        } else {
            createStudent();
        }
        // After loading student. In case of temporary change by the user and orientation change.
        showPhoto(photoUrl);
    }

    private void createStudent() {
        student = new Student();
        setTitle(R.string.student_activity_add);
    }

    private void obtainIntentData() {
        if (getIntent() != null && getIntent().hasExtra(EXTRA_STUDENT_ID)) {
            studentId = getIntent().getLongExtra(EXTRA_STUDENT_ID, 0);
        }
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            photoUrl = savedInstanceState.getString(STATE_PHOTO_URL);
        } else {
            photoUrl = getRandomPhotoUrl();
        }
    }

    private void initViews() {
        setupToolbar();
    }

    private void loadStudent(long studentId) {
        student = repository.queryStudent(studentId);
        showStudent(student);
    }

    private void showStudent(Student student) {
        photoUrl = student.getPhotoUrl();
        txtName.setText(student.getName());
        txtAddress.setText(student.getAddress());
        setTitle(R.string.student_activity_update);
    }

    private void showPhoto(String photoUrl) {
        Picasso.with(this).load(photoUrl).placeholder(R.drawable.ic_person_black_24dp).error(
                R.drawable.ic_person_black_24dp).into(imgPhoto);
    }

    private String getRandomPhotoUrl() {
        return "http://lorempixel.com/200/200/abstract/" + (random.nextInt(7) + 1) + "/";
    }

    @OnClick(R.id.imgPhoto)
    public void changePhoto(View view) {
        photoUrl = getRandomPhotoUrl();
        showPhoto(photoUrl);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, android.R.color.white));
    }

    @SuppressWarnings("UnusedParameters")
    @OnEditorAction(R.id.txtAddress)
    public boolean done(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            saveStudent();
            return true;
        }
        return false;
    }

    @OnClick(R.id.fab)
    public void saveStudent() {
        if (TextUtils.isEmpty(txtName.getText().toString())) return;
        student.setName(txtName.getText().toString());
        student.setAddress(txtAddress.getText().toString());
        student.setPhotoUrl(photoUrl);
        repository.saveStudent(student);
        finish();
    }

    public static void start(Context context, long studentId) {
        Intent intent = new Intent(context, StudentActivity.class);
        intent.putExtra(EXTRA_STUDENT_ID, studentId);
        context.startActivity(intent);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, StudentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PHOTO_URL, photoUrl);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
