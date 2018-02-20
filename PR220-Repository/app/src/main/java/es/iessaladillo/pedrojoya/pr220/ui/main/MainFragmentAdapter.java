package es.iessaladillo.pedrojoya.pr220.ui.main;


import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.text.TextUtils;

import es.iessaladillo.pedrojoya.pr220.data.model.Student;
import es.iessaladillo.pedrojoya.pr220.ui.views.RecyclerBindingAdapter;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public class MainFragmentAdapter extends RecyclerBindingAdapter {

    private static final DiffCallback<Student> DIFF_CALLBACK = new DiffCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldStudent, @NonNull Student newStudent) {
            return TextUtils.equals(oldStudent.getId(), newStudent.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldStudent,
                @NonNull Student newStudent) {
            return oldStudent.equals(newStudent);
        }
    };

    @SuppressWarnings({"SameParameterValue", "WeakerAccess"})
    protected MainFragmentAdapter(int modelBRId) {
        super(modelBRId, DIFF_CALLBACK);
    }

}
