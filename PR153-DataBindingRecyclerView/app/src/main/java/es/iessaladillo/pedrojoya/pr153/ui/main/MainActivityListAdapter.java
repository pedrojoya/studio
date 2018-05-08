package es.iessaladillo.pedrojoya.pr153.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import es.iessaladillo.pedrojoya.pr153.base.bindinglistadapter.BindingListAdapter;
import es.iessaladillo.pedrojoya.pr153.data.local.model.Student;

@SuppressWarnings("unused")
public class MainActivityListAdapter extends BindingListAdapter<Student> {

    public static final DiffUtil.ItemCallback<Student> DIFF_CALLBACK = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldStudent, @NonNull Student newStudent) {
            return oldStudent.getId() == newStudent.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldStudent,
                @NonNull Student newStudent) {
            return oldStudent.equals(newStudent);
        }
    };

    protected MainActivityListAdapter(int modelBRId) {
        super(modelBRId, DIFF_CALLBACK);
    }

}
