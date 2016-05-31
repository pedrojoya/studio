package pedrojoya.iessaladillo.es.pr181.main.view.listener;

import android.view.View;

import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;

public interface OnMainItemLongClickListener {
    void onItemLongClick(View view, Student alumno, int position);
}
