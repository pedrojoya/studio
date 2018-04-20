package es.iessaladillo.pedrojoya.pr220.ui.student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import activitystarter.Arg;
import es.iessaladillo.pedrojoya.pr220.R;
import es.iessaladillo.pedrojoya.pr220.base.BaseFragment;
import es.iessaladillo.pedrojoya.pr220.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr220.databinding.FragmentStudentBinding;
import es.iessaladillo.pedrojoya.pr220.utils.ViewMessage;

@SuppressWarnings({"unchecked", "unused"})
public class StudentFragment extends BaseFragment<StudentActivityViewModel, FragmentStudentBinding> {

    @Arg(optional = true)
    public String studentId;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setVm(viewModel);
        initViews();
        viewModel.getStudent().observe(requireActivity(), student -> binding.setStudent(student));
        viewModel.getMessage().observe(requireActivity(), this::showMessage);
    }

    @Override
    protected Class getViewModelClass() {
        return StudentActivityViewModel.class;
    }

    @Override
    protected StudentActivityViewModel createViewModel() {
        return new StudentActivityViewModel(RepositoryImpl.getInstance(requireActivity()), studentId);
    }

    private void showMessage(ViewMessage viewMessage) {
        if (!viewMessage.askIsShownAndMarkAsShown()) {
            Toast.makeText(requireActivity(), viewMessage.getMessageResId(), Toast.LENGTH_SHORT).show();
        }
        if (viewMessage.isFinishActivityNeeded()) {
            requireActivity().finish();
        }
    }

    private void initViews() {
        FloatingActionButton fab = requireActivity().findViewById(R.id.fab);
        fab.setOnClickListener(v -> viewModel.onFabClick());
        updateTitle();
    }

    private void updateTitle() {
        requireActivity().setTitle(
                viewModel.isInEditMode() ? R.string.student_fragment_edit_student : R.string.student_fragment_add_student);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_student;
    }
}
