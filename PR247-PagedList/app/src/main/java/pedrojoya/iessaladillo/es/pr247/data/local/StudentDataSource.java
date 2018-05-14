package pedrojoya.iessaladillo.es.pr247.data.local;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import pedrojoya.iessaladillo.es.pr247.data.model.Student;

public class StudentDataSource extends PositionalDataSource<Student> {

    private Database database;

    public StudentDataSource(Database database) {
        this.database = database;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Student> callback) {
        if (params.placeholdersEnabled) {
            callback.onResult(
                    database.getStudents(params.requestedStartPosition, params.requestedLoadSize),
                    params.requestedStartPosition,
                    database.getStudentsCount());
        } else {
            callback.onResult(
                    database.getStudents(params.requestedStartPosition, params.requestedLoadSize),
                    params.requestedStartPosition);
        }
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Student> callback) {
        callback.onResult(database.getStudents(params.startPosition, params.loadSize));
    }

}
