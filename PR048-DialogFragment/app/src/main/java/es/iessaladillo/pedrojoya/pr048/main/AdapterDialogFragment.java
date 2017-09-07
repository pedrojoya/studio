package es.iessaladillo.pedrojoya.pr048.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr048.R;
import es.iessaladillo.pedrojoya.pr048.data.model.Student;


public class AdapterDialogFragment extends DialogFragment {

    private Callback mListener = null;
    private ArrayList<Student> mStudents;

    @SuppressWarnings("UnusedParameters")
    public interface Callback {
        void onListItemClick(DialogFragment dialog, Student student);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.adapter_dialog_student);
        b.setIcon(R.drawable.ic_person_black_24dp);
        mStudents = createStudentList();
        MainActivityAdapter adaptador = new MainActivityAdapter(this.getActivity(),
                createStudentList());
        // Cuando se hace click sobre un elemento de la lista.
        b.setAdapter(adaptador, (dialog, which) -> mListener.onListItemClick(AdapterDialogFragment.this,
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
                    + " must implement AdapterDialogFragment.Callback");
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