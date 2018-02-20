package es.iessaladillo.pedrojoya.pr220.ui.student;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import es.iessaladillo.pedrojoya.pr220.R;
import es.iessaladillo.pedrojoya.pr220.base.SingleFragmentActivity;
import es.iessaladillo.pedrojoya.pr220.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr220.databinding.ActivityStudentBinding;

@MakeActivityStarter
public class StudentActivity extends SingleFragmentActivity<StudentActivityViewModel,
        ActivityStudentBinding> {

    private static final String TAG_STUDENT_FRAGMENT = "TAG_STUDENT_FRAGMENT";

    @SuppressWarnings("WeakerAccess")
    @Arg(optional = true)
    public String studentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setMe(this);
    }

    @Override
    protected Class<StudentActivityViewModel> getViewModelClass() {
        return StudentActivityViewModel.class;
    }

    @Override
    protected StudentActivityViewModel createViewModel(Application application) {
        return new StudentActivityViewModel(RepositoryImpl.getInstance(this), studentId);
    }

    @Override
    public String getTag() {
        return TAG_STUDENT_FRAGMENT;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_student;
    }

    @Override
    public Fragment getFragment() {
        return !TextUtils.isEmpty(studentId) ? StudentFragmentStarter.newInstance(
                studentId) : StudentFragmentStarter.newInstance();
    }

    @Override
    public int getContainerResId() {
        return R.id.flContent;
    }

}
