package pedrojoya.iessaladillo.es.pr201;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import java.util.List;

class AlumnosDiffUtilCallback extends DiffUtil.Callback {

    private final List<Alumno> oldAlumnos;
    private final List<Alumno> newAlumnos;

    public AlumnosDiffUtilCallback(List<Alumno> newAlumnos, List<Alumno> oldAlumnos) {
        this.newAlumnos = newAlumnos;
        this.oldAlumnos = oldAlumnos;
    }

    @Override
    public int getOldListSize() {
        return oldAlumnos.size();
    }

    @Override
    public int getNewListSize() {
        return newAlumnos.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return TextUtils.equals(oldAlumnos.get(oldItemPosition).getNombre(),
                newAlumnos.get(newItemPosition).getNombre());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldAlumnos.get(oldItemPosition).equals(newAlumnos.get(newItemPosition));
    }

    @SuppressWarnings("EmptyMethod")
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

}
