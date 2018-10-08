package es.iessaladillo.pedrojoya.pr048.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import es.iessaladillo.pedrojoya.pr048.R;
import es.iessaladillo.pedrojoya.pr048.data.model.Student;

@SuppressWarnings("WeakerAccess")
public class StudentsDialogFragment extends DialogFragment {

    private Callback mListener = null;
    private ArrayList<Student> mStudents;

    @SuppressWarnings("UnusedParameters")
    public interface Callback {
        void onListItemClick(DialogFragment dialog, Student student);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(requireActivity());
        b.setTitle(R.string.adapter_dialog_student);
        mStudents = createStudentList();
        StudentsDialogFragmentAdapter adaptador = new StudentsDialogFragmentAdapter(requireActivity(),
                createStudentList());
        // Cuando se hace click sobre un elemento de la lista.
        b.setAdapter(adaptador, (dialog, which) -> mListener.onListItemClick(StudentsDialogFragment.this,
                mStudents.get(which)));
        return b.create();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement StudentsDialogFragment.Listener");
        }
    }

    private ArrayList<Student> createStudentList() {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            students.add(new Student(
                    "Student " + i,
                    "c/ Address, nº " + i,
                    "http://lorempixel.com/100/100/abstract/" + i + "/"));
        }
        return students;
    }

}