package es.iessaladillo.pedrojoya.pr153.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import es.iessaladillo.pedrojoya.pr153.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr153.dbutils.RecyclerBindingAdapter;

@SuppressWarnings("unused")
public class MainActivityAdapter extends RecyclerBindingAdapter<Student> {

    private final int modelBRId;

    public static final DiffCallback<Student> DIFF_CALLBACK = new DiffCallback<Student>() {
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

    protected MainActivityAdapter(int modelBRId) {
        super(modelBRId, DIFF_CALLBACK);
        this.modelBRId = modelBRId;
    }

}
