package pedrojoya.iessaladillo.es.pr256.data.local;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import pedrojoya.iessaladillo.es.pr256.data.local.model.Student;

@Database(entities = {Student.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "instituto";
    // AppDatabase must be created just once.
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            insertInitialData();
                        }
                    }).build();
                }
            }
        }
        return instance;
    }

    private static void insertInitialData() {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> instance.studentDao().insertInitialStudents(100));
    }

    public abstract StudentDao studentDao();

}