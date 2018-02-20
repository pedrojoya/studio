package es.iessaladillo.pedrojoya.pr220.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import es.iessaladillo.pedrojoya.pr220.data.model.Student;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract StudentDao studentDao();

}